package com.springboot.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springboot.exception.UserNotFoundException;
import com.springboot.model.User;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	public UserDaoImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean createUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
		session.flush();
		return true;
	}

	public boolean registerUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
		session.flush();
		return true;
	}

	public boolean validateUser(String email, String password) throws UserNotFoundException {
		User user = getUserById(email);
		if (user == null) {
			throw new UserNotFoundException("UserNotFoundException");
		} else {
			if (!password.equals(user.getPassword())) {
				return false;
			}
		}
		return true;
	}

	public User getUserById(String id) throws UserNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.get(User.class, id);
		if (user == null) {
			throw new UserNotFoundException("UserNotFoundException");
		}
		session.flush();
		return user;
	}

}
