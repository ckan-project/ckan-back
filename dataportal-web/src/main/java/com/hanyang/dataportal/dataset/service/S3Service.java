package com.hanyang.dataportal.dataset.service;


import com.hanyang.dataportal.core.exception.FileException;
import com.hanyang.dataportal.dataset.domain.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.hanyang.dataportal.core.response.ResponseMessage.FILE_ERROR;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final S3Client s3Client;
    public Resource uploadFile(Long datasetId, MultipartFile multipartFile){

        String fileName = multipartFile.getOriginalFilename();

        //파일 형식 구하기
        assert fileName != null;
        String ext = fileName.split("\\.")[1];
        String resourceName = fileName.split("\\.")[0];

        String s3ObjectPath = datasetId + "/" + fileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(s3ObjectPath)
                .build();
        try {
            s3Client.putObject(putObjectRequest,RequestBody.fromBytes(multipartFile.getBytes()));
        } catch (IOException e) {
            throw new FileException(FILE_ERROR);
        }


        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(bucket)
                .key(s3ObjectPath)
                .build();

        return  Resource.builder().
                type(ext).
                resourceUrl(String.valueOf(s3Client.utilities().getUrl(getUrlRequest))).
                resourceName(resourceName).
                build();
    }

    public void deleteFolder(Long datasetId) {
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(bucket)
                .prefix(datasetId+"/")
                .build();
        ListObjectsV2Response listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);

        List<ObjectIdentifier> objectIdentifiers = new ArrayList<>();
        for (S3Object s3Object : listObjectsResponse.contents()) {
            objectIdentifiers.add(ObjectIdentifier.builder().key(s3Object.key()).build());
        }

        Delete delete = Delete.builder().objects(objectIdentifiers).build();
        DeleteObjectsRequest deleteObjectsRequest = DeleteObjectsRequest.builder()
                .bucket(bucket)
                .delete(delete)
                .build();

        s3Client.deleteObjects(deleteObjectsRequest);

    }

}
