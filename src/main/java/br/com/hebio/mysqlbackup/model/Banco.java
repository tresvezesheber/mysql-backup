package br.com.hebio.mysqlbackup.model;


import br.com.hebio.mysqlbackup.enums.TipoDeBanco;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Banco {

    private Long id;

    private String nome;

    private TipoDeBanco tipo;

    private String nomeDeUsuario;

    private String senha;

    private Servidor servidor;

}
