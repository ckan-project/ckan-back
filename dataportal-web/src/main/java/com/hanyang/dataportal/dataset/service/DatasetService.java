package com.hanyang.dataportal.dataset.service;

import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.domain.vo.Theme;
import com.hanyang.dataportal.dataset.dto.DataSearch;
import com.hanyang.dataportal.dataset.dto.req.ReqDataSearchDto;
import com.hanyang.dataportal.dataset.dto.req.ReqDatasetDto;
import com.hanyang.dataportal.dataset.dto.res.ResDatasetDetailDto;
import com.hanyang.dataportal.dataset.repository.DatasetRepository;
import com.hanyang.dataportal.dataset.repository.DatasetSearchRepository;
import com.hanyang.dataportal.dataset.repository.DatasetThemeRepository;
import com.hanyang.dataportal.resource.repository.DownloadRepository;
import com.hanyang.dataportal.user.domain.Download;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.repository.ScrapRepository;
import com.hanyang.dataportal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DatasetService {
    private final DatasetRepository datasetRepository;
    private final DatasetSearchRepository datasetSearchRepository;
    private final DatasetThemeRepository datasetThemeRepository;
    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;
    private final DownloadRepository downloadRepository;

    public Dataset create(ReqDatasetDto reqDatasetDto) {
        Dataset dataset = reqDatasetDto.toEntity();

        List<Theme> themeList = reqDatasetDto.getTheme();
        for (Theme theme : themeList) {
            dataset.addTheme(theme);
        }
        return datasetRepository.save(dataset);
    }

    public Dataset update(Long datasetId, ReqDatasetDto reqDatasetDto) {
        Dataset dataset = datasetRepository.findByIdWithTheme(datasetId).orElseThrow(() -> new ResourceNotFoundException("해당 데이터셋은 존재하지 않습니다"));
        dataset.updateDataset(reqDatasetDto);
        datasetThemeRepository.deleteAll(dataset.getDatasetThemeList());
        dataset.removeTheme();

        List<Theme> themeList = reqDatasetDto.getTheme();
        for (Theme theme : themeList) {
            dataset.addTheme(theme);
        }

        return dataset;
    }

    @Transactional(readOnly = true)
    public Page<Dataset> getDatasetList(ReqDataSearchDto reqDataSearchDto) {

        DataSearch dataSearch = DataSearch.builder().keyword(reqDataSearchDto.getKeyword()).
                organization(reqDataSearchDto.getOrganization()).
                theme(reqDataSearchDto.getTheme()).
                page(reqDataSearchDto.getPage()).
                type(reqDataSearchDto.getType()).
                sort(reqDataSearchDto.getSort()).
                build();
        return datasetSearchRepository.searchDatasetList(dataSearch);

    }

    public ResDatasetDetailDto getDatasetDetail(Long datasetId) {
        Dataset dataset = datasetRepository.findByIdWithResourceAndTheme(datasetId).orElseThrow(() -> new ResourceNotFoundException("해당 데이터셋은 존재하지 않습니다"));
        Long scrapCount = scrapRepository.countByDataset(dataset);
        dataset.updateView(dataset.getView() + 1);
        return new ResDatasetDetailDto(dataset, scrapCount.intValue());
    }

    public Dataset findById(Long id) {
        return datasetRepository.findByIdWithTheme(id).orElseThrow(() -> new ResourceNotFoundException("해당 데이터셋은 존재하지 않습니다"));
    }

    public void delete(Long id) {
        Dataset dataset = findById(id);
        datasetRepository.delete(dataset);
    }

    @Transactional(readOnly = true)
    public List<String> getByKeyword(String keyword) {
        return datasetRepository.findByTitleContaining(keyword).stream().map(Dataset::getTitle).toList();
    }

    public List<Dataset> getPopular() {
        Pageable pageable = PageRequest.of(0, 6);
        return datasetRepository.findOrderByPopular(pageable);
    }

    public List<Dataset> getNew() {
        Pageable pageable = PageRequest.of(0, 6);
        return datasetRepository.findOrderByDateDesc(pageable);
    }

    public Page<Dataset> getMyDownloadsList(UserDetails userDetails) {
//      userDatails에서 userId 값을 추출하고,  user_id에 해당하는 모든 테이블을 불러옴
        Optional<User> userInfo = userRepository.findByEmail(userDetails.getUsername());
        Long userId = userInfo.get().getUserId();
        List<Download> downloads = downloadRepository.findAllByUser_userId(userId);

        // 리스트 갯수만큼 downloads.get.datasetID()를 하여서 순차적으로 dataset 에 조회 후 페이지로 반환
        List<Long> datasetIds = downloads.stream().map(Download::getDatasetId).collect(Collectors.toList());

        // 데이터셋 ID를 사용하여 Dataset 객체 조회
        List<Dataset> datasets = datasetRepository.findAllById(datasetIds);

        // PageRequest 설정 (페이지 번호와 크기는 필요에 따라 설정)
        PageRequest pageRequest = PageRequest.of(0, 10);

        // PageImpl을 사용하여 Page 객체로 변환하여 반환
        return new PageImpl<>(datasets, pageRequest, datasets.size());


        // return Page.empty();

    }
}
