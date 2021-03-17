package com.spring;

public enum OrderBy {

	ID("id"), USERID("Id");
	private String OrderByCode;

	private OrderBy(String orderBy) {
		this.OrderByCode = orderBy;
	}

	public String getOrderByCode() {
		return this.OrderByCode;
	}

}
