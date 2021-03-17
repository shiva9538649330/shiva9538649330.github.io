package com.springboot.dao;

import com.springboot.exception.UserNotFoundException;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.springboot.model.User;

@Repository
public interface UserDao  extends PagingAndSortingRepository<User, Integer> {


	public boolean createUser(User user);

	public boolean registerUser(User user);

	public boolean validateUser(String email, String password) throws UserNotFoundException;

	public User getUserById(String id) throws UserNotFoundException;

}
