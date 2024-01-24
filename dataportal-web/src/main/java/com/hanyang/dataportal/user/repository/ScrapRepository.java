package com.hanyang.dataportal.user.repository;

import com.hanyang.dataportal.user.domain.Scrap;
import com.hanyang.dataportal.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    List<Scrap> findAllByUser(User user);
}
