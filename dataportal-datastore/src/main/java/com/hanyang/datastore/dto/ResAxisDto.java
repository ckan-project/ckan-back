package com.hanyang.datastore.dto;

import lombok.Data;

import java.util.List;
@Data
public class ResAxisDto {
    List<String> axis;

    public ResAxisDto(List<String> axis) {
        this.axis = axis;
    }
}
