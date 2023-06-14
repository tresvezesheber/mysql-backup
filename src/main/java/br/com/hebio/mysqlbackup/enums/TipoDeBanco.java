package br.com.hebio.mysqlbackup.enums;

public enum TipoDeBanco {

    MYSQL("MySql"), POSTGRESQL("PostgreSQL");

    private String nome;

    TipoDeBanco(String nome) {
        this.nome = nome;
    }
}
