package br.com.hebio.mysqlbackup.api.model.input;

import br.com.hebio.mysqlbackup.domain.model.Banco;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class BackupDeBancoInput {

    private OffsetDateTime dataDeCriacao;

    @ManyToOne
    private Banco banco;
}
