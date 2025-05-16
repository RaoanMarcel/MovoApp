package Movo.demo.Services

import Movo.demo.Repositories.AlunoRepository
import Movo.demo.Repositories.EmpresaRepository
import Movo.demo.Repositories.MotoristaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import Movo.demo.Security.CustomUserDetails

@Service
class CustomUserDetailsService(
    private val alunoRepository: AlunoRepository,
    private val empresaRepository: EmpresaRepository,
    private val motoristaRepository: MotoristaRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val aluno = alunoRepository.findByEmail(email)
        if (aluno != null) return CustomUserDetails(aluno.id!!, aluno.email, aluno.senha)

        val empresa = empresaRepository.findByEmail(email)
        if (empresa != null) return CustomUserDetails(empresa.id!!, empresa.email, empresa.senha)

        val motorista = motoristaRepository.findByEmail(email)
        if (motorista != null) return CustomUserDetails(motorista.id!!, motorista.email, motorista.senha)

        throw UsernameNotFoundException("Usuário com email $email não encontrado")

    }
}
