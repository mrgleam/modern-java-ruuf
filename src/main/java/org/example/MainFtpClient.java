package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class MainFtpClient {
    public static void main(String[] args) {
        String server = "127.0.0.1";
        int port = 2121;
        String user = "one";
        String pass = "1234";

        FTPClient ftpClient = new FTPClient();

        try {
            // Connect to the FTP server
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);

            // Set file type to binary
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();

            FtpService ftpService = new FtpService(ftpClient);
            // Specify the file to upload
            ftpService.uploadFile();
            Optional<ArrayList<String>> deletedFiles = ftpService.searchAndDeleteFiles("32741");

            if(deletedFiles.isPresent()) {
                printDeletedFiles(deletedFiles.get());
            }else{
                System.out.println("Nothing to delete");
            }
            // Logout and disconnect
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void printDeletedFiles(ArrayList<String> deletedFiles) {
        for (String deletedFile : deletedFiles) {
            System.out.println("Deleted file " + deletedFile);
        }
    }
}