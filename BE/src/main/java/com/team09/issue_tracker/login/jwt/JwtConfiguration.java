package com.team09.issue_tracker.login.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {

	@Value("${jwt.token.secret}")
	private String jwtSecret;

	@Value("${jwt.token.accessTokenExpireTime}")
	private String accessTokenExpireTime;

	@Value("${jwt.token.refreshTokenExpireTime}")
	private String refreshTokenExpireTime;

	@Bean
	public JwtTokenProvider jwtTokenProvider() {
		return new JwtTokenProvider(jwtSecret, Long.parseLong(accessTokenExpireTime),
			Long.parseLong(refreshTokenExpireTime));
	}


}
