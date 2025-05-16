package Movo.demo.Services

import Movo.demo.DTO.MotoristaDTO
import Movo.demo.Models.Motorista
import Movo.demo.Repositories.MotoristaRepository
import Movo.demo.Repositories.RoleRepository
import Movo.demo.DTO.toDTO
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MotoristaService(
    private val motoristaRepository: MotoristaRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun listarTodos(): List<Unit> {
        return motoristaRepository.findAll().map { it.toDTO() }
    }

    fun buscarPorId(id: Long): MotoristaDTO? {
        return motoristaRepository.findById(id).orElse(null)?.toDTO()
    }

    fun cadastrar(motorista: Motorista): MotoristaDTO {
        val roleMotorista = roleRepository.findByNome("MOTORISTA")
            ?: throw RuntimeException("Role MOTORISTA não encontrada")

        val senhaCriptografada = passwordEncoder.encode(motorista.senha)
        val novoMotorista = motorista.copy(role = roleMotorista, senha = senhaCriptografada)

        return motoristaRepository.save(novoMotorista).toDTO()
    }

    fun deletar(id: Long) {
        motoristaRepository.deleteById(id)
    }

}