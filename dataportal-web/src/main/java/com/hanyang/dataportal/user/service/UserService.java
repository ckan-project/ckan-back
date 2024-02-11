package com.hanyang.dataportal.user.service;

import com.hanyang.dataportal.core.exception.ResourceNotFoundException;
import com.hanyang.dataportal.user.domain.User;
import com.hanyang.dataportal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hanyang.dataportal.core.global.reponse.ResponseMessage.NOT_EXIST_USER;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    public boolean isExistByEmail(String email){
        return userRepository.findByEmailAndActiveTrue(email).isPresent();
    }
    public boolean isExistByName(String name){
        return userRepository.findByNameAndActiveTrue(name).isPresent();
    }
    public User findByEmail(String email){
        return userRepository.findByEmailAndActiveTrue(email).orElseThrow(()-> new ResourceNotFoundException(NOT_EXIST_USER));
    }
}
