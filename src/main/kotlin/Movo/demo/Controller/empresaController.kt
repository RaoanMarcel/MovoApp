package Movo.demo.Controllers

import Movo.demo.Models.Empresa
import Movo.demo.DTO.EmpresaDTO
import Movo.demo.Services.EmpresaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/empresas")
class EmpresaController(private val empresaService: EmpresaService) {

    @GetMapping
    fun listarTodos(): ResponseEntity<List<EmpresaDTO>> {
        val empresas = empresaService.listarTodos()
        return ResponseEntity.ok(empresas)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): ResponseEntity<EmpresaDTO> {
        val empresa = empresaService.buscarPorId(id)
        return if (empresa != null) ResponseEntity.ok(empresa) else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun cadastrar(@RequestBody empresa: Empresa): ResponseEntity<EmpresaDTO> {
        return ResponseEntity.ok(empresaService.cadastrar(empresa))
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: Long): ResponseEntity<Void> {
        empresaService.deletar(id)
        return ResponseEntity.noContent().build()
    }

}