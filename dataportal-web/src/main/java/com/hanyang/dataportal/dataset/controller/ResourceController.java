package com.hanyang.dataportal.dataset.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dataset")
public class ResourceController {



    @PostMapping(value= "/{datasetId}/resource",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveResource(@PathVariable Long datasetId){

        return ResponseEntity.ok().build();
    }
}
