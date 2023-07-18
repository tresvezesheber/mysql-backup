package br.com.hebio.mysqlbackup.api.assembler;

import br.com.hebio.mysqlbackup.api.model.BancoOutput;
import br.com.hebio.mysqlbackup.api.model.input.BancoInput;
import br.com.hebio.mysqlbackup.domain.model.Banco;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class BancoAssembler {

    private final ModelMapper modelMapper;

    public Banco toEntity(BancoInput bancoInput) {
        return modelMapper.map(bancoInput, Banco.class);
    }

    public BancoOutput toModel(Banco banco) {
        return modelMapper.map(banco, BancoOutput.class);
    }

    public void copyToDomainObject(BancoInput bancoInput, Banco banco) {
        modelMapper.map(bancoInput, banco);
    }

    public List<BancoOutput> toCollectionModel(List<Banco> bancos) {
        return bancos.stream()
                .map(this::toModel)
                .toList();
    }
}
