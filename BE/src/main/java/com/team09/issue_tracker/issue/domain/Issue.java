package com.team09.issue_tracker.issue.domain;

import com.team09.issue_tracker.common.BaseTimeEntity;
import com.team09.issue_tracker.milestone.domain.Milestone;
import java.util.List;
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
	private Long id;

	private String title;

	private String content;

	private boolean isOpened;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private Milestone milestone;

	private List<Long> commentId;

	private Long memberId;
}
