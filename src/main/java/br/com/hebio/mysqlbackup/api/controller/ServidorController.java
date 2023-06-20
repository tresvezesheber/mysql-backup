package br.com.hebio.mysqlbackup.api.controller;

import br.com.hebio.mysqlbackup.domain.model.Servidor;
import br.com.hebio.mysqlbackup.domain.repository.ServidorRepository;
import br.com.hebio.mysqlbackup.domain.service.RegistroServidorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/servidores")
public class ServidorController {

    private final RegistroServidorService registroServidorService;
    private final ServidorRepository servidorRepository;

    @GetMapping
    public List<Servidor> listar() {
        return servidorRepository.findAll();
    }

    @GetMapping("/{servidorId}")
    public ResponseEntity<Servidor> buscar(@PathVariable Long servidorId) {
        return servidorRepository.findById(servidorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Servidor adicionar(@Valid @RequestBody Servidor servidor) {
        return registroServidorService.salvar(servidor);
    }

    @PutMapping("/{servidorId}")
    public ResponseEntity<Servidor> atualizar(@PathVariable Long servidorId,
                                              @Valid @RequestBody Servidor servidor) {
        if (!servidorRepository.existsById(servidorId)) {
            return ResponseEntity.notFound().build();
        }

        servidor.setId(servidorId);
        Servidor servidorAtualizado = registroServidorService.salvar(servidor);

        return ResponseEntity.ok(servidorAtualizado);
    }

    @DeleteMapping("/{servidorId}")
    public ResponseEntity<Void> remover(@PathVariable Long servidorId) {
        if (!servidorRepository.existsById(servidorId)) {
            return ResponseEntity.notFound().build();
        }

        registroServidorService.excluir(servidorId);
        return ResponseEntity.noContent().build();
    }
}
