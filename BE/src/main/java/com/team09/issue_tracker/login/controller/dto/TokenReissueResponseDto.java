package com.team09.issue_tracker.login.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenReissueResponseDto {

	private String accessToken;
	private String refreshToken;

	@Builder
	public TokenReissueResponseDto(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
