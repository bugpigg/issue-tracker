package com.team09.issue_tracker.exception;

public class IssueNotFoundException extends NotFoundException{

	@Override
	protected String getBodyMessage() {
		return "존재하지 않는 이슈번호입니다.";
	}
}
