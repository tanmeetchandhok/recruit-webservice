package com.recruit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class JobTitleExistException extends RuntimeException {
	public JobTitleExistException(String message) {
		super(message);
	}
}
