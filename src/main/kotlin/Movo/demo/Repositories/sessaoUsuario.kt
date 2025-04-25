package Movo.demo.Repositories

import Movo.demo.Models.SessaoUsuario
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SessaoUsuarioRepository : JpaRepository<SessaoUsuario, Long> {
    fun findByToken(token: String): Optional<SessaoUsuario>
}
