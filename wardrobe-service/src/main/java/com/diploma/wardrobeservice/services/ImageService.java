package com.diploma.wardrobeservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private static final String REL_DIR = "/images/";
    private static final String ABS_DIR = System.getProperty("user.dir") + REL_DIR;

    public String saveImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }

        File directory = new File(ABS_DIR);
        if (!directory.exists()) {
            boolean isCreated = directory.mkdirs();
            if (!isCreated) {
                throw new IOException("Failed to create directory");
            }
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File destinationFile = new File(ABS_DIR + fileName);

        if (!directory.canWrite()) {
            throw new IOException("No write permission for the directory");
        }

        file.transferTo(destinationFile);

        return fileName;
    }

    public Resource loadImage(String fileName) throws IOException {
        File file = new File(ABS_DIR + fileName);
        if (!file.exists()) {
            throw new IOException("File not found");
        }
        return new UrlResource(file.toURI());
    }
}
