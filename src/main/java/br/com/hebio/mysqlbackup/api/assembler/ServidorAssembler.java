package br.com.hebio.mysqlbackup.api.assembler;

import br.com.hebio.mysqlbackup.api.model.ServidorOutput;
import br.com.hebio.mysqlbackup.api.model.input.ServidorInput;
import br.com.hebio.mysqlbackup.domain.model.Servidor;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class ServidorAssembler {

    private final ModelMapper modelMapper;

    public Servidor toEntity(ServidorInput servidorInput) {
        return modelMapper.map(servidorInput, Servidor.class);
    }

    public ServidorOutput toModel(Servidor servidor) {
        return modelMapper.map(servidor, ServidorOutput.class);
    }

    public List<ServidorOutput> toCollectionModel(List<Servidor> servidores) {
        return servidores.stream()
                .map(this::toModel)
                .toList();
    }

}
