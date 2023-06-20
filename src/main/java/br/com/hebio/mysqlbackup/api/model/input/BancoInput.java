package br.com.hebio.mysqlbackup.api.model.input;

import br.com.hebio.mysqlbackup.domain.model.Servidor;
import br.com.hebio.mysqlbackup.domain.model.TipoDeBanco;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BancoInput {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

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
    private ServidorInput servidor;
}
