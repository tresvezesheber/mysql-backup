package br.com.hebio.mysqlbackup.api.model.input;

import br.com.hebio.mysqlbackup.domain.model.TipoDeBanco;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BancoInput {

    @NotBlank
    @Size(max = 50)
    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoDeBanco tipo;

    @NotBlank
    @Size(max = 50)
    private String nomeDeUsuario;

    @NotBlank
    @Size(max = 120)
    private String senha;

    @Valid
    @NotNull
    private ServidorIdInput servidor;
}
