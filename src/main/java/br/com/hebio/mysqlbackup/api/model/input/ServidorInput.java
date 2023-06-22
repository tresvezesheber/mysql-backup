package br.com.hebio.mysqlbackup.api.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$")
    private String enderecoIp;
}
