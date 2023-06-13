package br.com.hebio.mysqlbackup.service;

import br.com.hebio.mysqlbackup.ServidorRepository;
import br.com.hebio.mysqlbackup.model.Servidor;
import br.com.hebio.mysqlbackup.service.exceptions.ServidorNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServidorService {

    @Autowired
    private ServidorRepository servidorRepository;

    public Iterable<Servidor> listaPaginadaDeServidores(Integer size) {
        if (size == null) {
            return servidorRepository.findAll();
        } else {
            PageRequest pageable = PageRequest.of(0, size);
            return servidorRepository.findAll(pageable);
        }
    }

    public Optional<Servidor> buscarServidor(Long id) {
        Optional<Servidor> servidor = servidorRepository.findById(id);
        if (servidor.isEmpty()) {
            throw new ServidorNaoEncontradoException("Servidor não encontrado.");
        }
        return servidor;
    }

    public Long salvarServidor(Servidor servidor) {
        servidor.setId(null);
        return servidorRepository.save(servidor).getId();
    }

    public void deleteServidor(Long id) {
        try {
            servidorRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ServidorNaoEncontradoException("Servidor não encontrado.");
        }
    }

    public void atualizarServidor(Servidor servidor) {
        verificaExistencia(servidor);
        servidorRepository.save(servidor);
    }

    private void verificaExistencia(Servidor servidor) {
        buscarServidor(servidor.getId());
    }
}
