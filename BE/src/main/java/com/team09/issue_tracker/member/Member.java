package com.team09.issue_tracker.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private MemberType type;

	private String name;

	@Column(unique = true)
	private String userId;

	private String email;

	private String imgUrl;

	@Builder
	public Member(MemberType type, String name, String userId, String email, String imgUrl) {
		this.type = type;
		this.name = name;
		this.userId = userId;
		this.email = email;
		this.imgUrl = imgUrl;
	}

	public Member update(String name, String email, String imgUrl) {
		this.name = name;
		this.email = email;
		this.imgUrl = imgUrl;
		return this;
	}
}
