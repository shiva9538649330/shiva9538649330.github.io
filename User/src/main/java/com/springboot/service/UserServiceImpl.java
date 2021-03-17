package com.springboot.service;

import java.awt.print.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

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
	
	@SuppressWarnings("deprecation")
	public Page<User> findJsonDataByCondition(String orderBy, String direction, int page, int size) {
		Sort sort = null;
		if (direction.equals("ASC")) {
		sort = new Sort(new Sort.Order(Direction.ASC, orderBy));
		}
		if (direction.equals("DESC")) {
		sort = new Sort(new Sort.Order(Direction.DESC, orderBy));
		}
		PageRequest pageable = new PageRequest(page, size, sort);
		Page<User> data = userDao.findAll(pageable);
		return data;
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
