package com.team09.issue_tracker.member;

import java.util.Arrays;

public enum MemberType {

	GITHUB, APPLE, GENERAL;

	public static MemberType getEnum(String value) {
		return Arrays.stream(MemberType.values())
			.filter(memberType -> memberType.name().equalsIgnoreCase(value))
			.findFirst()
			.orElseThrow(() -> new RuntimeException("지원하지 않는 Provider 입니다!"));
	}
}
