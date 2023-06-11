package br.com.hebio.mysqlbackup.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/backup")
public class BackupController {

    @GetMapping()
    public static void backupMysqlDatabase() throws SQLException, ClassNotFoundException {
        String database = "xxxxx";
        String username = "xxxxx";
        String password = "xxxxx";
        String backupDirectory = System.getProperty("user.dir");

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String backupFileName = String.format("%s_%s.sql", database, timeStamp);

        String command = String.format("mysqldump.exe -u %s -p%s %s > %s\\%s",
                username, password, database, backupDirectory, backupFileName);

        try {
            Process process = Runtime.getRuntime().exec(command);

            int exitCode = process.waitFor();

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
