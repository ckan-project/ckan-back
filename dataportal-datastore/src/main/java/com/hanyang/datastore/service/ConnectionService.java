package com.hanyang.datastore.service;

import com.hanyang.datastore.dto.ResourceDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Transactional
public class ConnectionService {

    public void sendResourceData(Long datasetId, ResourceDto resourceDto){

        WebClient webClient = WebClient.create("http://127.0.0.1:8080");
        System.out.println(resourceDto.getResourceUrl());

        // POST 요청 보내기
        String requestBody = "{\"resourceId\":\""+resourceDto.getResourceId()+ "\"," +
                "\"resourceUrl\":\""+resourceDto.getResourceUrl()+"\"," +
                "\"type\":\""+resourceDto.getType()+"\"}";

        webClient.post()
                .uri("/dataset/"+datasetId+"/resource")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(responseBody -> {
                    System.out.println("Response: " + responseBody);
                });
    }

}
