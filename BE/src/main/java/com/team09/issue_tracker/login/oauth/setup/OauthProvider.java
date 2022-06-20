package com.team09.issue_tracker.login.oauth.setup;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OauthProvider {

	private final String clientId;
	private final String clientSecret;
	private final String redirectUrl;
	private final String tokenUrl;
	private final String userInfoUrl;

	public static OauthProvider bind(OauthProperties.User user, OauthProperties.Provider provider) {
		return new OauthProvider(user.getClientId(), user.getClientSecret(), user.getRedirectUrl(),
			provider.getTokenUrl(), provider.getUserInfoUrl());
	}
}
