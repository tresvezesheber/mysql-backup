package br.com.hebio.mysqlbackup.domain.repository;

import br.com.hebio.mysqlbackup.domain.model.Servidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServidorRepository extends JpaRepository<Servidor, Long> {
}
