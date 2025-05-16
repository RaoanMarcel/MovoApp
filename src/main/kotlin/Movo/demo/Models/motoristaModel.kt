package Movo.demo.Models

import Movo.demo.DTO.MotoristaDTO
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "motorista")
data class Motorista(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val nome: String,

    @Column(nullable = false, unique = true, length = 14)
    val cpf: String,

    @Column(nullable = false, unique = true, length = 11)
    val cnh: String,

    @Column(nullable = false, length = 2)
    val categoriaCnh: String,

    @Column(nullable = false)
    val dataValidadeCnh: LocalDate,

    @Column(nullable = false, length = 15)
    val telefone: String,

    @Column(nullable = false, unique = true, length = 100)
    val email: String,

    @Column(nullable = false)
    val senha: String,

    @Column(name = "data_criacao")
    val dataCriacao: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "role_id")
    val role: Role? = null
) {
    fun toDTO(): MotoristaDTO {
        return MotoristaDTO(
            id = this.id,
            nome = this.nome,
            cpf = this.cpf,
            cnh = this.cnh,
            categoriaCnh = this.categoriaCnh,
            dataValidadeCnh = this.dataValidadeCnh,
            telefone = this.telefone,
            email = this.email
        )
    }
}


