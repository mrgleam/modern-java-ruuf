package org.example.service;

import org.example.entity.*;
import org.example.repository.DocumentRepository;
import org.example.utils.Compose;
import org.example.utils.FileWriterUtil;
import org.example.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DocumentTransactionService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentTransactionService.class);
    public void uploadDocumentTransaction() {
        DocumentRepository documentRepository = new DocumentRepository();
        Optional<List<RequestCase>> cases = documentRepository.getAllRequestCase();
        List<RequestCase> requestCases = cases.orElse(Collections.emptyList());

        Optional<UserLegacy> maybeUserLegacy = documentRepository.getUserLegacyById("complicate@sample.com");
        UserComplicate userComplicate = maybeUserLegacy
                .map(UserLegacy::toUserComplicate)
                .orElse(this.makeNewUserComplicate());

        //cases.stream().filter()

        String caseNo = userComplicate.userSimple().name() + "-" + userComplicate.userSimple().email();
        String createdBy = userComplicate.userSimple().name();
        String destinationCompanyCode = !requestCases.isEmpty()
                ? requestCases
                .stream()
                .filter(RequestCase::isCase005)
                .findFirst()
                .map(RequestCase::destinationCompanyCode)
                .orElse("COMP001")
                : "COMP002";
        var documentTransactionEntity = new DocumentTransactionEntity(
                caseNo,
                "CASE003",
                destinationCompanyCode,
                3,
                "status1",
                "hireNo",
                createdBy
        );

        var userComplicateFileName = "user_complicate.json";
        JsonUtil.safeToJson(userComplicate)
                .flatMap(Compose.pipe(
                        documentRepository.save(userComplicate.userSimple().email()),
                        FileWriterUtil.to(userComplicateFileName)
                ))
                .onSuccess(u -> logger.info("JSON string has been saved to " + userComplicateFileName))
                .onFailure(e -> logger.error("Error writing JSON to file: " + e.getMessage()));

        var documentTransactionFileName = "document_transaction.json";
        JsonUtil.safeToJson(documentTransactionEntity)
                .flatMap(Compose.pipe(
                        documentRepository.save(documentTransactionEntity.caseNo()),
                        FileWriterUtil.to(documentTransactionFileName)
                ))
                .onSuccess(u -> logger.info("JSON string has been saved to " + documentTransactionFileName))
                .onFailure(e -> logger.error("Error writing JSON to file: " + e.getMessage()));

        ArrayList<String> sourceFileList = new ArrayList<>();
        sourceFileList.add("user_complicate.json");
        sourceFileList.add("document_transaction.json");

        FtpService ftpService = new FtpService("localhost",
                2121,
                "one",
                "1234");

        // Specify the file to upload
        String localFile = "user_data.json"; // Path to local file
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
                ftpService.uploadFile(directoryName, fileName)
                        .ifPresentOrElse(
                                result -> logger.info("Upload successfully"),
                                () -> logger.error("Upload failed for some reasons")
                        )
        );

        ftpService.terminateConnection();
    }

    private UserComplicate makeNewUserComplicate() {
        UserSimple userSimple = new UserSimple(
                "New",
                "new@customer",
                22,
                false
        );
        return new UserComplicate(
                userSimple,
                "ff1",
                "ff2",
                "ff3",
                "ff4",
                "ff5");
    }
}
