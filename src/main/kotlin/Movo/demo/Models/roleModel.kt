package Movo.demo.Models

import jakarta.persistence.*

@Entity
@Table(name = "role")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true, length = 50)
    val nome: String,

    @Column(length = 255)
    val descricao: String? = null
)