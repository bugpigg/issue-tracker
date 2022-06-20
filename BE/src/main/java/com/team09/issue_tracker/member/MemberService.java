package com.team09.issue_tracker.member;

import com.team09.issue_tracker.login.oauth.user.OauthUserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;

	public Member saveOrUpdate(OauthUserProfile userProfile) {
		Member resultMember = memberRepository.findByUserId(userProfile.getUserId())
			.map(member -> member.update(userProfile.getName(), userProfile.getEmail(),
				userProfile.getImgUrl()))
			.orElse(userProfile.toMember());
		return memberRepository.save(resultMember);
	}

}
