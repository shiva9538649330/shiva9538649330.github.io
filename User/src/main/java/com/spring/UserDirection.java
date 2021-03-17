package com.spring;

public enum UserDirection {

	ASCENDIN("ASC");
	private final String directionCode;
	private UserDirection(String direction) {
	this.directionCode = direction;
	}
	public String getDirectionCode() {
	return this.directionCode;
	}
}
