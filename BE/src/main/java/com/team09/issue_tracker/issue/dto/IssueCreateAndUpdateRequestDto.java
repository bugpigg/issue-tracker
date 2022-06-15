package com.team09.issue_tracker.issue.dto;

import com.team09.issue_tracker.label.Label;
import com.team09.issue_tracker.member.Member;
import com.team09.issue_tracker.milestone.Milestone;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueCreateAndUpdateRequestDto {

	private String title;
	private String content;
	private List<Label> label;
	private Milestone milestone;
	private List<Member> assignee;

}
