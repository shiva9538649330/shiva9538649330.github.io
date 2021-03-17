package com.springboot.controller;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.OrderBy;
import com.springboot.exception.UserAlreadyExistException;
import com.springboot.exception.UserSortingException;
import com.springboot.model.User;
import com.springboot.service.UserService;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" })
public class UserController {

	@Autowired
	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping("/user/register")
	public ResponseEntity<?> registerUser(User user, @RequestParam("orderBy") String orderBy,
			@RequestParam("direction") String direction, @RequestParam("page") int page,
			@RequestParam("size") int size) {
		try {
			userService.registerUser(user);

			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		} catch (UserAlreadyExistException e) {
			return new ResponseEntity<User>(user, HttpStatus.CONFLICT);
		}

	}

	@PostMapping("value = \"/conditionalPagination\", params = { \"orderBy\", \"direction\", \"page\",\r\n"
			+ "	\"size\" }")
	@ResponseBody
	public Page<User> findJsonDataByPageAndSize(@RequestParam("orderBy") String orderBy,
			@RequestParam("direction") String direction, @RequestParam("page") int page,
			@RequestParam("size") int size) {

		if (!(direction.equals(Direction.ASCENDING.getDirectionCode()))) {
			throw new UserSortingException("Invalid sort direction");
		}

		if (!(orderBy.equals(OrderBy.ID.getOrderByCode()) || orderBy.equals(OrderBy.USERID.getOrderByCode()))) {
			throw new UserSortingException("Invalid orderBy condition");
		}
		Page<User> list = userService.findJsonDataByCondition(orderBy, direction, page, size);
		return list;

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
