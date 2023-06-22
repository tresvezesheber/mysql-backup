package br.com.hebio.mysqlbackup.api.model;

import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class BackupDeBancoOutput {

    private OffsetDateTime dataDeCriacao;

    @ManyToOne
    private BancoOutput banco;
}
