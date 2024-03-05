package com.hanyang.datastore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanyang.datastore.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class WebhookController {

    private final ChatService chatService;

    @PostMapping("/webhook")
    public String webhook(@RequestBody String requestBody) throws JsonProcessingException {
        System.out.println("Request Body: " + requestBody);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(requestBody);

        // "parameters" 객체 가져오기
        JsonNode parametersNode = rootNode.path("queryResult").path("parameters");
        String keyword = parametersNode.path("keyword").asText();
        System.out.println(keyword);
        String response = chatService.findByKeyword(keyword);
        String responseJson ="{" +
                "  \"fulfillmentMessages\": [" +
                "    {" +
                "      \"text\": {" +
                "        \"text\": [" +
                "          \""+response+"\"" +
                "        ]" +
                "      }" +
                "    }" +
                "  ]" +
                "}";
        return responseJson;


    }
}
