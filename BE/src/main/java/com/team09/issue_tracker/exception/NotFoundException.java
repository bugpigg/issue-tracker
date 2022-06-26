package com.team09.issue_tracker.exception;

import org.springframework.http.HttpStatus;

public abstract class NotFoundException extends BusinessException {

	@Override
	protected HttpStatus getHttpStatus() {
		return HttpStatus.NOT_FOUND;
	}

}
