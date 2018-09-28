package com.demo.main.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.demo.main.domain.User;
import com.demo.main.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> getAllUsers() {
		LOG.info("Fetching All Users");
		return userRepository.findAll();
	}

	/*@Override
	@Cacheable(value = "users", key = "#id")
	public User getUserById(long id) {
		LOG.info("Getting user with ID {}.", id);
		return userRepository.findOne(id);
	}*/
	
	
	
	

	@Override
	public User updateUserById(long id, User user) {
		LOG.info("Updating user with ID {}.", id);
		userRepository.save(user);
		return user;
	}

	@Override
	public void deleteUserById(long id) {
		LOG.info("Deleting User with id {}", id);
		userRepository.deleteById(id);
	}

	@Override
	public User createUser(User user) {
		LOG.info("Creating User with  {}", user);
		return userRepository.save(user);
	}

	@Cacheable(value = "users",key="#id +':'+ #division")
	public Optional<User> getUserByIdAndDivision(String id, String division) {
		LOG.info("Getting user By Id:{} and division:{}.", id,division);
		Optional<User> user=userRepository.findById(Long.valueOf(id));
		
		return user;
	}

}
