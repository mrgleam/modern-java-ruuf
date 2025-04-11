package org.example;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FtpService {
    private String serverAddress;
    private int port;
    private String username;
    private String password;

    private final FTPClient ftpClient;

    public FtpService(String serverAddress, int port, String username, String password) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.username = username;
        this.password = password;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(serverAddress, port);
            ftpClient.login(username, password);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FtpService(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    public Optional<ArrayList<String>> searchAndDeleteFiles(String keyword) {
        return searchFiles(keyword).flatMap(this::deleteTargetFiles);
    }

    public Optional<ArrayList<String>> deleteTargetFiles(ArrayList<String> targets) {
        List<String> deletedFiles = targets.stream()
                .filter(filename -> {
                    try {
                        return ftpClient.deleteFile(filename);
                    } catch (IOException e) {
                        return false;
                    }
                })
                .toList();
        return deletedFiles.isEmpty() ? Optional.empty() : Optional.of(new ArrayList<>(deletedFiles));
    }

    public Optional<ArrayList<String>> searchFiles(String keyword) {
        return listFiles()
                .map(files -> new ArrayList<>(Arrays.stream(files)
                        .map(FTPFile::getName)
                        .filter(name -> name.contains(keyword))
                        .toList())); // Stream and filter file names, then collect into ArrayList
    }

    public Optional<FTPFile[]> listFiles() {
        try {
            FTPFile[] listFiles = this.ftpClient.listFiles();
            return (listFiles.length != 0) ? Optional.of(listFiles) : Optional.empty();
        } catch (IOException e) {
            System.err.println("Error fetching file list: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Boolean> uploadFile(String localFile) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String remoteFile = "remote_file_" + timeStamp + ".txt";
        try (FileInputStream inputStream = new FileInputStream(localFile)) {
            return this.ftpClient.storeFile(remoteFile, inputStream)
                    ? Optional.of(true)
                    : Optional.empty();
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public void terminateConnection(){
        try {
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
