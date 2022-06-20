package com.team09.issue_tracker.login.oauth.user;

import com.team09.issue_tracker.member.Member;
import com.team09.issue_tracker.member.MemberType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OauthUserProfile {

	private String providerName;
	private String name;
	private String userId;
	private String email;
	private String imgUrl;

	@Builder
	public OauthUserProfile(String providerName, String name, String id, String email,
		String imgUrl) {
		this.providerName = providerName;
		this.name = name;
		this.userId = id;
		this.email = email;
		this.imgUrl = imgUrl;
	}

	public Member toMember() {
		return Member.builder()
			.type(MemberType.valueOf(providerName))
			.name(name)
			.userId(userId)
			.email(email)
			.imgUrl(imgUrl)
			.build();
	}
}
