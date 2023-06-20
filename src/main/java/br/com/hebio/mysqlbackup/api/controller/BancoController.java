package br.com.hebio.mysqlbackup.api.controller;

import br.com.hebio.mysqlbackup.api.assembler.BancoAssembler;
import br.com.hebio.mysqlbackup.api.model.BancoOutput;
import br.com.hebio.mysqlbackup.api.model.input.BancoInput;
import br.com.hebio.mysqlbackup.domain.model.Banco;
import br.com.hebio.mysqlbackup.domain.repository.BancoRepository;
import br.com.hebio.mysqlbackup.domain.service.RegistroBancoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/bancos")
public class BancoController {

    private final BancoRepository bancoRepository;
    private final RegistroBancoService registroBancoService;
    private final BancoAssembler bancoAssembler;

    @GetMapping
    public List<BancoOutput> listar() {
        return bancoAssembler.toCollectionModel(bancoRepository.findAll());
    }

    @GetMapping("/{veiculoId}")
    public ResponseEntity<BancoOutput> buscar(@PathVariable Long veiculoId) {
        return bancoRepository.findById(veiculoId)
                .map(bancoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BancoOutput cadastrar(@Valid @RequestBody BancoInput bancoInput) {
        Banco novoBanco = bancoAssembler.toEntity(bancoInput);
        Banco bancoCadastrado = registroBancoService.salvar(novoBanco);

        return bancoAssembler.toModel(bancoCadastrado);
    }
}
