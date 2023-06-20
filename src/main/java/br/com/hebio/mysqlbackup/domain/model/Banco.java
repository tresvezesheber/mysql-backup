package br.com.hebio.mysqlbackup.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Banco {

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

    @ManyToOne
    private Servidor servidor;

}
