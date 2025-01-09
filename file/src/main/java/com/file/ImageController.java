package com.file;

import com.file.domain.IImageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {
    private final IImageRepository imageRepository;

    public ImageController(IImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(MultipartFile file) throws Exception {
        String url = imageRepository.upload(file);
        return ResponseEntity.ok(url);
    }
}
