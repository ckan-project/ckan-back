package com.hanyang.dataportal.user.service;

import com.hanyang.dataportal.core.exception.ResourceExistException;
import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.dto.req.ReqSignupDto;
import com.hanyang.dataportal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hanyang.dataportal.core.response.ResponseMessage.DUPLICATE_EMAIL;
import static com.hanyang.dataportal.core.response.ResponseMessage.NOT_EXIST_USER;
import static com.hanyang.dataportal.user.domain.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException(NOT_EXIST_USER));
    }

    public User signUp(ReqSignupDto reqSignupDto){

        if(!isExistByEmail(reqSignupDto.getEmail()) && !isExistByName(reqSignupDto.getName())) {
            User user = User.builder()
                    .email(reqSignupDto.getEmail())
                    .password(passwordEncoder.encode(reqSignupDto.getPassword()))
                    .name(reqSignupDto.getName())
                    .role(ROLE_USER)
                    .build();
            return userRepository.save(user);
        }

        throw new ResourceExistException(DUPLICATE_EMAIL);
    }

    private boolean isExistByName(String name){
        return userRepository.findByName(name).isPresent();
    }

    private boolean isExistByEmail(String email){
        return userRepository.findByEmail(email).isPresent();
    }

}
