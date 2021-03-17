package com.springboot.test.model;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.test.BeanTester;

import com.springboot.model.User;

public class UserTest {

	private User user;

	@Before
	public void setUp() throws Exception {

		user = new User();
		user.setId("1");
		user.setName("James");
		user.setEmail("James@123.com");
		user.setPassword("EMPLOYEE");
		user.setRole("1!23#4");
	}

	@Test
	public void test() {
		new BeanTester().testBean(User.class);
	}

}
