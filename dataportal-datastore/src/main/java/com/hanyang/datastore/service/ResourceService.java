package com.hanyang.datastore.service;

import com.hanyang.datastore.core.exception.ResourceNotFoundException;
import com.hanyang.datastore.core.response.ResponseMessage;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ResourceService {
    public void existFindById(String resourceId){
        try {
            // 요청을 보낼 URL 설정
            URL url = new URL("http://localhost:8080/resource?resourceId="+resourceId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int statusCode = conn.getResponseCode();

            if(statusCode == 404) {
                throw new ResourceNotFoundException(ResponseMessage.NOT_EXIST_RESOURCE);
            }
            conn.disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
