package com.team09.issue_tracker.issue.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SelectableMilestoneResponse {

	private Long milestoneId;
	private String title;
	private Long issueId;

}
