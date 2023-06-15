package br.com.hebio.mysqlbackup.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetails {

    private String title;

    private Long status;

    private Long timestamp;

    private String developerMessage;

}
