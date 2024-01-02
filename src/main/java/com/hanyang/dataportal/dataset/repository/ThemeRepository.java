package com.hanyang.dataportal.dataset.repository;

import com.hanyang.dataportal.dataset.domain.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme,Long> {
    Theme findByName(String name);
}
