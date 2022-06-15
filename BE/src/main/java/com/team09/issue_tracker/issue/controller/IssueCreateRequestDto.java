package com.team09.issue_tracker.issue.controller;

import com.team09.issue_tracker.label.domain.Label;
import com.team09.issue_tracker.member.domain.Member;
import com.team09.issue_tracker.milestone.domain.Milestone;
import java.util.List;

public class IssueCreateRequestDto {

	private String title;
	private String content;
	private List<Label> label;
	private List<Milestone> milestone;
	private List<Member> assignee;

}
