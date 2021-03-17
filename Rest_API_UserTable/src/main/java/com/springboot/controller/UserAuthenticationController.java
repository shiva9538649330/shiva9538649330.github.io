package com.springboot.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.exception.UserNotFoundException;
import com.springboot.model.User;
import com.springboot.service.UserService;

@RestController
@CrossOrigin(origins={"http://localhost:4200"})
public class UserAuthenticationController {

	@Autowired
	private UserService userService;

	public UserAuthenticationController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<String> authenticateUser(@RequestBody User user, HttpSession session)
			throws UserNotFoundException {

		if (userService.validateUser(user.getEmail(), user.getPassword())) {
			session.setAttribute("logInUserId", user.getEmail());
			return new ResponseEntity<String>("If login is successful", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("If login is not successfully", HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		if (session != null && session.getAttribute("loggedInUserId") != null) {
			session.invalidate();
			return new ResponseEntity<String>("Logged Out", HttpStatus.OK);
		} else
			return new ResponseEntity<String>("User does not exists", HttpStatus.BAD_REQUEST);
	}

}
