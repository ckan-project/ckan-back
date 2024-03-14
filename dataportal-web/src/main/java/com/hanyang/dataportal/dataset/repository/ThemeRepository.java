package com.hanyang.dataportal.dataset.repository;

import com.hanyang.dataportal.dataset.domain.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme,Long> {
    Optional<Theme> findByValue(String value);
}
