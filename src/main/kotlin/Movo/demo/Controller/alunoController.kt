package Movo.demo.Controllers

import Movo.demo.DTO.AlunoDTO
import Movo.demo.Models.Aluno
import Movo.demo.Services.AlunoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/alunos")
class AlunoController(private val alunoService: AlunoService) {

    @GetMapping
    fun listarTodos(): ResponseEntity<List<AlunoDTO>> {
        return ResponseEntity.ok(alunoService.listarTodos())
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): ResponseEntity<AlunoDTO> {
        val aluno = alunoService.buscarPorId(id)
        return if (aluno != null) ResponseEntity.ok(aluno) else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun cadastrar(@RequestBody aluno: Aluno): ResponseEntity<AlunoDTO> {
        return ResponseEntity.ok(alunoService.cadastrar(aluno))
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long): ResponseEntity<Void> {
        alunoService.deletar(id)
        return ResponseEntity.noContent().build()
    }

}