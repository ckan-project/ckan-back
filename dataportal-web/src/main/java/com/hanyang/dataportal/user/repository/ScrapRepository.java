package com.hanyang.dataportal.user.repository;

import com.hanyang.dataportal.dataset.domain.Dataset;
import com.hanyang.dataportal.user.domain.Scrap;
import com.hanyang.dataportal.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByDatasetAndUser(Dataset dataset, User user);
    @Query("select s from Scrap s join fetch s.user u join fetch s.dataset where u.email=:email")
    List<Scrap> findAllByUserEmail(String email);
}
