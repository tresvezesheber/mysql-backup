package br.com.hebio.mysqlbackup.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Servidor {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String nome;

    @NotBlank
    @Size(max = 15)
    private String enderecoIp;

    @OneToMany(mappedBy = "servidor", cascade = CascadeType.ALL)
    private List<Banco> bancos = new ArrayList<>();

}
