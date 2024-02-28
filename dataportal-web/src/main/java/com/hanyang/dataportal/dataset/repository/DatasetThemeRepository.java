package com.hanyang.dataportal.dataset.repository;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.dataset.domain.DatasetTheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DatasetThemeRepository extends JpaRepository<DatasetTheme,Long> {
    @Modifying
    @Query("DELETE FROM DatasetTheme dt WHERE dt.dataset = :dataset")
    void deleteByDataset(Dataset dataset);
}
