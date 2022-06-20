package com.team09.issue_tracker.login.oauth.setup;

import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@NoArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "oauth")
public class OauthProperties {

	private final Map<String, User> user = new HashMap<>();

	private final Map<String, Provider> provider = new HashMap<>();

	@Getter
	public static class User {

		private String clientId;
		private String clientSecret;
		private String redirectUrl;

		@Builder
		public User(String clientId, String clientSecret, String redirectUrl) {
			this.clientId = clientId;
			this.clientSecret = clientSecret;
			this.redirectUrl = redirectUrl;
		}
	}

	@Getter
	public static class Provider {

		private String tokenUrl;
		private String userInfoUrl;

		@Builder
		public Provider(String tokenUrl, String userInfoUrl) {
			this.tokenUrl = tokenUrl;
			this.userInfoUrl = userInfoUrl;
		}
	}
}
