package com.hanyang.datastore.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResChartTableDto {
    private List<String> label;
    private List<List<String>> dataList;

    public ResChartTableDto(List<String> label, List<List<String>> dataList) {
        this.label = label;
        this.dataList = dataList;
    }
}
