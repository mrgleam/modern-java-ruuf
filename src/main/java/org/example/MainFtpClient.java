package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.io.FileWriter;
import com.google.gson.Gson;

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
        Gson gson = new Gson();
        String jsonString = gson.toJson(userComplicate);
        documentRepository.saveUserComplicate(userComplicate);

        try (FileWriter fileWriter = new FileWriter("user_data.json")) {
            fileWriter.write(jsonString);
            System.out.println("JSON string has been saved to user_data.json");
        } catch (IOException e) {
            System.err.println("Error writing JSON to file: " + e.getMessage());
        }

        try {
            FtpService ftpService = new FtpService("localhost", 2121, "one", "1234");

            // Specify the file to upload
            String localFile = "user_data.json"; // Path to local file
            System.out.println("Starting file upload: " + localFile);
            String directoryName = userComplicate.userSimple.name;
            Optional<Boolean> directoryNotExist = ftpService.checkIfDirectoryIsAlreadyExist(directoryName);
            directoryNotExist.ifPresent(
                    result -> ftpService.createDirectory(directoryName)
            );
            ftpService.uploadFile(directoryName ,localFile)
                    .ifPresentOrElse(
                    result -> System.out.println("Upload successfully"),
                            ()      -> System.out.println("Upload failed for some reasons")
            );
            ftpService.terminateConnection();

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