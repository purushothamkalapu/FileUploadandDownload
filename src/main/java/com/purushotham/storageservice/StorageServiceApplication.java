package com.purushotham.storageservice;

import com.purushotham.storageservice.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@SpringBootApplication
@RestController
@AllArgsConstructor
@RequestMapping("/image")
public class StorageServiceApplication {
    private StorageService storageService;
    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadImage = storageService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }
    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable("") String fileName) throws IOException {
        byte[] imageData = storageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);

    }
    public static void main(String[] args) {
        SpringApplication.run(StorageServiceApplication.class, args);
    }

}
