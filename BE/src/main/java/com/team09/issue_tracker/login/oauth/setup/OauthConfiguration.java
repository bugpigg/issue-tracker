package com.team09.issue_tracker.login.oauth.setup;

import com.team09.issue_tracker.login.oauth.setup.OauthProperties.Provider;
import com.team09.issue_tracker.login.oauth.setup.OauthProperties.User;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(OauthProperties.class)
public class OauthConfiguration {

	private final OauthProperties oauthProperties;

	@Bean
	public OauthProviderInMemoryRepository oauthProviderRepository() {
		Map<String, User> users = oauthProperties.getUser();
		Map<String, Provider> providers = oauthProperties.getProvider();

		Map<String, OauthProvider> oauthProviders = new HashMap<>();

		users.keySet()
			.forEach(key -> oauthProviders.put(key,
				OauthProvider.bind(users.get(key), providers.get(key))));

		return new OauthProviderInMemoryRepository(oauthProviders);
	}
}
