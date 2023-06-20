package br.com.hebio.mysqlbackup.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServidorOutput {

    private Long id;

    private String nome;

    private String enderecoIp;
}
