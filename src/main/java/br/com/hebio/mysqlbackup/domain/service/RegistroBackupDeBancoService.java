package br.com.hebio.mysqlbackup.domain.service;

import br.com.hebio.mysqlbackup.api.assembler.BackupDeBancoAssembler;
import br.com.hebio.mysqlbackup.api.model.BackupDeBancoOutput;
import br.com.hebio.mysqlbackup.domain.exception.EntidadeNaoEncontradaException;
import br.com.hebio.mysqlbackup.domain.model.BackupDeBanco;
import br.com.hebio.mysqlbackup.domain.model.Banco;
import br.com.hebio.mysqlbackup.domain.repository.BackupDeBancoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegistroBackupDeBancoService {

    private final BackupDeBancoRepository backupDeBancoRepository;
    private final BackupDeBancoAssembler backupDeBancoAssembler;
    private final RegistroBancoService registroBancoService;

    public BackupDeBancoOutput buscar(Long bancoId, Long backupId) {
        registroBancoService.buscar(bancoId);
        return backupDeBancoRepository.findByIdAndBanco_Id(backupId, bancoId).map(backupDeBancoAssembler::toModel)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Backup de banco não encontrado"));
    }

    @Transactional
    public BackupDeBanco realizar(Long bancoId, BackupDeBanco backupDeBanco) {
        Banco banco = registroBancoService.buscar(bancoId);
        return banco.adicionarBackupDeBanco(backupDeBanco);
    }
}
