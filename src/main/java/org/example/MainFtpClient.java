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
            String localFile = "test.txt"; // Path to local file
            System.out.println("Starting file upload: " + localFile);

            Optional<Boolean> uploadedResult = ftpService.uploadFile(localFile);
            if(uploadedResult.isPresent()){
                System.out.println("File uploaded successfully");
            }else{
                System.err.println("File upload failed");
            }

            Optional<ArrayList<String>> deletedFiles = ftpService.searchAndDeleteFiles("5131736");
            if(deletedFiles.isPresent()) {
                printDeletedFiles(deletedFiles.get());
            }else{
                System.out.println("Nothing to delete");
            }

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