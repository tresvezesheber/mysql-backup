package br.com.hebio.mysqlbackup.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/backups")
public class BackupController {

    @GetMapping()
    public static void backupMysqlDatabase() {
        String database = "xxxxx";
        String username = "xxxxx";
        String password = "xxxxx";
        String backupDirectory = System.getProperty("user.dir");

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String backupFileName = String.format("%s_%s.sql", database, timeStamp);
        File fbackup = new File(backupDirectory + "/" + backupFileName);
        String command = String.format("mysqldump.exe -u %s -p%s %s",
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
}
