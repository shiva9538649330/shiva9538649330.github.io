package com.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.dao.UserDao;
import com.springboot.exception.UserAlreadyExistException;
import com.springboot.exception.UserNotFoundException;
import com.springboot.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	public boolean registerUser(User user) throws UserAlreadyExistException {
		boolean register = userDao.registerUser(user);
		if (!register) {
			throw new UserAlreadyExistException("UserAlreadyExistException");
		}
		return true;
	}

	public boolean validateUser(String email, String password) throws UserNotFoundException {
		boolean validate = userDao.validateUser(email, password);
		if (!validate) {
			throw new UserNotFoundException("UserNotFoundException");
		}

		return true;
	}

	public User getUserById(String Id) throws UserNotFoundException {
		User user = userDao.getUserById(Id);
		if (user == null) {
			throw new UserNotFoundException("UserNotFoundException");
		}
		return user;
	}

}
