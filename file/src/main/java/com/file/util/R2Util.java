package com.file.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Slf4j
@Service
public class R2Util {
    private S3Client s3Client;
    @Value("${r2.bucket.image.name}")
    private String bucketName;
    @Value("${r2.secretAccessKey}")
    private String secretAccessKey;
    @Value("${r2.endPoint}")
    private String endPoint;
    @Value("${r2.accessKeyId}")
    private String accessKeyId;
    @Value("${r2.bucket.image.tempAccessUrl}")
    private String tempAccessUrl;

    @PostConstruct
    public void init(){
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKeyId,secretAccessKey);
        s3Client = S3Client.builder()
                .endpointOverride(URI.create(endPoint))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of("auto"))
                .build();
    }
    public String  uploadFile(MultipartFile file) throws Exception {
        String key = String.valueOf(UUID.randomUUID());
        File tempFile = convertMultipartFileToFile(file);
        String url = null;
        if(tempFile ==null){
            throw new Exception("文件上传失败");
        }
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType("image/png")
                    .build();
            s3Client.putObject(putObjectRequest,tempFile.toPath());
            url = tempAccessUrl + "/"+ key;

        }catch (S3Exception e){
            log.info(e.toString());
            throw new Exception("文件上传失败");
        }finally {
            tempFile.delete();
        }
        return url;


    }
    private File convertMultipartFileToFile(MultipartFile file) throws Exception {
        try {
            File convFile = File.createTempFile("temp", null);
            try (FileOutputStream fos = new FileOutputStream(convFile)) {
                fos.write(file.getBytes());
            }
            return convFile;
        } catch (IOException e) {
            log.info(e.toString());
            throw new Exception("文件转换失败");

        }
    }
}