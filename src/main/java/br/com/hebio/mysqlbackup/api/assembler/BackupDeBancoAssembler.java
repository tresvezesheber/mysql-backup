package br.com.hebio.mysqlbackup.api.assembler;

import br.com.hebio.mysqlbackup.api.model.BackupDeBancoOutput;
import br.com.hebio.mysqlbackup.api.model.input.BackupDeBancoInput;
import br.com.hebio.mysqlbackup.domain.model.BackupDeBanco;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class BackupDeBancoAssembler {

    private final ModelMapper modelMapper;

    public BackupDeBanco toEntity(BackupDeBancoInput bancoInput) {
        return modelMapper.map(bancoInput, BackupDeBanco.class);
    }

    public BackupDeBancoOutput toModel(BackupDeBanco backupDeBanco) {
        return modelMapper.map(backupDeBanco, BackupDeBancoOutput.class);
    }

    public List<BackupDeBancoOutput> toCollectionModel(List<BackupDeBanco> backupsDeBancos) {
        return backupsDeBancos.stream()
                .map(this::toModel)
                .toList();
    }
}
