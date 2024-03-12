package com.hanyang.datastore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResTableLabelDto {
    private String x_axis_name;
    private List<String> x_label;
    private List<String> dataName;
    private List<List<Double>> dataList;

}
