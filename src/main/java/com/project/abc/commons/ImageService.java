package com.project.abc.commons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class ImageService {

    private final Path baseDirectory = Paths.get("D:/ICBT/Assignments/Advanced Programming/abc-restaurant-images");

    public String uploadImage(MultipartFile image) {
        log.info("upload image");
        return saveImageToLocalDirectory(image);
    }

    private String saveImageToLocalDirectory(MultipartFile image) {
        log.info("save image to local directory");
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path filePath = baseDirectory.resolve(fileName);

        try {
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, image.getBytes());
            System.out.println("File saved at: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + fileName, e);
        }

        return "http://localhost:8080/api/uploads/" + fileName;
    }
}
