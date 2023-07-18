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

import java.io.IOException;
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
        Banco banco = registroBancoService.buscar(bancoId);

        backupDatabase(banco);

        return banco.adicionarBackupDeBanco(backupDeBanco);
    }

    public void backupDatabase(Banco banco) {

        try {
            ProcessBuilder novoProcessoBuilder = criaProcessoBuilder(banco);
            Process processo = novoProcessoBuilder.start();

            // Aguarda o término do processo
            int exitCode = processo.waitFor();

            if (exitCode == 0) {
                System.out.println("Backup realizado com sucesso.");
            } else {
                System.out.println("Falha ao realizar o backup. Código de saída: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ProcessBuilder criaProcessoBuilder(Banco banco) {
        String nomeDoBanco = banco.getNome();
        String usuario = banco.getNomeDeUsuario();
        String senha = banco.getSenha();
        String host = banco.getServidor().getEnderecoIp();

        ProcessBuilder processBuilder = new ProcessBuilder();
        String caminhoDoArquivo = System.getProperty("user.dir") + "/" + criaNomeDoArquivoDeBackup(nomeDoBanco);

        if (banco.getTipo() == TipoDeBanco.MYSQL) {
            System.out.println(String.format("mysqldump -h %s -u %s -p%s %s --no-tablespaces > %s", host, usuario, senha, nomeDoBanco, caminhoDoArquivo));
            String[] cmd = {"mysqldump", "-h", host, "-u", usuario, "-p" + senha, nomeDoBanco, "--no-tablespaces", "-r", caminhoDoArquivo};
            processBuilder.command(cmd);
            return processBuilder;
        }

        String[] cmd = {"pg_dump", "-h", host, "-U", usuario, "-F", "p", "-f", caminhoDoArquivo, nomeDoBanco};
        processBuilder.command(cmd);
        processBuilder.environment().put("PGPASSWORD", senha);
        return processBuilder;
    }

    public String criaNomeDoArquivoDeBackup(String nomeDoBanco) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nomeDoArquivoDeBackup = String.format("%s_%s.sql", nomeDoBanco, timeStamp);
        return nomeDoArquivoDeBackup;
    }
}
