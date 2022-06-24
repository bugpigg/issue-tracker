package com.team09.issue_tracker.issue.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SelectableLabelResponse {

	private Long labelId;
	private String title;
	private String backgroundColor;
	private Boolean darkMode;
	private Long memberId;
	private Long issueId;

}
