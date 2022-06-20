package com.team09.issue_tracker.login.service;

import com.team09.issue_tracker.login.controller.dto.LoginResponseDto;
import com.team09.issue_tracker.login.jwt.JwtToken;
import com.team09.issue_tracker.login.jwt.JwtTokenProvider;
import com.team09.issue_tracker.login.oauth.setup.OauthProvider;
import com.team09.issue_tracker.login.oauth.setup.OauthProviderInMemoryRepository;
import com.team09.issue_tracker.login.oauth.token.OauthAccessTokenProvider;
import com.team09.issue_tracker.login.oauth.token.OauthAccessTokenResponse;
import com.team09.issue_tracker.login.oauth.user.OauthUserProfile;
import com.team09.issue_tracker.login.oauth.user.OauthUserProfileProvider;
import com.team09.issue_tracker.member.Member;
import com.team09.issue_tracker.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {

	private final OauthProviderInMemoryRepository oauthProviderRepository;
	private final MemberService memberService;
	private final JwtTokenProvider tokenProvider;

	public LoginResponseDto login(String providerName, String code) {

		OauthProvider oauthProvider = oauthProviderRepository.findByProviderName(providerName);

		OauthAccessTokenResponse tokenResponse = OauthAccessTokenProvider.getOauthAccessToken(
			oauthProvider, code);

		OauthUserProfile userProfile = OauthUserProfileProvider.getUserProfile(tokenResponse,
			oauthProvider, providerName);

		Member member = memberService.saveOrUpdate(userProfile);

		JwtToken token = tokenProvider.createJwtToken(member.getId(), member.getName());

		return LoginResponseDto.builder()
			.userId(member.getId())
			.imgUrl(member.getImgUrl())
			.accessToken(token.getAccessToken())
			.refreshToken(token.getRefreshToken())
			.build();
	}
}
