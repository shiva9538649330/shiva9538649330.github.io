package com.springboot.service;

public interface SecurityService {

	String findLoggedInUsername();

	void autoLogin(String email, String password);
}
