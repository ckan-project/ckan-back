package com.hanyang.dataportal.dataset.repository;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.domain.Organization;
import com.hanyang.dataportal.dataset.domain.Theme;
import com.hanyang.dataportal.dataset.domain.Type;
import com.hanyang.dataportal.dataset.dto.req.DatasetSearchCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
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
                .where(titleLike(datasetSearchCond.getKeyword()),
                        organizationIn(datasetSearchCond.getOrganization()),
                        typeIn(datasetSearchCond.getType()),
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
                .limit(pageable.getPageSize()).fetch();

        Long count = queryFactory.select(dataset.count())
                .from(dataset)
                .where(titleLike(datasetSearchCond.getKeyword()),
                        organizationIn(datasetSearchCond.getOrganization()),
                        typeIn(datasetSearchCond.getType()),
                        themeIn(datasetSearchCond.getTheme()))
                .fetchOne();
        return new PageImpl<>(content, pageable, count);
    }

    private BooleanExpression titleLike(String keyword) {
        if(keyword == null){
            return null;
        }
        NumberTemplate<Double> booleanTemplateTitle = Expressions.numberTemplate(Double.class,
                "function('match',{0},{1})", dataset.title,keyword+"*");
        return booleanTemplateTitle.gt(0);
    }

    private BooleanExpression organizationIn(List<Organization> organizationList) {
        return organizationList != null ? dataset.organization.in(organizationList) : null;
    }

    private BooleanExpression themeIn(List<Theme> themeList){
        return themeList !=null ? dataset.datasetThemeList.any().theme.in(themeList) : null;
    }

    private BooleanExpression typeIn(List<Type> typeList) {
        return typeList != null ? dataset.resource.type.in(typeList) : null;
    }
}
