package com.hanyang.datastore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ResTableLabelDto {
    private String labelName;
    private List<String> labelList;
    private String dataName;
    private ArrayList<Double> dataList = new ArrayList<>();
}
