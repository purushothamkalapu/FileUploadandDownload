package com.purushotham.storageservice;

import com.purushotham.storageservice.service.StorageService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger LOGGER = LoggerFactory.getLogger(StorageServiceApplication.class);
    @GetMapping("/welcome")
    public ResponseEntity<?> welcome(){
        return new ResponseEntity<>("Welcome", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        LOGGER.debug("Uploading started");
        String uploadImage = storageService.uploadImage(file);
        LOGGER.debug("Uploading END");
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);

    }
    @GetMapping("/{name}")
    public ResponseEntity<?> downloadImage(@PathVariable("name") String fileName) throws IOException {
        byte[] imageData = storageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);

    }

    @PostMapping("/file_system")
    public ResponseEntity<?> uploadImageToFileSystem(@RequestParam("image")MultipartFile file, @RequestParam("folderName") String folderName) throws IOException {
        String uploadImage = storageService.uploadImageToFileSystem(file, folderName);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }
    @GetMapping("/file_system/{name}")
    public ResponseEntity<?> downloadImageToFileSystem(@PathVariable("name") String fileName) throws IOException {
        byte[] imageData = storageService.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);

    }
    public static void main(String[] args) {
        SpringApplication.run(StorageServiceApplication.class, args);
    }

}
