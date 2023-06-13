package br.com.hebio.mysqlbackup.handler;

import br.com.hebio.mysqlbackup.model.ErrorDetails;
import br.com.hebio.mysqlbackup.service.exceptions.ServidorNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ServidorNaoEncontradoException.class)
    public ResponseEntity<ErrorDetails> handleServidorNaoEncontradoException(ServidorNaoEncontradoException e, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(404l);
        errorDetails.setTitle("O servidor não pôde ser encontrado");
        errorDetails.setDeveloperMessage("http://erros.mysql-backup.com.br/404");
        errorDetails.setTimestamp(System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }
}
