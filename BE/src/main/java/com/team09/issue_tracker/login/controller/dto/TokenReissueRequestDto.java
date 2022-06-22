package com.team09.issue_tracker.login.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenReissueRequestDto {
	private String accessToken;
	private String refreshToken;

}
