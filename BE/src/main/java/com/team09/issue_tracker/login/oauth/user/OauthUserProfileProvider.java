package com.team09.issue_tracker.login.oauth.user;

import com.team09.issue_tracker.login.oauth.setup.OauthProvider;
import com.team09.issue_tracker.login.oauth.token.OauthAccessTokenResponse;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OauthUserProfileProvider {

	public static OauthUserProfile getUserProfile(OauthAccessTokenResponse tokenResponse, OauthProvider provider, String providerName) {
		Map<String, Object> userAttributes = getUserAttributes(tokenResponse, provider);
		return OauthUserProfileType.toMember(userAttributes, providerName);
	}

	private static Map<String,Object> getUserAttributes(OauthAccessTokenResponse tokenResponse, OauthProvider provider) {
		return WebClient.create()
			.get()
			.uri(provider.getUserInfoUrl())
			.headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
			.block();
	}
}
