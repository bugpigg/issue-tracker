package com.team09.issue_tracker.login.filter;

import com.team09.issue_tracker.login.jwt.JwtTokenProvider;
import com.team09.issue_tracker.member.MemberService;
import javax.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class FilterConfiguration {

	private final JwtTokenProvider tokenProvider;
	private final MemberService memberService;

	@Bean
	public FilterRegistrationBean<Filter> loginValidateFilter() {
		LoginValidateFilter validateFilter = new LoginValidateFilter(tokenProvider, memberService);
		return new FilterRegistrationBean<>(validateFilter);
	}

}
