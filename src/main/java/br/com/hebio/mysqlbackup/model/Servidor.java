package br.com.hebio.mysqlbackup.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Servidor {

    private Long id;

    private String nome;

    private String enderecoIp;

}
