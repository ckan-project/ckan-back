package com.hanyang.dataportal.dataset.repository;

import com.hanyang.dataportal.dataset.domain.Dataset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DatasetRepository extends JpaRepository<Dataset,Long> {
    Page<Dataset> findByTitleContainingAndThemeAndOrganization(String title,String theme,String organization,Pageable pageable);

    @Query("select d from Dataset d join fetch d.resource r where d.datasetId = :datasetId")
    Optional<Dataset> findByIdWithResource(Long datasetId);

}
