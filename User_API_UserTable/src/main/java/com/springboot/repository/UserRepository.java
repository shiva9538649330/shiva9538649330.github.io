package com.springboot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import com.springboot.exception.UserNotFoundException;
import com.springboot.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	User findByUsername(String username);


	public boolean validateUser(String email, String password) throws UserNotFoundException;
}
