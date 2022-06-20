package com.team09.issue_tracker.login.oauth.setup;

import java.util.HashMap;
import java.util.Map;

public class OauthProviderInMemoryRepository {

	private final Map<String, OauthProvider> providers;

	public OauthProviderInMemoryRepository(
		Map<String, OauthProvider> providers) {
		this.providers = new HashMap<>(providers);
	}

	public OauthProvider findByProviderName(String name) {
		return providers.get(name);
	}
}
