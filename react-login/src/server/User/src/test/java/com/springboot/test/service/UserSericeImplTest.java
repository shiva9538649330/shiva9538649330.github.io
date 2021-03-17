package com.springboot.test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.springboot.dao.UserDao;
import com.springboot.exception.UserAlreadyExistException;
import com.springboot.exception.UserNotFoundException;
import com.springboot.model.User;
import com.springboot.service.UserServiceImpl;

public class UserSericeImplTest {

	@Mock
	private UserDao userDao;
	@InjectMocks
	UserServiceImpl userServiceImpl;

	private User user;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		user = new User("1", "James", "James@123.com", "1!23#4", "EMPLOYEE");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRegisterUserSuccess() throws UserAlreadyExistException, UserNotFoundException {
		when(userDao.getUserById(user.getId())).thenReturn(null);
		when(userDao.registerUser(user)).thenReturn(true);
		boolean status = userServiceImpl.registerUser(user);
		assertEquals(true, status);
		verify(userDao, times(1)).registerUser(user);
	}

	@Test(expected = UserAlreadyExistException.class)
	public void testRegisterUserFailure() throws UserAlreadyExistException, UserNotFoundException {
		when(userDao.getUserById(user.getId())).thenReturn(user);
		when(userDao.registerUser(user)).thenReturn(false);
		boolean status = userServiceImpl.registerUser(user);
		assertEquals(false, status);
		verify(userDao, times(1)).getUserById(user.getId());
	}

	@Test
	public void testGetUserByIdSuccess() throws UserNotFoundException {

		when(userDao.getUserById("1")).thenReturn(user);
		User userDetail = userServiceImpl.getUserById("1");
		assertEquals(user, userDetail);
		assertEquals(user.getId(), userDetail.getId());
		verify(userDao, times(1)).getUserById("1");

	}

	@Test(expected = UserNotFoundException.class)
	public void testGetUserByIdFailure() throws UserNotFoundException {

		when(userDao.getUserById("1")).thenReturn(null);
		User userDetail = userServiceImpl.getUserById("1");
		assertEquals(user, userDetail);
		assertEquals(user.getId(), userDetail.getId());
		verify(userDao, times(1)).getUserById("1");
	}

	@Test
	public void testValidateUserSuccess() throws UserNotFoundException {

		when(userDao.validateUser("James@123.com", "1!23#4")).thenReturn(true);
		boolean status = userServiceImpl.validateUser("James@123.com", "1!23#4");
		assertEquals(true, status);
		verify(userDao, times(1)).validateUser("James@123.com", "1!23#4");
	}

	@Test(expected = UserNotFoundException.class)
	public void testValidateUserFailure() throws UserNotFoundException {
		when(userDao.validateUser("James@123.com", "1!23#4")).thenReturn(false);
		@SuppressWarnings("unused")
		boolean status = userServiceImpl.validateUser("Jhon123", "123456");

	}

}
