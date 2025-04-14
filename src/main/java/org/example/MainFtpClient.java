package org.example;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.example.service.DocumentTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainFtpClient {
    private static final Logger logger = LoggerFactory.getLogger(MainFtpClient.class);

    public static void main(String[] args) {
        DocumentTransactionService documentTransactionService = new DocumentTransactionService();
        documentTransactionService.uploadDocumentTransaction();
    }
}