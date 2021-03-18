package com.springboot.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.exception.UserAlreadyExistException;
import com.springboot.exception.UserNotFoundException;
import com.springboot.model.Role;
import com.springboot.model.User;
import com.springboot.repository.RoleRepository;
import com.springboot.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserServiceImpl(UserRepository userRepository) {

		this.userRepository = userRepository;
	}

	public User createUser(User user) throws UserAlreadyExistException {
		try {

			User existingUser = getUserById(user.getId());
			if (existingUser != null) {

				throw new UserAlreadyExistException("could not exits");
			}
			User createUser = userRepository.save(user);
			if (createUser == null) {
				return null;
			}
			return createUser;

		}

		catch (Exception e) {
			// TODO Auto-generated catch block

			// e.printStackTrace();
			throw new UserAlreadyExistException("could not exists");

		}

	}

	public boolean validateUser(String email, String password) throws UserNotFoundException {
		boolean validate = userRepository.validateUser(email, password);
		if (!validate) {
			throw new UserNotFoundException("UserNotFoundException");
		}

		return true;
	}

	public User getUserById(String Id) throws UserNotFoundException {

		try {

			User getUser = userRepository.findById(Id).get();
			return getUser;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new UserNotFoundException("could not exists");

		}

	}

	public void save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		List<Role> list = new ArrayList<Role>();
		user = (User) roleRepository.findAll();
		user.setRoles(list);
		userRepository.save(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByUsername(email);
	}
}
