package com.hanyang.datastore.controller;

import com.hanyang.datastore.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final ChatService chatService;

    @Operation(summary = "채팅시 키워드에 따른 검색 데이터 가져오기(TEST)")
    @GetMapping("/dataset")
    public ResponseEntity<?> getDatasetData(@RequestParam(required = false) String keyword){
        return ResponseEntity.ok(chatService.findByKeyword(keyword));
    }
}
