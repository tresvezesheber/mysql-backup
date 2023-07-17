package br.com.hebio.mysqlbackup.domain.service;

import br.com.hebio.mysqlbackup.api.assembler.BackupDeBancoAssembler;
import br.com.hebio.mysqlbackup.api.model.BackupDeBancoOutput;
import br.com.hebio.mysqlbackup.domain.exception.EntidadeNaoEncontradaException;
import br.com.hebio.mysqlbackup.domain.model.BackupDeBanco;
import br.com.hebio.mysqlbackup.domain.model.Banco;
import br.com.hebio.mysqlbackup.domain.model.TipoDeBanco;
import br.com.hebio.mysqlbackup.domain.repository.BackupDeBancoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@Service
public class RegistroBackupDeBancoService {

    private final BackupDeBancoRepository backupDeBancoRepository;
    private final BackupDeBancoAssembler backupDeBancoAssembler;
    private final RegistroBancoService registroBancoService;

    public BackupDeBancoOutput buscar(Long bancoId, Long backupId) {
        registroBancoService.buscar(bancoId);
        return backupDeBancoRepository.findByIdAndBanco_Id(backupId, bancoId).map(backupDeBancoAssembler::toModel)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Backup de banco não encontrado"));
    }

    @Transactional
    public BackupDeBanco realizar(Long bancoId, BackupDeBanco backupDeBanco) {
        Banco banco = registroBancoService.buscar(bancoId);;
        try {
            backupMysqlDatabase(banco);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return banco.adicionarBackupDeBanco(backupDeBanco);
    }

    public void backupMysqlDatabase(Banco banco) throws SQLException, ClassNotFoundException {
        String database = banco.getNome();
        String username = banco.getNomeDeUsuario();
        String password = banco.getSenha();
        String backupDirectory = System.getProperty("user.dir");

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String backupFileName = String.format("%s_%s.sql", database, timeStamp);
        File fbackup = new File(backupDirectory + "/" + backupFileName);
        String command = String.format(verficaTipoDeBanco(banco),
                username, password, database, backupDirectory, backupFileName);

        try {
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new FileWriter(fbackup));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                writer.write(line);
                writer.newLine();
            }
            writer.close();

            int exitCode = process.waitFor();

            System.out.println(exitCode);
            if (exitCode == 0) {
                System.out.println("Database backup successful.");
            } else {
                System.err.println("Error backing up database.");
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error executing backup command: " + e.getMessage());
        }
    }

    public String verficaTipoDeBanco(Banco banco) {
        if(banco.getTipo() == TipoDeBanco.MYSQL) {
            return "mysqldump.exe -u %s -p%s %s";
        }
        return "NOT MYSQL";
    }
}
