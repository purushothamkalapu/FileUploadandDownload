package com.purushotham.storageservice.repository;

import com.purushotham.storageservice.entity.FileData;
import com.purushotham.storageservice.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileDataRepository extends JpaRepository<FileData, Long> {
    Optional<FileData> findByName(String name);
}
