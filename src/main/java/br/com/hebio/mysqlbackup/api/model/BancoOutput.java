package br.com.hebio.mysqlbackup.api.model;

import br.com.hebio.mysqlbackup.domain.model.Servidor;
import br.com.hebio.mysqlbackup.domain.model.TipoDeBanco;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BancoOutput {

    private Long id;

    private String nome;

    private TipoDeBanco tipo;

    private String nomeDeUsuario;

    private String senha;

}
