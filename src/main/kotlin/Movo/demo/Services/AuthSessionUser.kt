package Movo.demo.Services

import Movo.demo.DTO.AuthRequest
import Movo.demo.DTO.AuthResponse
import Movo.demo.Security.CustomUserDetails
import Movo.demo.Security.FileTokenStorage
import Movo.demo.Security.JwtUtil
import io.jsonwebtoken.JwtException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authManager: AuthenticationManager,
    private val jwtUtil: JwtUtil,
    private val fileStore: FileTokenStorage
) {

    fun authenticate(dto: AuthRequest): AuthResponse {
        val auth = authManager.authenticate(
            UsernamePasswordAuthenticationToken(dto.username, dto.password)
        )

        val userId = (auth.principal as CustomUserDetails).getId()
        val access = jwtUtil.generateAccessToken(userId)
        val refresh = jwtUtil.generateRefreshToken(userId)

        val refreshId = jwtUtil.validateToken(refresh).body.id
        fileStore.save(refreshId, refresh)

        return AuthResponse(accessToken = access, refreshToken = refresh)
    }

    fun refresh(refreshToken: String): AuthResponse {
        val claims = try {
            jwtUtil.validateToken(refreshToken).body
        } catch (ex: JwtException) {
            throw IllegalArgumentException("Token inválido ou expirado")
        }

        val tokenId = claims.id
        val storedTokenPair = fileStore.loadAll().find { it.first == tokenId }
            ?: throw IllegalArgumentException("Refresh token não encontrado")

        if (storedTokenPair.second != refreshToken) {
            throw IllegalArgumentException("Refresh token inválido")
        }

        fileStore.remove(tokenId)

        val userId = claims.subject.toLongOrNull()
            ?: throw IllegalArgumentException("ID do usuário inválido no token")

        val newAccess = jwtUtil.generateAccessToken(userId)
        val newRefresh = jwtUtil.generateRefreshToken(userId)

        val newRefreshId = jwtUtil.validateToken(newRefresh).body.id
        fileStore.save(newRefreshId, newRefresh)

        return AuthResponse(accessToken = newAccess, refreshToken = newRefresh)
    }
}
