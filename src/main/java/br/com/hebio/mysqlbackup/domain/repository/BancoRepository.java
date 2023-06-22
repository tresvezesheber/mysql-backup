package br.com.hebio.mysqlbackup.domain.repository;

import br.com.hebio.mysqlbackup.domain.model.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {

    Optional<Banco> findByNome(String nome);
}
