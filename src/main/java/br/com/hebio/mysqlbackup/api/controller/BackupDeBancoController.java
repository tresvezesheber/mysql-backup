package br.com.hebio.mysqlbackup.api.controller;

import br.com.hebio.mysqlbackup.api.assembler.BackupDeBancoAssembler;
import br.com.hebio.mysqlbackup.api.model.BackupDeBancoOutput;
import br.com.hebio.mysqlbackup.api.model.input.BackupDeBancoInput;
import br.com.hebio.mysqlbackup.domain.model.BackupDeBanco;
import br.com.hebio.mysqlbackup.domain.model.Banco;
import br.com.hebio.mysqlbackup.domain.service.RegistroBackupDeBancoService;
import br.com.hebio.mysqlbackup.domain.service.RegistroBancoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/bancos/{bancoId}/backups")
public class BackupDeBancoController {

    private final BackupDeBancoAssembler backupDeBancoAssembler;
    private final RegistroBackupDeBancoService registroBackupDeBancoService;
    private final RegistroBancoService registroBancoService;

    @GetMapping
    public List<BackupDeBancoOutput> listar(@PathVariable Long bancoId) {
        Banco banco = registroBancoService.buscar(bancoId);
        return backupDeBancoAssembler.toCollectionModel(banco.getBackups());
    }

    @GetMapping("/{backupId}")
    public BackupDeBancoOutput buscar(@PathVariable Long bancoId, @PathVariable Long backupId) {
        return registroBackupDeBancoService.buscar(bancoId, backupId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BackupDeBancoOutput realizar(@PathVariable Long bancoId) {
        BackupDeBanco novoBackupDeBanco = backupDeBancoAssembler.toEntity(new BackupDeBancoInput());
        BackupDeBanco backupDeBancoRealizado = registroBackupDeBancoService.realizar(bancoId, novoBackupDeBanco);
        return backupDeBancoAssembler.toModel(backupDeBancoRealizado);
    }
}
