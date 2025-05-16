package Movo.demo.Services

import Movo.demo.Models.Aluno
import Movo.demo.Repositories.AlunoRepository
import Movo.demo.Repositories.RoleRepository
import Movo.demo.DTO.AlunoDTO
import Movo.demo.DTO.toDTO
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AlunoService(
    private val alunoRepository: AlunoRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun listarTodos(): List<AlunoDTO> =
        alunoRepository.findAll().map { it.toDTO() }

    fun buscarPorId(id: Long): AlunoDTO? =
        alunoRepository.findById(id).orElse(null)?.toDTO()

    fun cadastrar(aluno: Aluno): AlunoDTO {
        val roleAluno = roleRepository.findByNome("ALUNO")
            ?: throw RuntimeException("Role ALUNO não encontrada")
        val senhaCriptografada = passwordEncoder.encode(aluno.senha)
        val novoAluno = aluno.copy(role = roleAluno, senha = senhaCriptografada)
        return alunoRepository.save(novoAluno).toDTO()
    }

    fun deletar(id: Long) {
        alunoRepository.deleteById(id)
    }

}