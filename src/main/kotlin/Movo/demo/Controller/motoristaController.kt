package Movo.demo.Controllers

import Movo.demo.DTO.MotoristaDTO
import Movo.demo.Models.Motorista
import Movo.demo.Services.MotoristaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/motoristas")
class MotoristaController(private val motoristaService: MotoristaService) {

    @GetMapping
    fun listarTodos(): ResponseEntity<List<Unit>> {
        return ResponseEntity.ok(motoristaService.listarTodos())
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): ResponseEntity<MotoristaDTO> {
        val motorista = motoristaService.buscarPorId(id)
        return if (motorista != null) ResponseEntity.ok(motorista) else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun cadastrar(@RequestBody motorista: Motorista): ResponseEntity<MotoristaDTO> {
        return ResponseEntity.ok(motoristaService.cadastrar(motorista))
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long): ResponseEntity<Void> {
        motoristaService.deletar(id)
        return ResponseEntity.noContent().build()
    }

}