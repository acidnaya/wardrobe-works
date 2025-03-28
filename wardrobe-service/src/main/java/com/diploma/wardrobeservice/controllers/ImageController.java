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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wardrobe-service/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/save")
    public ResponseEntity<?> saveClothesImage(@RequestHeader("X-User-ID") String userId,
                                              @RequestParam("file") MultipartFile file) {

        try {
            String filePath = imageService.saveImage(file);
            ImagePathResponse response = ImagePathResponse.builder().imagePath(filePath).build();
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving file: " + e.getMessage());
        }
    }

    @GetMapping("/load")
    public ResponseEntity<Resource> getImage(@RequestParam String path) {
        try {
            Resource resource = imageService.loadImage(path);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }
}
