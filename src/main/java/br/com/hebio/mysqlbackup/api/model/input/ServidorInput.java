package br.com.hebio.mysqlbackup.api.model.input;

import br.com.hebio.mysqlbackup.domain.model.Servidor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServidorInput {

    @NotNull
    private Long id;
}
