package Movo.demo.Models

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "sessao_usuario")
data class SessaoUsuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val usuarioId: Long,

    @Column(nullable = false, unique = true)
    val token: String,

    @Column(nullable = false)
    val dataLogin: LocalDateTime = LocalDateTime.now(),

    var dataLogout: LocalDateTime? = null,

    var ultimaAtividade: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val tempoExpiracao: Long = 30 // minutos
)
