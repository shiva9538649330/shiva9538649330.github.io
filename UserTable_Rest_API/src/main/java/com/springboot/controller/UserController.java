package com.springboot.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.exception.UserAlreadyExistException;
import com.springboot.model.Role;
import com.springboot.model.User;
import com.springboot.service.SecurityService;
import com.springboot.service.UserService;
import com.springboot.validator.UserValidator;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" })
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserValidator userValidator;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		return "registration";
	}

	@PostMapping("/user/register")
	public String createUser(@ModelAttribute("userForm") User userForm, BindingResult bindingResult)
			throws UserAlreadyExistException {
		userValidator.validate(userForm, bindingResult);
		List<User> userList = new ArrayList<User>();
		List<Role> roles = new ArrayList<Role>();
		Role role = new Role();
		role.setId(100L);
		role.setName("James");
		role.setUsers(userList);
		Role role2 = new Role(200l, "Peter", userList);
		Role role3 = new Role(300l, "John", userList);
		Role role4 = new Role(400l, "Fred", userList);
		roles.add(role);
		roles.add(role2);
		roles.add(role3);
		roles.add(role4);
		User user = new User();
		user.setId("1");
		user.setName("James");
		user.setEmail("James@123.com");
		user.setPassword("1!23#4");
		user.setPasswordConfirm("1!23#4");
		user.setRole("EMPLOYEE");
		user.setRoles(roles);

		User user2 = new User("2", "Peter", "Peter @123.com", "8^23!3", "8^23!3", "EMPLOYEE", roles);
		User user3 = new User("3", "John", "John @123.com", "98!891", "98!891", "ADMIN", roles);
		User user4 = new User("4", "Fred", "Fred @123.com", "68651", "68651", "ADMIN", roles);
		userList.add(user);
		userList.add(user2);
		userList.add(user3);
		userList.add(user4);

		System.out.println(" Before Sorting :" + userList);

		Collections.sort(userList);

		System.out.println(" After Sorting :" + userList);

		if (bindingResult.hasErrors()) {
			return "registration";
		}

		userService.save(userForm);

		securityService.autoLogin(userForm.getEmail(), userForm.getPasswordConfirm());
		return "redirect:/welcome";

	}

	@GetMapping("/login")
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your email and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}

	@GetMapping({ "/", "/welcome" })
	public String welcome(Model model) {
		return "welcome";
	}

	@GetMapping("/user/{id}")
	public String getUser(@PathVariable String id, User user) {
		try {
			userService.getUserById(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "userService";
	}

}
