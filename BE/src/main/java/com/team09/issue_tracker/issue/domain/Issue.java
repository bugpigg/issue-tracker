package com.team09.issue_tracker.issue.domain;

import com.team09.issue_tracker.common.BaseTimeEntity;
import com.team09.issue_tracker.common.CommonResponseDto;
import com.team09.issue_tracker.issue.dto.IssueDetailResponseDto;
import com.team09.issue_tracker.issue.dto.IssueListResponseDto;
import com.team09.issue_tracker.label.Label;
import com.team09.issue_tracker.milestone.Milestone;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	private Long memberId;

	@OneToMany(mappedBy = "issue", fetch = FetchType.LAZY)
	private List<IssueLabel> issueLabels = new ArrayList<>();

	@OneToMany(mappedBy = "issue", fetch = FetchType.LAZY)
	private List<IssueAssignee> issueAssignees = new ArrayList<>();

	public boolean isWriter(Long memberId) {
		return memberId.equals(this.memberId);
	}

	public void addIssueLabel(List<IssueLabel> issueLabels) {
		this.issueLabels = issueLabels;
	}

	public void addIssueAssignee(List<IssueAssignee> issueAssignees) {
		this.issueAssignees = issueAssignees;
	}

	public static Issue of(Long issueId) {
		return Issue.builder()
			.id(issueId)
			.build();
	}

	public static Issue of(String title, String content, Long memberId, boolean isOpened,
		Milestone milestone) {
		return Issue.builder()
			.title(title)
			.content(content)
			.milestone(milestone)
			.isOpened(isOpened)
			.memberId(memberId)
			.build();
	}

	public CommonResponseDto toCommonResponse() {
		return new CommonResponseDto(id);
	}

	public IssueListResponseDto toListResponse() {
		return IssueListResponseDto.builder()
			.id(id)
			.title(title)
			.content(content)
			.isOpened(isOpened)
			.milestoneTitle(Optional.ofNullable(milestone)
				.map(Milestone::getTitle)
				.orElse(""))
			.labels(issueLabels.stream()
				.map(IssueLabel::getLabel)
				.map(Label::toResponseDto)
				.collect(Collectors.toList()))
			.build();
	}

	public IssueDetailResponseDto toDetailResponse(boolean isEditable) {
		return IssueDetailResponseDto.builder()
			.id(id)
			.title(title)
			.content(content)
			.isOpened(isOpened)
			.milestoneTitle(Optional.ofNullable(milestone)
				.map(Milestone::getTitle)
				.orElse(""))
			.isEditable(isEditable)
			.build();
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setMilestone(Milestone milestone) {
		this.milestone = milestone;
	}

	public void setOpened(boolean opened) {
		isOpened = opened;
	}

}
