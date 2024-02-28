package com.hanyang.datastore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ResTableDto {
    private List<String> col;
    private List<RowDto> rows;

    @Data
    public static class RowDto {
        private String id;
        List<String> row = new ArrayList<>();
        public void add(String value){
            row.add(value);
        }
    }
}
