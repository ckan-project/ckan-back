package com.hanyang.datastore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResTableDto {
    private String labelName;
    private List<String> labelList;
    private List<String> dataName;
    private String[][] dataList;

}
