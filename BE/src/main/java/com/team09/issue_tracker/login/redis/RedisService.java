package com.team09.issue_tracker.login.redis;

import com.team09.issue_tracker.login.jwt.JwtTokenProvider;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisService {

	private final RedisTemplate<String, String> redisTemplate;
	private final JwtTokenProvider tokenProvider;

	public void setValue(String key, String data) {
		ValueOperations<String, String> values = redisTemplate.opsForValue();
		values.set(key, data, tokenProvider.refreshTokenExpireTime, TimeUnit.MILLISECONDS);
	}

	public String getValue(String key) {
		ValueOperations<String, String> values = redisTemplate.opsForValue();
		return values.get(key);
	}

	public void deleteValue(String key) {
		redisTemplate.delete(key);
	}
}
