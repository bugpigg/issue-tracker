package com.team09.issue_tracker.login.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenProvider {

	private final Key key;
	private final long accessTokenExpireTime;
	public final long refreshTokenExpireTime;

	public JwtTokenProvider(String secret, long accessTokenExpireTime,
		long refreshTokenExpireTime) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.accessTokenExpireTime = accessTokenExpireTime;
		this.refreshTokenExpireTime = refreshTokenExpireTime;
	}

	public JwtToken createJwtToken(String id, String userName) {
		return new JwtToken(key, accessTokenExpireTime, refreshTokenExpireTime, id, userName);
	}

	public boolean validateToken(String token) {
		return parseClaims(token) != null;
	}

	public Claims parseClaims(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (SecurityException e) {
			log.info("Invalid JWT signature.");
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT token.");
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT token.");
			throw new RuntimeException("토큰이 만료되었습니다!!");
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT token.");
		} catch (IllegalArgumentException e) {
			log.info("JWT token compact of handler are invalid.");
		}
		return null;
	}
}
