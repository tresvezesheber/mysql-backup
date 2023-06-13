package br.com.hebio.mysqlbackup.service.exceptions;

public class ServidorNaoEncontradoException extends RuntimeException {

    public ServidorNaoEncontradoException(String message) {
        super(message);
    }

    public ServidorNaoEncontradoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
