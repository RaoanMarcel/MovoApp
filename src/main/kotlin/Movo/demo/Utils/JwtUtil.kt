package Movo.demo.Security

import Movo.demo.Config.DotenvConfig
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {
    private val dotenv = DotenvConfig.dotenv

    private val secret = dotenv["JWT_SECRET"]
        ?: throw IllegalStateException("JWT_SECRET não encontrado no .env")

    private val accessExpiration = 900_000L // 15 minutos
    private val refreshExpiration = 604_800_000L // 7 dias

    val keyBytes: ByteArray
        get() = Base64.getDecoder().decode(secret)

    private fun getSigningKey() = Keys.hmacShaKeyFor(keyBytes)

    fun generateAccessToken(userId: Long): String = Jwts.builder()
        .setSubject(userId.toString())
        .setExpiration(Date(System.currentTimeMillis() + accessExpiration))
        .signWith(getSigningKey())
        .compact()

    fun generateRefreshToken(userId: Long): String = Jwts.builder()
        .setSubject(userId.toString())
        .setId(UUID.randomUUID().toString())
        .setExpiration(Date(System.currentTimeMillis() + refreshExpiration))
        .signWith(getSigningKey())
        .compact()

    fun validateToken(token: String): Jws<Claims> {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
    }
}
