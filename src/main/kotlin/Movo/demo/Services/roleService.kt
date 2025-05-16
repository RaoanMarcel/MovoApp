package Movo.demo.Services

import Movo.demo.Models.Role
import Movo.demo.Repositories.RoleRepository
import org.springframework.stereotype.Service

@Service
class RoleService(
    private val roleRepository: RoleRepository
) {

    fun listarTodos(): List<Role> =
        roleRepository.findAll()

    fun buscarPorId(id: Long): Role? =
        roleRepository.findById(id).orElse(null)

    fun cadastrar(role: Role): Role =
        roleRepository.save(role)

    fun deletar(id: Long) {
        roleRepository.deleteById(id)
    }
}
