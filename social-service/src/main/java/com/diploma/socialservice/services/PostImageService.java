//package com.diploma.socialservice.services;
//
//import com.diploma.socialservice.entities.Post;
//import com.diploma.socialservice.entities.PostImage;
//import com.diploma.socialservice.repositories.PostImageRepository;
//import com.diploma.socialservice.repositories.PostRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class PostImageService {
//
//    private final PostRepository postRepository;
//    private final PostImageRepository postImageRepository;
//
//    private static final String UPLOAD_DIR = "uploads/posts/";
//
//    public String saveImage(Long postId, MultipartFile file, Short position, Long outfitId) throws IOException {
//        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
//
//        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
//        Path postDir = Paths.get(UPLOAD_DIR, String.valueOf(postId));
//        Files.createDirectories(postDir);
//
//        Path filePath = postDir.resolve(filename);
//        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//        String imagePath = "/files/posts/" + postId + "/" + filename;
//
//        PostImage postImage = new PostImage();
//        postImage.setPost(post);
//        postImage.setImagePath(imagePath);
//        postImage.setPosition(position);
//        postImage.setOutfitId(outfitId);
//
//        postImageRepository.save(postImage);
//
//        return imagePath;
//    }
//}
