package br.com.hebio.mysqlbackup.domain.service;

import br.com.hebio.mysqlbackup.api.assembler.BancoAssembler;
import br.com.hebio.mysqlbackup.api.model.BancoOutput;
import br.com.hebio.mysqlbackup.api.model.input.BancoInput;
import br.com.hebio.mysqlbackup.domain.exception.NegocioException;
import br.com.hebio.mysqlbackup.domain.model.Banco;
import br.com.hebio.mysqlbackup.domain.model.Servidor;
import br.com.hebio.mysqlbackup.domain.repository.BancoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegistroBancoService {

    private final BancoRepository bancoRepository;
    private final RegistroServidorService registroServidorService;
    private final BancoAssembler bancoAssembler;

    public Banco buscar(Long bancoId) {
        return bancoRepository.findById(bancoId)
                .orElseThrow(() -> new NegocioException("Banco não encontrado"));
    }

    @Transactional
    public Banco salvar(Banco novoBanco) {
        if (novoBanco.getId() != null) {
            throw new NegocioException("Banco a ser cadastrado não deve possuir um id");
        }

        if (bancoJaCadastrado(novoBanco)) {
            throw new NegocioException("Já existe um banco cadastrado com esse nome");
        }

        Servidor servidor = registroServidorService.buscar(novoBanco.getServidor().getId());

        novoBanco.setServidor(servidor);

        return bancoRepository.save(novoBanco);
    }

    @Transactional
    public BancoOutput atualizar(Long bancoId, BancoInput bancoInput) {
        Banco bancoAtual = buscar(bancoId);
        registroServidorService.buscar(bancoAtual.getServidor().getId());

        bancoAssembler.copyToDomainObject(bancoInput, bancoAtual);

        bancoAtual = bancoRepository.save(bancoAtual);

        return bancoAssembler.toModel(bancoAtual);
    }

    @Transactional
    public void excluir(Long bancoId) {
        bancoRepository.deleteById(bancoId);
    }

    public boolean bancoJaCadastrado(Banco banco) {
        return bancoRepository.findByNome(banco.getNome())
                .filter(p -> !p.equals(banco))
                .isPresent();
    }

    @Transactional
    public void remover(Long bancoId) {
        buscar(bancoId);
        bancoRepository.deleteById(bancoId);
    }
}
