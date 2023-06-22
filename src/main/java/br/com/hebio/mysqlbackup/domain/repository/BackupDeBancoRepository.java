package br.com.hebio.mysqlbackup.domain.repository;

import br.com.hebio.mysqlbackup.domain.model.BackupDeBanco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BackupDeBancoRepository extends JpaRepository<BackupDeBanco, Long> {

    Optional<BackupDeBanco> findByIdAndBanco_Id(Long backupId, Long bancoId);
}
