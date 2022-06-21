package com.team09.issue_tracker.login.filter;

import com.team09.issue_tracker.login.jwt.JwtTokenProvider;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class LoginValidateFilter extends OncePerRequestFilter {

	private static final String HEADER_AUTHORIZATION = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";
	private static final String[] VALID_FREE_URLS = {"/login/oauth/*"};

	private final JwtTokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String requestUrl = request.getRequestURI();

		if (isValidRequiredUrl(requestUrl)) {
			log.info("로그인 인증 유무 확인 로직 실행 ---> {}", requestUrl);
			String accessToken = getAccessToken(request);
			if (!tokenProvider.validateToken(accessToken)) {
				throw new RuntimeException("유효하지 않은 JWT 토큰입니다!!");
			}
		}
		filterChain.doFilter(request, response);
	}

	private boolean isValidRequiredUrl(String requestUrl) {
		return !PatternMatchUtils.simpleMatch(VALID_FREE_URLS, requestUrl);
	}

	private String getAccessToken(HttpServletRequest request) {
		String header = Optional.ofNullable(request.getHeader(HEADER_AUTHORIZATION))
			.orElseThrow(() -> new RuntimeException("JWT 토큰이 필요합니다!!"));

		return header.substring(TOKEN_PREFIX.length());
	}
}
