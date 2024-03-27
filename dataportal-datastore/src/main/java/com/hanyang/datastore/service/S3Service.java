package com.hanyang.datastore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final S3Client s3Client;

    public InputStream getFile(String datasetId) throws IOException {

        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(bucket)
                .prefix(datasetId+"/")
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(listObjectsRequest);
        List<S3Object> s3ObjectsList = response.contents();

        String key = null;
        for (S3Object object : s3ObjectsList) {
            key =object.key();
        }
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
        byte[] data = objectBytes.asByteArray();

        assert key != null;

        return new ByteArrayInputStream(data);
    }
}
