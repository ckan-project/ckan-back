package com.hanyang.dataportal.dataset.repository;

import com.hanyang.dataportal.dataset.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource,Long> {
}
