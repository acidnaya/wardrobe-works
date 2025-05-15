package com.diploma.wardrobeservice.services;

import com.diploma.wardrobeservice.exceptions.RequestNotProcessedException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {
    private static final String root = "/data/images";

    public String saveImage(Long userId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new RequestNotProcessedException("Не был передан файл");
        }
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        String filename = uuid + "." + extension;

        Path userDir = Paths.get(root, String.valueOf(userId));
        Files.createDirectories(userDir);

        Path targetPath = userDir.resolve(filename);
        file.transferTo(targetPath);

        return String.valueOf(userId) + "/" + filename;
    }

    public Resource loadImage(String relativePath) throws IOException {
        Path imagePath = Paths.get("data").resolve(relativePath);
        Resource resource = new UrlResource(imagePath.toUri());
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new FileNotFoundException("Image not found: " + relativePath);
        }
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1);
    }
}

