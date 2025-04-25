package Movo.demo.Security

import Movo.demo.Services.SessaoUsuarioService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

@Component
class JwtAuthenticationFilter(
    private val sessaoUsuarioService: SessaoUsuarioService
) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val token = httpRequest.getHeader("Authorization")?.removePrefix("Bearer ")

        if (!token.isNullOrEmpty()) {
            try {
                sessaoUsuarioService.atualizarAtividade(token)
            } catch (e: Exception) {
                println("Erro ao atualizar atividade ou sessão expirada: ${e.message}")
            }
        }

        chain.doFilter(request, response)
    }
}
