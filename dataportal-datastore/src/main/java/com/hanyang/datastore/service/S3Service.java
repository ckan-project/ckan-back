package com.hanyang.datastore.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.hanyang.datastore.dto.ResourceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static com.hanyang.datastore.utill.RandomStringGenerator.generateRandomString;

@Service
@Transactional
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;


    //s3는 datasetId/resourceId/파일명 으로 구성되어있음
    public ResourceDto uploadFile(Long datasetId, MultipartFile multipartFile) throws IOException {

        String fileName = multipartFile.getOriginalFilename();

        //파일 형식 구하기
        String ext = fileName.split("\\.")[1];
        String contentType = switch (ext) {
            case "jpeg" -> "image/jpeg";
            case "jpg" -> "image/jpg";
            case "png" -> "image/png";
            default -> "";

            //content type을 지정해서 올려주지 않으면 자동으로 "application/octet-stream"으로 고정이 되서 링크 클릭시 웹에서 열리는게 아니라 자동 다운이 시작됨.
        };

        String resourceId = generateRandomString();

        String s3ObjectName = datasetId+ "/" +resourceId;

        try {
            byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            metadata.setContentLength(bytes.length);

            amazonS3.putObject(new PutObjectRequest(bucket, s3ObjectName, multipartFile.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (SdkClientException e) {
            e.printStackTrace();
        }

        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setResourceId(resourceId);
        resourceDto.setType(ext);
        resourceDto.setResourceUrl(String.valueOf(amazonS3.getUrl(bucket, s3ObjectName)));

        return resourceDto;
    }

    public InputStream getFile(String datasetId, String resourceId){
        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucket, datasetId + "/" + resourceId));
        return s3Object.getObjectContent();
    }
}
