package br.com.hebio.mysqlbackup.service.exceptions;

public class BancoNaoEncontradoException extends RuntimeException {

    public BancoNaoEncontradoException(String message) {
        super(message);
    }

    public BancoNaoEncontradoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
