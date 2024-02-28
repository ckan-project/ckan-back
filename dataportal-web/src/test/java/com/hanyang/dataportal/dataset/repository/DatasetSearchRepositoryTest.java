package com.hanyang.dataportal.dataset.repository;

import com.hanyang.dataportal.TestQueryDSLConfig;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.domain.Organization;
import com.hanyang.dataportal.dataset.domain.Theme;
import com.hanyang.dataportal.dataset.dto.req.DatasetSearchCond;
import com.hanyang.dataportal.dataset.utill.DatasetSort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Sql({"/schema.sql"})
@ActiveProfiles("test")
@DataJpaTest
@Import(TestQueryDSLConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DatasetSearchRepositoryTest {
    @Autowired
    private DatasetRepository datasetRepository;
    @Autowired
    private DatasetSearchRepository datasetSearchRepository;
    @BeforeEach
    void setUp() {
        List<Dataset> datasetList = new ArrayList<>();
        for (int i = 2000; i < 2021; i++) {
            Dataset.DatasetBuilder da = Dataset.builder().
                    title("대학교 " + i + "년도 입학생").
                    view(1000-i);
            if(i<2013){
                da.organization(Organization.소프트융합대학);
            }
            else{
                da.organization(Organization.공과대학);
            }

            Dataset dataset = da.build();
            dataset.addTheme(Theme.입학);
            if(i<2010){
                dataset.addTheme(Theme.장학);
            }
            else{
                dataset.addTheme(Theme.재정);
            }

            datasetList.add(dataset);
        }
        datasetRepository.saveAll(datasetList);
    }

    @DisplayName("페이징 테스트")
    @Test
    void searchDatasetListPaging() {
        //given
        DatasetSearchCond datasetSearchCond = new DatasetSearchCond();
        datasetSearchCond.setPage(0);
        datasetSearchCond.setSort(DatasetSort.최신);
        //when
        Page<Dataset> datasets = datasetSearchRepository.searchDatasetList(datasetSearchCond);

        //then
        Assertions.assertThat(datasets.getTotalElements()).isEqualTo(21);
        Assertions.assertThat(datasets.getTotalPages()).isEqualTo(3);
        Assertions.assertThat(datasets.getContent().size()).isEqualTo(10);
    }

    //TODO 테스트 오류,swagger는 잘됨
    @DisplayName("데이터셋 KeyWord 테스트")
    @Test
    void searchDatasetListKeyWord() {
        //given
        DatasetSearchCond datasetSearchCond = new DatasetSearchCond();
        datasetSearchCond.setPage(0);
        datasetSearchCond.setKeyword("대");
        datasetSearchCond.setSort(DatasetSort.최신);
        //when
        Page<Dataset> datasets = datasetSearchRepository.searchDatasetList(datasetSearchCond);

        //then
        Assertions.assertThat(datasets.getTotalElements()).isEqualTo(10);
        Assertions.assertThat(datasets.getTotalPages()).isEqualTo(1);
    }

    @DisplayName("데이터셋 Organization 테스트")
    @Test
    void searchDatasetListOrganization() {
        //given
        DatasetSearchCond datasetSearchCond = new DatasetSearchCond();
        datasetSearchCond.setPage(0);
        datasetSearchCond.setOrganization(Stream.of(Organization.소프트융합대학, Organization.경상대학).collect(Collectors.toList()));
        datasetSearchCond.setSort(DatasetSort.최신);
        //when
        Page<Dataset> datasets = datasetSearchRepository.searchDatasetList(datasetSearchCond);

        //then
        Assertions.assertThat(datasets.getTotalElements()).isEqualTo(13);
        Assertions.assertThat(datasets.getTotalPages()).isEqualTo(2);
    }

    @DisplayName("데이터셋 Theme 테스트")
    @Test
    void searchDatasetListTheme() {
        //given
        DatasetSearchCond datasetSearchCond = new DatasetSearchCond();
        datasetSearchCond.setPage(0);
        datasetSearchCond.setTheme(Collections.singletonList(Theme.입학));
        datasetSearchCond.setSort(DatasetSort.최신);

        //when
        Page<Dataset> datasets = datasetSearchRepository.searchDatasetList(datasetSearchCond);

        //then
        Assertions.assertThat(datasets.getTotalElements()).isEqualTo(21);
        Assertions.assertThat(datasets.getTotalPages()).isEqualTo(3);
    }

    @DisplayName("데이터셋 Sort 조회 테스트")
    @Test
    void searchDatasetListSort() {
        //given
        DatasetSearchCond datasetSearchCond = new DatasetSearchCond();
        datasetSearchCond.setPage(0);
        datasetSearchCond.setSort(DatasetSort.조회);

        //when
        Page<Dataset> datasets = datasetSearchRepository.searchDatasetList(datasetSearchCond);

        //then
        Assertions.assertThat(datasets.getTotalElements()).isEqualTo(21);
        Assertions.assertThat(datasets.getTotalPages()).isEqualTo(3);
        Assertions.assertThat(datasets.getContent().get(0).getView()).isGreaterThan(datasets.getContent().get(1).getView());
    }
}