package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.io.FileWriter;
import com.google.gson.Gson;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MainFtpClient {
    public static void main(String[] args) {
        DocumentRepository documentRepository = new DocumentRepository();
        Optional<UserLegacy> maybeUserLegacy = documentRepository.getUserLegacyById("complicate@sample.com");
        UserComplicate userComplicate;
        if(maybeUserLegacy.isPresent()){
            UserLegacy userLegacy = maybeUserLegacy.get();
            UserSimple userSimple = new UserSimple(
                    userLegacy.name,
                    userLegacy.email,
                    userLegacy.age,
                    userLegacy.isDeveloper);
            userComplicate = new UserComplicate(
                    userSimple,
                    "f1",
                    "f2",
                    "f3",
                    "f4",
                    "f5");

        }else{
            UserSimple userSimple = new UserSimple(
                    "default",
                    "some@email",
                    22,
                    false
            );
            userComplicate = new UserComplicate(
                    userSimple,
                    "ff1",
                    "ff2",
                    "ff3",
                    "ff4",
                    "ff5");
        }

        DocumentTransactionEntity documentTransactionEntity = new DocumentTransactionEntity();
        documentTransactionEntity.setCaseNo(userComplicate.userSimple.name+"-"+userComplicate.userSimple.email);
        documentTransactionEntity.setCreatedBy(userComplicate.userSimple.name);
        documentTransactionEntity.setTotalDocument(3);
        documentTransactionEntity.setCreatedDate(new Date());
        documentTransactionEntity.setDestinationCompanyCode("code1");
        documentTransactionEntity.setDocStatus("status1");
        documentTransactionEntity.setDestinationCompanyCode("desCode1");
        documentTransactionEntity.setDocClass("docClass");
        documentTransactionEntity.setHireeNo("hireNo");

        Gson gson = new Gson();
        String jsonStringOfUserComplicate = gson.toJson(userComplicate);
        String jsonStringOfDocumentTransactionEntity = gson.toJson(documentTransactionEntity);
        documentRepository.saveUserComplicate(userComplicate);
        documentRepository.saveDocumentTransactionEntity(documentTransactionEntity);

        try (FileWriter fileWriter = new FileWriter("user_complicate.json")) {
            fileWriter.write(jsonStringOfUserComplicate);
            System.out.println("JSON string has been saved to user_data.json");
        } catch (IOException e) {
            System.err.println("Error writing JSON to file: " + e.getMessage());
        }

        try (FileWriter fileWriter = new FileWriter("document_transaction.json")) {
            fileWriter.write(jsonStringOfDocumentTransactionEntity);
            System.out.println("JSON string has been saved to user_data.json");
        } catch (IOException e) {
            System.err.println("Error writing JSON to file: " + e.getMessage());
        }

        ArrayList<String> sourceFileList = new ArrayList<>();
        sourceFileList.add("user_complicate.json");//.replace(".json", ""));
        sourceFileList.add("document_transaction.json");//.replace(".json", ""));

        FtpService ftpService = new FtpService("localhost", 2121, "one", "1234");

        // Specify the file to upload
        String localFile = "user_data.json"; // Path to local file
        System.out.println("Starting file upload: " + localFile);
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Define the formatter with the desired pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        // Format the date-time into the desired string format

        String directoryName = currentDateTime.format(formatter);
        Optional<Boolean> directoryNotExist = ftpService.checkIfDirectoryIsAlreadyExist(directoryName);
        directoryNotExist.ifPresent(
                result -> ftpService.createDirectory(directoryName)
        );
        sourceFileList.forEach(fileName ->
            ftpService.uploadFile(directoryName ,fileName)
                    .ifPresentOrElse(
                    result -> System.out.println("Upload successfully"),
                            ()      -> System.out.println("Upload failed for some reasons")
            )
        );
        ftpService.terminateConnection();
    }

    private static void printDeletedFiles(ArrayList<String> deletedFiles) {
        for (String deletedFile : deletedFiles) {
            System.out.println("Deleted file " + deletedFile);
        }
    }
}