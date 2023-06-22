package br.com.hebio.mysqlbackup.domain.service;

import br.com.hebio.mysqlbackup.domain.exception.NegocioException;
import br.com.hebio.mysqlbackup.domain.model.Servidor;
import br.com.hebio.mysqlbackup.domain.repository.ServidorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegistroServidorService {

    private final ServidorRepository servidorRepository;

    public Servidor buscar(Long servidorId) {
        return servidorRepository.findById(servidorId)
                .orElseThrow(() -> new NegocioException("Servidor não encontrado"));
    }

    @Transactional
    public Servidor salvar(Servidor servidor) {
        boolean servidorJaCadastrado = servidorRepository.findByEnderecoIp(servidor.getEnderecoIp())
                .filter(p -> !p.equals(servidor))
                .isPresent();

        if (servidorJaCadastrado) {
            throw new NegocioException("Já existe um servidor cadastrado com este endereço ip");
        }

        return servidorRepository.save(servidor);
    }

    @Transactional
    public void remover(Long servidorId) {
        buscar(servidorId);
        servidorRepository.deleteById(servidorId);
    }
}
