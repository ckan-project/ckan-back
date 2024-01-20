package com.hanyang.dataportal.user.repository;

import com.hanyang.dataportal.user.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Long> {

    //save는 @Repository

}
