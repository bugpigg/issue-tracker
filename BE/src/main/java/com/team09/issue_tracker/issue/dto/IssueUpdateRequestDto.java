package com.team09.issue_tracker.issue.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueUpdateRequestDto {

	private String title;
	private String content;
	private Long milestoneId;
	private List<Long> labelIds;
	private List<Long> assigneeIds;
	private boolean opened;

}
