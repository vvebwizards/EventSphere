package com.esprit.microservice.resourcemanagement.uploads;
import java.io.IOException;
import java.nio.file.*;
public class FileUploadUtil {
    public static String saveFile(String fileName, byte[] content) throws IOException {
        // Define the directory where the file will be saved relative to the project root
        String uploadDir = "src/main/resources/static/uploads";

        // Create the upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Define the full path for the file
        Path filePath = uploadPath.resolve(fileName);

        // Write the file content to the location
        Files.write(filePath, content);

        // Return the relative path to the file from the static folder
        return "/uploads/" + fileName;
    }

}
