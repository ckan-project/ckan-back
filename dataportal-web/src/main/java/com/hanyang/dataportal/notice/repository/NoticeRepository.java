package com.hanyang.dataportal.notice.repository;

import com.hanyang.dataportal.notice.domain.Notice;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Long> {

    // JpaRepository 기본제공 메서드(기능)
    // 1. Save, Find(findById(Id id)/findAll()), Dele(deleteByid(Id id), Delete(T entity)

    /* notice 번호를 통해 해당하는 Notice를 찾는 역할을 한다.
        반환타입은 Notice이고, 찾는 게시글이 있을수도 없을수도 있다.
     */
Optional<Notice> findNoticeByNoticeId(Long noticeId);
}
