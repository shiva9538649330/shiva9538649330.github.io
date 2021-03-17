package com.springboot.dao;

import com.springboot.exception.UserNotFoundException;
import com.springboot.model.User;

public interface UserDao {

	public boolean createUser(User user);

	public boolean registerUser(User user);

	public boolean validateUser(String email, String password) throws UserNotFoundException;

	public User getUserById(String id) throws UserNotFoundException;

}
