package org.example;

import org.example.service.DocumentTransactionService;

import java.util.ArrayList;


public class MainFtpClient {
    public static void main(String[] args) {
        DocumentTransactionService documentTransactionService = new DocumentTransactionService();
        documentTransactionService.uploadDocumentTransaction();
    }

    private static void printDeletedFiles(ArrayList<String> deletedFiles) {
        for (String deletedFile : deletedFiles) {
            System.out.println("Deleted file " + deletedFile);
        }
    }
}