package com.seongjun.toy.controller;

import com.seongjun.toy.domain.Photo;
import com.seongjun.toy.service.PhotoService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
public class PhotoController {
    private final PhotoService photoService;

    @PostMapping
    public ResponseEntity<String> registerPhoto(@RequestParam(value = "photos", required = false)List<MultipartFile> photos) {
        try {
            if (!photos.isEmpty()) {
                for (MultipartFile file : photos) {
                    photoService.registerPhoto(file);
                }
            }

            return ResponseEntity.ok().body("Files uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload files.");
        }
    }
}
