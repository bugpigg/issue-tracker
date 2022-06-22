package com.team09.issue_tracker.login.service;

import com.team09.issue_tracker.login.controller.dto.LoginResponseDto;
import com.team09.issue_tracker.login.controller.dto.TokenReissueRequestDto;
import com.team09.issue_tracker.login.controller.dto.TokenReissueResponseDto;
import com.team09.issue_tracker.login.jwt.JwtToken;
import com.team09.issue_tracker.login.jwt.JwtTokenProvider;
import com.team09.issue_tracker.login.oauth.setup.OauthProvider;
import com.team09.issue_tracker.login.oauth.setup.OauthProviderInMemoryRepository;
import com.team09.issue_tracker.login.oauth.token.OauthAccessTokenProvider;
import com.team09.issue_tracker.login.oauth.token.OauthAccessTokenResponse;
import com.team09.issue_tracker.login.oauth.user.OauthUserProfile;
import com.team09.issue_tracker.login.oauth.user.OauthUserProfileProvider;
import com.team09.issue_tracker.login.redis.RedisService;
import com.team09.issue_tracker.member.Member;
import com.team09.issue_tracker.member.MemberService;
import io.jsonwebtoken.Claims;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LoginService {

	private final OauthProviderInMemoryRepository oauthProviderRepository;
	private final MemberService memberService;
	private final JwtTokenProvider tokenProvider;
	private final RedisService redisService;

	@Transactional
	public LoginResponseDto login(String providerName, String code) {
		// providerName에 해당하는 provider의 속성값을 제공하는 OauthProvider 객체
		OauthProvider oauthProvider = oauthProviderRepository.findByProviderName(providerName);

		// provider로 부터 AccessToken 반환
		OauthAccessTokenResponse tokenResponse = OauthAccessTokenProvider.getOauthAccessToken(
			oauthProvider, code);

		// AccessToken을 기반으로 사용자 정보 조회
		OauthUserProfile userProfile = OauthUserProfileProvider.getUserProfile(tokenResponse,
			oauthProvider, providerName);

		// 조회한 사용자 정보를 기반으로 Member 엔티티 생성 및 DB에 저장
		Member member = memberService.saveOrUpdate(userProfile);

		// 사용자 정보를 기반으로 JWT token 생성
		JwtToken token = tokenProvider.createJwtToken(member.getUserId(), member.getName());

		// RedisDB 에 JWT refresh Token 저장
		redisService.setValue(String.valueOf(member.getId()), token.getRefreshToken());

		return LoginResponseDto.builder()
			.userId(member.getId())
			.imgUrl(member.getImgUrl())
			.accessToken(token.getAccessToken())
			.refreshToken(token.getRefreshToken())
			.build();
	}

	@Transactional
	public TokenReissueResponseDto reissue(TokenReissueRequestDto requestDto) {
		String accessToken = requestDto.getAccessToken();
		String refreshToken = requestDto.getRefreshToken();

		// JWT 토큰의 유효성 검사
		if (!tokenProvider.validateToken(accessToken) || !tokenProvider.validateToken(refreshToken)) {
			throw new RuntimeException("Token 이 유효하지 않습니다.");
		}

		// JWT access Token 에서 Member id 가져오기
		String userId = (String) tokenProvider.parseClaims(accessToken).get("id");
		Member member = memberService.findByUserId(userId);
		Long id = member.getId();

		// Member id 기반으로 RedisDB에 저장되어 있는 JWT refresh token 가져오기
		String refreshTokenValue = Optional.ofNullable(redisService.getValue(String.valueOf(id)))
				.orElseThrow(() -> new RuntimeException("로그아웃된 사용자 입니다."));

		// refresh Token 검증
		if (!refreshToken.equals(refreshTokenValue)) {
			throw new RuntimeException("토큰의 정보가 일치하지 않습니다.");
		}

		// 새로운 토큰 생성
		JwtToken newToken = tokenProvider.createJwtToken(member.getUserId(), member.getName());

		return TokenReissueResponseDto.builder()
			.accessToken(newToken.getAccessToken())
			.refreshToken(refreshToken)
			.build();
	}
}
