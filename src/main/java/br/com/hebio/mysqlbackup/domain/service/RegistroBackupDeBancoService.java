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
        String comando = criaStringDoComandoDeBackup(banco);

        String backupDirectory = System.getProperty("user.dir");

        String nomeDoArquivoDeBackup = criaNomeDoArquivoDeBackup(banco.getNome());

        File fbackup = new File(backupDirectory + "/" + nomeDoArquivoDeBackup);

        System.out.println(comando);
        try {
            Process process = Runtime.getRuntime().exec(comando);

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
            e.printStackTrace();
            System.err.println("Error executing backup command: " + e.getMessage());
        }
    }

    public String criaStringDoComandoDeBackup(Banco banco) {
        String database = banco.getNome();
        String username = banco.getNomeDeUsuario();
        String password = banco.getSenha();
        String host = banco.getServidor().getEnderecoIp();

        if(banco.getTipo() == TipoDeBanco.MYSQL) {
            return  String.format("mysqldump -h %s -u %s -p%s %s",
                    host, username, password, database);
        }
        return "";
    }

    public String criaNomeDoArquivoDeBackup(String nomeDoBanco) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nomeDoArquivoDeBackup = String.format("%s_%s.sql", nomeDoBanco, timeStamp);
        return nomeDoArquivoDeBackup;
    }
}
