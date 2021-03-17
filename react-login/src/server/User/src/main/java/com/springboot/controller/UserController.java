package com.springboot.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.exception.UserAlreadyExistException;
import com.springboot.model.User;
import com.springboot.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping("/user/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		try {
			userService.registerUser(user);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		} catch (UserAlreadyExistException e) {
			return new ResponseEntity<User>(user, HttpStatus.CONFLICT);
		}

	}

	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUser(@PathVariable String id, User user, HttpSession session) {
		try {
			if (session.getAttribute("loggedInUserId") != null && session.getAttribute("loggedInUserId").equals(id)) {
				if (userService.getUserById(id) == null)
					throw new Exception("not found");
				else
					return new ResponseEntity<User>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("User not found", HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
		}
	}

}
