package com.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.springboot.exception.UserAlreadyExistException;
import com.springboot.exception.UserNotFoundException;
import com.springboot.model.User;

public interface UserService {


	public boolean registerUser(User user) throws UserAlreadyExistException;

	public boolean validateUser(String email, String password) throws UserNotFoundException;

	public User getUserById(String Id) throws UserNotFoundException;

	public Page<User> findJsonDataByCondition(String orderBy, String direction, int page, int size);

}
