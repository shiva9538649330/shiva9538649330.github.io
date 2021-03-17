package com.springboot.exception;

public class UserSortingException extends RuntimeException {

	private static final long serialVersionUID = -123L;
	private String errorMessage;

	public UserSortingException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public UserSortingException() {
		super();
	}

}
