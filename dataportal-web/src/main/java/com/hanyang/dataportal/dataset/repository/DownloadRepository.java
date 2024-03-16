package com.hanyang.dataportal.dataset.repository;

import com.hanyang.dataportal.user.domain.Download;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DownloadRepository extends JpaRepository<Download,Long> {
}
