package com.file.infrastructure;

import com.file.domain.IImageRepository;
import com.file.util.R2Util;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageRepository implements IImageRepository {
    private final R2Util r2Util;

    public ImageRepository(R2Util r2Util) {
        this.r2Util = r2Util;
    }

    @Override
    public String upload(MultipartFile file) throws Exception {
        return r2Util.uploadFile(file);
    }
}
