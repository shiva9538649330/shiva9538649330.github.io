package com.springboot.test.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spingboot.webConfig.ApplicationContextConfig;
import com.springboot.controller.UserController;
import com.springboot.exception.UserAlreadyExistException;
import com.springboot.model.User;
import com.springboot.service.UserService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ApplicationContextConfig.class })
@WebAppConfiguration
public class UserControllerTest {

	private MockMvc mockMvc;
	private User user;
	@Autowired
	private MockHttpSession session;
	@Mock
	private UserService userService;
	@InjectMocks
	private UserController userController = new UserController(userService);

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

		user = new User("1", "James", "James@123.com", "1!23#4", "EMPLOYEE");
		// Setting session attribute

		session.setAttribute("loggedInUserId", user.getId());
	}

	@Test
	public void testRegisterUserSuccess() throws Exception {
		when(userService.registerUser(user)).thenReturn(true);
		mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
				.andExpect(status().isCreated()).andDo(print());
	}

	@Test
	public void testRegisterUserFailure() throws UserAlreadyExistException, Exception {

		when(userService.registerUser(user)).thenThrow(UserAlreadyExistException.class);
		mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
				.andExpect(status().isConflict()).andDo(print());
	}

	@Test
	public void testGetByUserIdSuccess() throws Exception {
		when(userService.getUserById(user.getId())).thenReturn(user);
		mockMvc.perform(get("/user/{id}", user.getId()).session(session)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void testGetByUserIdFailure() throws Exception {
		when(userService.getUserById(user.getId())).thenReturn(null);
		mockMvc.perform(get("/user/{id}", user.getId()).session(session)).andExpect(status().isNotFound())
				.andDo(print());
	}

	public static String asJsonString(final Object obj) {
		try {

			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
