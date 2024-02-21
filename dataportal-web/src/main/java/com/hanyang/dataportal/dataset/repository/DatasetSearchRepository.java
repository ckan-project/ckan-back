package com.hanyang.dataportal.dataset.repository;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.domain.Organization;
import com.hanyang.dataportal.dataset.domain.Theme;
import com.hanyang.dataportal.dataset.dto.DatasetSearchCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hanyang.dataportal.dataset.domain.QDataset.dataset;
import static com.hanyang.dataportal.dataset.domain.QDatasetTheme.datasetTheme;
import static com.hanyang.dataportal.dataset.domain.QResource.resource;
import static com.hanyang.dataportal.user.domain.QScrap.scrap;


@Repository
@RequiredArgsConstructor
public class DatasetSearchRepository {
    private final JPAQueryFactory queryFactory;

    public Page<Dataset> searchDatasetList(DatasetSearchCond datasetSearchCond){
        Pageable pageable = PageRequest.of(datasetSearchCond.getPage(), 10);
        JPAQuery<Dataset> query = queryFactory.selectFrom(dataset)
                .leftJoin(dataset.resource, resource).fetchJoin()
                .leftJoin(dataset.datasetThemeList, datasetTheme).fetchJoin()
                .where(organizationEq(datasetSearchCond.getOrganization()),
                        titleLike(datasetSearchCond.getKeyword()),
                        themeIn(datasetSearchCond.getTheme()));

        switch (datasetSearchCond.getSort().name()) {
            case "스크랩" -> {
                query.leftJoin(dataset.scrapList, scrap).
                        orderBy(dataset.scrapList.size().desc());
            }
            case "조회" -> {
                query.orderBy(dataset.view.desc());
            }
            case "다운로드" -> {
                query.orderBy(dataset.download.desc());
            }
            default -> {
                query.orderBy(dataset.createdDate.desc());
            }
        }

        List<Dataset> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory.select(dataset.count())
                .from(dataset)
                .where(organizationEq(datasetSearchCond.getOrganization()),
                        titleLike(datasetSearchCond.getKeyword()),
                        themeIn(datasetSearchCond.getTheme()))
                .fetchOne();
        return new PageImpl<>(content, pageable, count);
    }

    private BooleanExpression organizationEq(Organization organization) {
        return organization != null ? dataset.organization.eq(organization) : null;
    }

    private BooleanExpression titleLike(String keyword) {
        return keyword != null ? dataset.title.contains(keyword) : null;
    }

    private BooleanExpression themeIn(List<Theme> themeList){
        return themeList !=null ? dataset.datasetThemeList.any().theme.in(themeList) : null;
    }
}
