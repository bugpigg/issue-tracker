package com.team09.issue_tracker.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IssueLabelNotFoundException extends NotFoundException {

	private final Long issueId;
	private final Long labelId;

	@Override
	protected String getBodyMessage() {
		return "존재하지 않는 issueLabel 입니다. issueId:" + issueId + ", labelId:" + labelId;
	}
}
