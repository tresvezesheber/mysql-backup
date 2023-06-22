package br.com.hebio.mysqlbackup.api.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServidorInput {

    @NotBlank
    @Size(max = 50)
    private String nome;

    @NotBlank
    @Size(max = 15)
    private String enderecoIp;
}
