package com.team09.issue_tracker.issue;

import com.team09.issue_tracker.comment.Comment;
import com.team09.issue_tracker.common.BaseTimeEntity;
import com.team09.issue_tracker.member.Member;
import com.team09.issue_tracker.milestone.Milestone;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Issue extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "issue_id")
	private Long id;

	private String title;

	private String content;

	private boolean isOpened;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "milestone_id")
	private Milestone milestone;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member writer;
	
}
