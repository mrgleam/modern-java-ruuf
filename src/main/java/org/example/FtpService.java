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
        ArrayList<String> deletedFiles = new ArrayList<String>();
        for(String filename: targets) {
            try {
                this.ftpClient.deleteFile(filename);
            } catch (IOException e) {
                return Optional.empty();            }
            deletedFiles.add(filename);
        }
        if(deletedFiles.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(deletedFiles);
    }

    public Optional<ArrayList<String>> searchFiles(String keyword) {
        ArrayList<String> targets = new ArrayList<String>();
        Optional<FTPFile[]> files = listFiles();
        if (files.isPresent()) {
            targets.addAll(
                    Arrays.stream(files.get())
                            .filter(file -> file.getName().contains(keyword))
                            .map(FTPFile::getName)
                            .toList()
            );

        } else {
            return Optional.empty();
        }
        return Optional.of(targets);
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
        String remoteFile = "uploaded_file_" + timeStamp + ".txt";
        try (FileInputStream inputStream = new FileInputStream(localFile)) {
            System.out.println("Uploading file...");
            boolean done = this.ftpClient.storeFile(remoteFile, inputStream);
            if (done) {
                System.out.println("File uploaded successfully.");
            } else {
                System.out.println("Failed to upload file.");
            }
        }
    }
}
