package com.team09.issue_tracker.login.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {

	private Long userId;
	private String imgUrl;
	private String accessToken;
	private String refreshToken;

	@Builder
	public LoginResponseDto(Long userId, String imgUrl, String accessToken,
		String refreshToken) {
		this.userId = userId;
		this.imgUrl = imgUrl;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
