package Movo.demo.Controllers

import Movo.demo.DTO.AuthRequest
import Movo.demo.DTO.AuthResponse
import Movo.demo.Services.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    fun login(@RequestBody dto: AuthRequest): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authService.authenticate(dto))
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody body: Map<String,String>): ResponseEntity<AuthResponse> {
        val refreshToken = body["refreshToken"] ?: throw IllegalArgumentException("RefreshToken ausente")
        return ResponseEntity.ok(authService.refresh(refreshToken))
    }
}