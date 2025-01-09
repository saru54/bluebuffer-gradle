package com.file.domain;

import org.springframework.web.multipart.MultipartFile;

public interface IImageRepository {
    String upload(MultipartFile file) throws Exception;
}
