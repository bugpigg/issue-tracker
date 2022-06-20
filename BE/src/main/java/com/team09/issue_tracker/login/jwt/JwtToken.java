package com.team09.issue_tracker.login.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import lombok.Getter;

@Getter
public class JwtToken {

	private final String accessToken;
	private final String refreshToken;

	JwtToken(Key key, long accessExpireTime, long refreshExpireTime, Long id, String userName) {
		this.accessToken = createAccessToken(key, getExpireTime(accessExpireTime), id, userName);
		this.refreshToken = createRefreshToken(key, getExpireTime(refreshExpireTime));
	}

	private Date getExpireTime(long expireTime) {
		return new Date(getCurrentTime() + expireTime);
	}

	private long getCurrentTime() {
		return (new Date()).getTime();
	}

	private String createAccessToken(Key key, Date accessTokenExpireIn, Long id, String userName) {
		return Jwts.builder()
			.setIssuer(userName)
			.setExpiration(accessTokenExpireIn)
			.claim("id", id)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	private String createRefreshToken(Key key, Date refreshTokenExpireIn) {
		return Jwts.builder()
			.setExpiration(refreshTokenExpireIn)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}
}
