package com.springboot.service;

import com.springboot.exception.UserAlreadyExistException;
import com.springboot.exception.UserNotFoundException;
import com.springboot.model.User;

public interface UserService {

	User findByEmail(String email);

	void save(User user);

	public User createUser(User user) throws UserAlreadyExistException;

	public boolean validateUser(String email, String password) throws UserNotFoundException;

	public User getUserById(String Id) throws UserNotFoundException;

}
