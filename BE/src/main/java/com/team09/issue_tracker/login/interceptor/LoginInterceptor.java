package com.team09.issue_tracker.login.interceptor;

import com.team09.issue_tracker.login.jwt.JwtTokenProvider;
import com.team09.issue_tracker.member.MemberService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginInterceptor implements HandlerInterceptor {

	private static final String HEADER_AUTHORIZATION = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";
	private final JwtTokenProvider tokenProvider;
	private final MemberService memberService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
		Object handler) throws Exception {

		String requestUrl = request.getRequestURI();

		log.info("로그인 인증 유무 확인 로직 실행 ---> {}", requestUrl);
		String accessToken = getAccessToken(request);
		if (!tokenProvider.validateToken(accessToken)) {
			throw new RuntimeException("유효하지 않은 JWT 토큰입니다!!");
		}
		addMemberIdToRequestAttribute(accessToken, request);
		return true;
	}

	private String getAccessToken(HttpServletRequest request) {
		String header = Optional.ofNullable(request.getHeader(HEADER_AUTHORIZATION))
			.orElseThrow(() -> new RuntimeException("JWT 토큰이 필요합니다!!"));
		return header.substring(TOKEN_PREFIX.length());
	}

	private void addMemberIdToRequestAttribute(String accessToken, HttpServletRequest request) {
		String userId = (String) tokenProvider.parseClaims(accessToken).get("id");
		log.info("유저 아이디 정보---> {}", userId);

		Long id = memberService.findIdFromUserId(userId);

		request.setAttribute("memberId", String.valueOf(id));
	}
}
