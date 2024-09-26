package com.commerzbank.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.commerzbank.entity.User;
import com.commerzbank.repository.UserRepository;

@Service
public class UserSevice {
	Logger log = LoggerFactory.getLogger(UserSevice.class);

	private UserRepository userRepository;

	public UserSevice(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User save(User user) {
		log.info("saving user:" + user.getName() + " " + user.getSurname());
		return userRepository.save(user);
	}

}
