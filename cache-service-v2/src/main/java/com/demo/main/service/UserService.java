package com.demo.main.service;

import java.util.List;
import java.util.Optional;

import com.demo.main.domain.User;

public interface UserService {


	/*User getUserById(long id);*/
	
	 Optional<User> getUserByIdAndDivision(String id,String division);

	User updateUserById(long id, User user);

	void deleteUserById(long id);
	
	User createUser(User user);

	List<User> getAllUsers();

	

	
}
