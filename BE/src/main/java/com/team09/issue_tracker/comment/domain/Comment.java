package com.team09.issue_tracker.comment.domain;

import com.team09.issue_tracker.common.BaseTimeEntity;
import com.team09.issue_tracker.emogi.domain.Emogi;
import com.team09.issue_tracker.issue.domain.Issue;
import com.team09.issue_tracker.member.domain.Member;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Comment extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String content;

	@ManyToOne
	@JoinColumn(name = "writer_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "issue_id")
	private Issue issue;

	@OneToMany(mappedBy = "Comment")
	private List<Emogi> emogis;
}
