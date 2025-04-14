package org.example;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.example.service.DocumentTransactionService;

public class MainFtpClient {
    public static void main(String[] args) {
        DocumentTransactionService documentTransactionService = new DocumentTransactionService();
        documentTransactionService.uploadDocumentTransaction();
    }
}