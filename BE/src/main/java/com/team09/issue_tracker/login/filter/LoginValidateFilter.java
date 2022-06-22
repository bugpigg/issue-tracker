package com.team09.issue_tracker.login.filter;

import com.team09.issue_tracker.login.jwt.JwtTokenProvider;
import com.team09.issue_tracker.member.Member;
import com.team09.issue_tracker.member.MemberRepository;
import com.team09.issue_tracker.member.MemberService;
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
	private static final String[] VALID_FREE_URLS = {"/login/*"};
	private final JwtTokenProvider tokenProvider;
	private final MemberService memberService;

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
			addMemberIdToResponseHeader(accessToken, response);
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

	private void addMemberIdToResponseHeader(String accessToken, HttpServletResponse response) {
		String userId = (String) tokenProvider.parseClaims(accessToken).get("id");
		log.info("유저 아이디 정보---> {}", userId);

		Long id = memberService.findIdFromUserId(userId);

		response.addHeader("userId", String.valueOf(id));
	}
}
