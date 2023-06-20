package br.com.hebio.mysqlbackup.domain.repository;

import br.com.hebio.mysqlbackup.domain.model.Servidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServidorRepository extends JpaRepository<Servidor, Long> {

    Optional<Servidor> findByEnderecoIp(String enderecoIp);
}
