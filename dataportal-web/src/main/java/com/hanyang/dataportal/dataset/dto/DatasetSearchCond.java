package com.hanyang.dataportal.dataset.dto;

import com.hanyang.dataportal.dataset.domain.Organization;
import com.hanyang.dataportal.dataset.domain.Theme;
import com.hanyang.dataportal.dataset.utill.DatasetSort;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatasetSearchCond {
    String keyword;
    Organization organization;
    List<Theme> theme;
    DatasetSort sort;
    int page;
}
