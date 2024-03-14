package com.hanyang.dataportal.core.component;

import com.hanyang.dataportal.dataset.domain.Theme;
import com.hanyang.dataportal.dataset.repository.ThemeRepository;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.hanyang.dataportal.user.domain.Role.ROLE_ADMIN;
import static com.hanyang.dataportal.user.domain.Role.ROLE_USER;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ThemeRepository themeRepository;

    @Override
    public void run(String... args){

        String rawPassword = "1234";

        if (!userRepository.existsByEmail("testAdmin")) {
            User testAdmin = User.builder().name("관리자").email("testAdmin").password(passwordEncoder.encode(rawPassword)).role(ROLE_ADMIN).build();
            userRepository.save(testAdmin);
        }

        if (!userRepository.existsByEmail("testUser")) {
            User testUser = User.builder().name("유저").email("testUser").password(passwordEncoder.encode(rawPassword)).role(ROLE_USER).build();
            userRepository.save(testUser);
        }
//        datasetRepository.deleteAll();
//        for (int i = 1900; i < 2100; i++) {
//            ReqDatasetDto reqDatasetDto = new ReqDatasetDto();
//            reqDatasetDto.setTitle("대학교 " + i + "년 입학생");
//            reqDatasetDto.setTheme(Stream.of(Theme.학생, Theme.입학).collect(Collectors.toList()));
//            reqDatasetDto.setOrganization(Organization.소프트융합대학);
//            updateDatasetService.create(reqDatasetDto);
//        }
        String[] themeList = new String[] { "입학", "학생", "학사", "국제", "복지", "재정", "취창업", "학술", "장학"};
        for (String theme:themeList) {
            themeRepository.save(Theme.builder().
                    value(theme).
                    build());
        }
    }
}
