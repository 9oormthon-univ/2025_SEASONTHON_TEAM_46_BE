package jpabasic.newsthinkybe.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jpabasic.newsthinkybe.global.exception.CustomException;
import jpabasic.newsthinkybe.global.exception.ErrorCode;
import jpabasic.newsthinkybe.user.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final RedisTemplate<Object, Object> redisTemplate;

    public UserService(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void logout(HttpServletRequest req, String email) {
        //헤더로부터 token 받아오기
        String refreshToken=req.getHeader("x-refresh-token");

        Object redisToken=redisTemplate.opsForValue().get("refresh:"+email);
        if (redisToken != null && redisToken.equals(refreshToken)) {
            redisTemplate.delete("refresh:" + email);
        } else {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

    }
}
