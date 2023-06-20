package br.com.hebio.mysqlbackup.domain.service;

import br.com.hebio.mysqlbackup.domain.exception.NegocioException;
import br.com.hebio.mysqlbackup.domain.model.Banco;
import br.com.hebio.mysqlbackup.domain.model.Servidor;
import br.com.hebio.mysqlbackup.domain.repository.BancoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegistroBancoService {

    private final BancoRepository bancoRepository;
    private final RegistroServidorService registroServidorService;

    public Banco buscar(Long proprietarioId) {
        return bancoRepository.findById(proprietarioId)
                .orElseThrow(() -> new NegocioException("Banco não encontrado"));
    }

    @Transactional
    public Banco salvar(Banco novoBanco) {
        if (novoBanco.getId() != null) {
            throw new NegocioException("Banco a ser cadastrado não deve possuir um id");
        }

        Servidor servidor = registroServidorService.buscar(novoBanco.getServidor().getId());

        novoBanco.setServidor(servidor);

        return bancoRepository.save(novoBanco);
    }

    @Transactional
    public void excluir(Long bancoId) {
        bancoRepository.deleteById(bancoId);
    }
}
