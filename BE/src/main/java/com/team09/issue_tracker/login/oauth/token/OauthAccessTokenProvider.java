package com.team09.issue_tracker.login.oauth.token;

import com.team09.issue_tracker.login.oauth.setup.OauthProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OauthAccessTokenProvider {

	public static OauthAccessTokenResponse getOauthAccessToken(OauthProvider oauthProvider,
		String code) {
		return WebClient.create()
			.post()
			.uri(oauthProvider.getTokenUrl())
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.accept(MediaType.APPLICATION_JSON)
			.bodyValue(tokenRequestForm(oauthProvider, code))
			.retrieve()
			.bodyToMono(OauthAccessTokenResponse.class)
			.block();
	}

	private static MultiValueMap<String, String> tokenRequestForm(OauthProvider provider,
		String code) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("code", code);
		formData.add("client_id", provider.getClientId());
		formData.add("client_secret", provider.getClientSecret());
		return formData;
	}
}
