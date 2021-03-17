package com.springboot.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Date;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import com.spingboot.webConfig.ApplicationContextConfig;
import com.springboot.dao.UserDao;
import com.springboot.dao.UserDaoImpl;
import com.springboot.exception.UserNotFoundException;
import com.springboot.model.User;

@RunWith(SpringRunner.class)
@Transactional
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationContextConfig.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
public class UserDaoImplTest {

	@Autowired
	private SessionFactory sessionFactory;
	private UserDao userDao;
	private User user;

	@Before
	public void setUp() throws Exception {

		userDao = new UserDaoImpl(sessionFactory);
		user = new User("1", "James", "James@123.com", "1!23#4", "EMPLOYEE");
		Query query = sessionFactory.getCurrentSession().createQuery("DELETE from User");
		query.executeUpdate();
	}

	@After
	public void tearDown() throws Exception {

		Query query = sessionFactory.getCurrentSession().createQuery("DELETE from User");
		query.executeUpdate();
	}

	@Test
	@Rollback(true)
	public void testRegisterUserSuccess() {
		userDao.registerUser(user);
		User fetchedUser = userDao.getUserById("1");
		assertEquals(user, fetchedUser);
	}

	@Test
	@Rollback(true)
	public void testRegisterUserFailure() {
		userDao.registerUser(user);
		User fetchedUser = userDao.getUserById("1");
		assertNotEquals("George3706", fetchedUser.getId());
	}

	@Test
	public void testGetUserByIdSuccess() {

		userDao.registerUser(user);
		User fetchedUser = userDao.getUserById("1");
		assertEquals(user, fetchedUser);
	}

	@Test
	public void testGetUserByIdFailure() {

		userDao.registerUser(user);
		User fetchedUser = userDao.getUserById("1");
		assertNotEquals("George123", fetchedUser.getId());
	}

	@Test
	public void testValidateUserSuccess() throws UserNotFoundException {

		userDao.registerUser(user);
		boolean status = userDao.validateUser("James@123.com", "1!23#4");
		assertEquals(true, status);

	}

	@Test(expected = UserNotFoundException.class)
	public void testValidateUserFailure() throws UserNotFoundException {

		userDao.registerUser(user);
		@SuppressWarnings("unused")
		boolean validatedUser = userDao.validateUser("James@123.com", "1!23#4");

	}
}
