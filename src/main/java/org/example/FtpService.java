package org.example;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FtpService {
    private final FTPClient ftpClient;
    public FtpService(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    public ArrayList<String> searchAndDeleteFiles(String keyword) throws IOException {
        ArrayList<String> targets = searchFiles(keyword);
        return deleteTargetFiles(targets);
    }
    public ArrayList<String> deleteTargetFiles(ArrayList<String> targets) throws IOException {
        ArrayList<String> deletedFiles = new ArrayList<String>();
        for(String filename: targets) {
            this.ftpClient.deleteFile(filename);
            deletedFiles.add(filename);
        }
        return deletedFiles;
    }

    public ArrayList<String> searchFiles(String keyword) throws IOException {
        ArrayList<String> targets = new ArrayList<String>();
        FTPFile[] files = listFiles();
        if (files != null && files.length > 0) {
            for (FTPFile file : files) {
                if (file.getName().contains(keyword)) {
                    targets.add(file.getName());
                }
            }
            System.out.println(targets.size()+" Files containing the keyword \"" + keyword + "\":");
        } else {
            System.out.println("No files found in the directory.");
            return targets;
        }
        return targets;
    }

    public FTPFile[] listFiles() throws IOException {
        return this.ftpClient.listFiles();
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
