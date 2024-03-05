package com.hanyang.datastore.service;

import com.hanyang.datastore.core.exception.ResourceNotFoundException;
import com.hanyang.datastore.core.response.ResponseMessage;
import com.hanyang.datastore.dto.DatasetMetaDataDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * dataportal과 통신할때 사용하는 서비스
 */
@Service
public class DataPortalService {

    /**
     *   dataset 제목,설명,데이터셋 id 가져오기
     */
    public DatasetMetaDataDto findDataset(String datasetId){
        try {
            WebClient webClient = WebClient.create("http://localhost:8080");
            return webClient.get()
                    .uri("/dataset?resourceId=" + datasetId)
                    .retrieve()
                    .onStatus(HttpStatus.NOT_FOUND::equals, response -> {
                        throw WebClientResponseException.create(HttpStatus.NOT_FOUND.value(),
                                "Resource not found", null, null, null);
                    })
                    .bodyToMono(DatasetMetaDataDto.class)
                    .block();
        }
        catch (WebClientResponseException.NotFound e) {
            throw new ResourceNotFoundException(ResponseMessage.NOT_EXIST_DATASET);
        }

    }
}
