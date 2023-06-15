package br.com.hebio.mysqlbackup.service;

import br.com.hebio.mysqlbackup.model.Banco;
import br.com.hebio.mysqlbackup.repository.BancoRepository;
import br.com.hebio.mysqlbackup.repository.ServidorRepository;
import br.com.hebio.mysqlbackup.service.exceptions.BancoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BancoService {

    @Autowired
    private BancoRepository bancoRepository;

    @Autowired
    private ServidorService servidorService;


    public Iterable<Banco> listaPaginadaDeBancos(Integer size) {
        if (size == null) {
            return bancoRepository.findAll();
        } else {
            PageRequest pageable = PageRequest.of(0, size);
            return bancoRepository.findAll(pageable);
        }
    }

    public Optional<Banco> buscarBanco(Long id) {
        Optional<Banco> banco = bancoRepository.findById(id);
        if (banco.isEmpty()) {
            throw new BancoNaoEncontradoException("Banco não encontrado.");
        }
        return banco;
    }

    public Long salvarBanco(Banco banco) {
        banco.setId(null);
        servidorService.verificaExistencia(banco.getServidor());
        return bancoRepository.save(banco).getId();
    }

    public void deleteBanco(Long id) {
        try {
            bancoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BancoNaoEncontradoException("Banco não encontrado.");
        }
    }

    public void atualizarBanco(Banco banco) {
        verificaExistencia(banco);
        bancoRepository.save(banco);
    }

    private void verificaExistencia(Banco banco) {
        buscarBanco(banco.getId());
    }
}
