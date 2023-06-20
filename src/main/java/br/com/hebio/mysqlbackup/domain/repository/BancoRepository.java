package br.com.hebio.mysqlbackup.domain.repository;

import br.com.hebio.mysqlbackup.domain.model.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {
}
