package Movo.demo.Services

import Movo.demo.Models.SessaoUsuario
import Movo.demo.Repositories.SessaoUsuarioRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SessaoUsuarioService(private val sessaoUsuarioRepository: SessaoUsuarioRepository) {

    fun criarSessao(usuarioId: Long, token: String): SessaoUsuario {
        val sessao = SessaoUsuario(usuarioId = usuarioId, token = token)
        return sessaoUsuarioRepository.save(sessao)
    }

    fun verificarSessaoExpirada(sessao: SessaoUsuario): Boolean {
        val agora = LocalDateTime.now()
        val expiracao = sessao.ultimaAtividade.plusMinutes(sessao.tempoExpiracao)
        return agora.isAfter(expiracao)
    }

    fun atualizarAtividade(token: String) {
        val sessao = sessaoUsuarioRepository.findByToken(token)
            .orElseThrow { IllegalArgumentException("Sessão inválida") }

        if (verificarSessaoExpirada(sessao)) {
            encerrarSessao(token)
            throw IllegalArgumentException("Sessão expirada, faça login novamente.")
        }

        sessao.ultimaAtividade = LocalDateTime.now()
        sessaoUsuarioRepository.save(sessao)
    }

    fun encerrarSessao(token: String) {
        val sessao = sessaoUsuarioRepository.findByToken(token)
            .orElseThrow { IllegalArgumentException("Sessão inválida") }
        sessao.dataLogout = LocalDateTime.now()
        sessaoUsuarioRepository.save(sessao)
    }
}
