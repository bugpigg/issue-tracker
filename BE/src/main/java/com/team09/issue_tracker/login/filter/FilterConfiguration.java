package com.team09.issue_tracker.login.filter;

import com.team09.issue_tracker.login.jwt.JwtTokenProvider;
import javax.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class FilterConfiguration {

	private final JwtTokenProvider tokenProvider;

	@Bean
	public FilterRegistrationBean<Filter> loginValidateFilter() {
		LoginValidateFilter validateFilter = new LoginValidateFilter(tokenProvider);
		return new FilterRegistrationBean<>(validateFilter);
	}

}
