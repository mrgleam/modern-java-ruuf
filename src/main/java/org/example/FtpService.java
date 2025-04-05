package org.example;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

public class FtpService {
    private final FTPClient ftpClient;
    public FtpService(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    public Optional<ArrayList<String>> searchAndDeleteFiles(String keyword) {
        return searchFiles(keyword).flatMap(this::deleteTargetFiles);
    }
    public Optional<ArrayList<String>> deleteTargetFiles(ArrayList<String> targets) {
        ArrayList<String> deletedFiles = new ArrayList<>();
        targets.forEach(filename -> {
            try {
                if (this.ftpClient.deleteFile(filename)) {
                    deletedFiles.add(filename); // Add only successfully deleted files
                } else {
                    System.err.println("Failed to delete file: " + filename);
                }
            } catch (IOException e) {
                System.err.println("Error deleting file: " + filename + " - " + e.getMessage());
            }
        });
        return deletedFiles.isEmpty() ? Optional.empty() : Optional.of(deletedFiles);
    }

    public Optional<ArrayList<String>> searchFiles(String keyword) {
        Optional<FTPFile[]> files = listFiles();
        if (files.isPresent()) {
            ArrayList<String> targets = new ArrayList<>();
            targets.addAll(
                    Arrays.stream(files.get())
                            .filter(file -> file.getName().contains(keyword))
                            .map(FTPFile::getName)
                            .toList()
            );
            return Optional.of(targets);
        } else {
            return Optional.empty();
        }
    }

    public Optional<FTPFile[]> listFiles() {
        try {
            FTPFile[] listFiles = this.ftpClient.listFiles();
            if(listFiles.length != 0) {
                return Optional.of(listFiles);
            }else{
                return Optional.empty();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void uploadFile() throws IOException {
        String localFile = "test.txt"; // Path to local file
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String remoteFile = "remote_file_" + timeStamp + ".txt";
        try (FileInputStream inputStream = new FileInputStream(localFile)) {
            System.out.println("Starting file upload: " + localFile + " -> " + remoteFile);
            boolean isUploaded = this.ftpClient.storeFile(remoteFile, inputStream);
            if (isUploaded) {
                System.out.println("File uploaded successfully: " + remoteFile);
            } else {
                System.err.println("File upload failed: " + remoteFile);
            }
        } catch (IOException e) {
            System.err.println("Error during file upload: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
