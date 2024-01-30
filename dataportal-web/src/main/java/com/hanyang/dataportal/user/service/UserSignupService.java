package com.hanyang.dataportal.user.service;

import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.dto.req.ReqSignupDto;
import com.hanyang.dataportal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hanyang.dataportal.core.dto.ResponseMessage.DUPLICATE_EMAIL;
import static com.hanyang.dataportal.user.domain.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSignupService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    public User signUp(ReqSignupDto reqSignupDto){

        if(!userService.isExistByEmail(reqSignupDto.getEmail()) && !userService.isExistByName(reqSignupDto.getName())) {
            User user = User.builder()
                    .email(reqSignupDto.getEmail())
                    .password(passwordEncoder.encode(reqSignupDto.getPassword()))
                    .name(reqSignupDto.getName())
                    .role(ROLE_USER)
                    .build();
            return userRepository.save(user);
        }

        throw new ResourceNotFoundException(DUPLICATE_EMAIL);
    }
}
