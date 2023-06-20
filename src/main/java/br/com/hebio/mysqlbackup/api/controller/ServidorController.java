package br.com.hebio.mysqlbackup.api.controller;

import br.com.hebio.mysqlbackup.api.assembler.ServidorAssembler;
import br.com.hebio.mysqlbackup.api.model.ServidorOutput;
import br.com.hebio.mysqlbackup.api.model.input.ServidorInput;
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

    private final ServidorRepository servidorRepository;
    private final RegistroServidorService registroServidorService;
    private final ServidorAssembler servidorAssembler;

    @GetMapping
    public List<ServidorOutput> listar() {
        return servidorAssembler.toCollectionModel(servidorRepository.findAll());
    }

    @GetMapping("/{servidorId}")
    public ResponseEntity<ServidorOutput> buscar(@PathVariable Long servidorId) {
        return servidorRepository.findById(servidorId)
                .map(servidorAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServidorOutput adicionar(@Valid @RequestBody ServidorInput servidorInput) {
        Servidor novoServidor = servidorAssembler.toEntity(servidorInput);
        Servidor servidorCadastrado = registroServidorService.salvar(novoServidor);

        return servidorAssembler.toModel(servidorCadastrado);
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
