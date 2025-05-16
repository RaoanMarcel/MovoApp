package Movo.demo.Controllers

import Movo.demo.Models.Role
import Movo.demo.Services.RoleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/roles")
class RoleController(private val roleService: RoleService) {

    @GetMapping
    fun listarTodos(): ResponseEntity<List<Role>> =
        ResponseEntity.ok(roleService.listarTodos())

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): ResponseEntity<Role> {
        val role = roleService.buscarPorId(id)
        return if (role != null) ResponseEntity.ok(role)
        else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun cadastrar(@RequestBody role: Role): ResponseEntity<Role> =
        ResponseEntity.ok(roleService.cadastrar(role))

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long): ResponseEntity<Void> {
        roleService.deletar(id)
        return ResponseEntity.noContent().build()
    }
}
