package br.com.hebio.mysqlbackup.api.model.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServidorIdInput {

    @NotNull
    private Long id;
}
