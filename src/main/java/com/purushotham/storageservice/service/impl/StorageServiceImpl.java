package com.purushotham.storageservice.service.impl;

import com.purushotham.storageservice.entity.FileData;
import com.purushotham.storageservice.entity.ImageData;
import com.purushotham.storageservice.repository.FileDataRepository;
import com.purushotham.storageservice.repository.StorageRepository;
import com.purushotham.storageservice.service.StorageService;
import com.purushotham.storageservice.util.ImageUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StorageServiceImpl implements StorageService {
    private StorageRepository storageRepository;
    private FileDataRepository fileDataRepository;

    private final String rootFolderName = "C:\\Users\\Purushotham\\Desktop\\MyFiles\\";
    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = storageRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        if(imageData != null){
            return "File uploaded successfully : "+file.getOriginalFilename();
        }
        return null;
    }

    @Override
    public byte[] downloadImage(String fileName) throws IOException {
        Optional<ImageData> dbImageData = storageRepository.findByName(fileName);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

    /*@Override
    public String uploadImageToFileSystem(MultipartFile file, String folderName) throws IOException {
        String filePath = folderPath+file.getOriginalFilename();
        FileData fileData = fileDataRepository.save(FileData.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .filePath(filePath)
                .build());
        file.transferTo(new File(filePath));
        if(fileData != null){
            return "File upload successfully : "+filePath;
        }
        return null;
    }*/
    @Override
    public String uploadImageToFileSystem(MultipartFile file, String folderName) throws IOException {
        String filePath = rootFolderName+folderName+"\\"+file.getOriginalFilename();
        FileData fileData = fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath)
                .build());
        file.transferTo(new File(filePath));
        if(fileData != null){
            return "File upload successfully : "+filePath;
        }
        return null;
    }

    @Override
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        String filePath = fileData.get().getFilePath();
        byte[] image = Files.readAllBytes(new File(filePath).toPath());
        return image;
    }
}
