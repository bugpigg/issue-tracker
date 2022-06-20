package com.team09.issue_tracker.login.oauth.user;

import java.util.Arrays;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OauthUserProfileType {
	GITHUB("github") {
		@Override
		public OauthUserProfile of(Map<String, Object> userAttributes, String providerName) {
			return OauthUserProfile.builder()
				.id(String.valueOf(userAttributes.get("id")))
				.email((String) userAttributes.get("email"))
				.name((String) userAttributes.get("name"))
				.providerName(providerName)
				.imgUrl((String) userAttributes.get("avatar_url"))
				.build();
		}
	};

	private final String providerName;

	public abstract OauthUserProfile of(Map<String, Object> userAttributes, String providerName);

	public static OauthUserProfile toMember(Map<String, Object> userAttributes,
		String providerName) {
		return Arrays.stream(values())
			.filter(provider -> providerName.equals(provider.providerName))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new)
			.of(userAttributes, providerName);
	}
}
