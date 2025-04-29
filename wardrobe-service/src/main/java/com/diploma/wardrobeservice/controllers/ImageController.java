package com.diploma.wardrobeservice.controllers;

import com.diploma.wardrobeservice.services.ImageService;
import com.diploma.wardrobeservice.transfers.ImagePathResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;


@RestController
@RequestMapping("api/v1/wardrobe-service/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @RequestHeader("X-User-ID") Long userId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String relativePath = imageService.saveImage(userId, file);
            return ResponseEntity.ok(relativePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка загрузки изображения");
        }
    }
}
