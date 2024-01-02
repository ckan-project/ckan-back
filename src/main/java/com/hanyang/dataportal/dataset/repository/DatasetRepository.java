package com.hanyang.dataportal.dataset.repository;

import com.hanyang.dataportal.dataset.domain.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatasetRepository extends JpaRepository<Dataset,Long> {
}
