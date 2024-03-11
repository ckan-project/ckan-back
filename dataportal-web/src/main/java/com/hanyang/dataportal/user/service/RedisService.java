package com.hanyang.dataportal.user.service;

import com.hanyang.dataportal.core.exception.UnAuthenticationException;
import com.hanyang.dataportal.core.response.ResponseMessage;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, Object> valueOperations;

    public void setCode(String email,String code){
        //만료기간 3분
        valueOperations.set(email,code,180, TimeUnit.SECONDS);
    }

    /**
     *
     * @param email redis key 값으로 넣을 유저 이메일
     * @param code redis value 값으로 넣을 값
     * @param expire TTL (초 단위)
     */
    public void setCode(String email, String code, Long expire) {
        valueOperations.set(email, code, expire, TimeUnit.SECONDS);
    }

    public String getCode(String email){
        ValueOperations<String, Object> valOperations = redisTemplate.opsForValue();
        Object code = valOperations.get(email);
        if(code == null){
            throw new UnAuthenticationException(ResponseMessage.UN_AUTHORIZED);
        }
        return code.toString();
    }
}
