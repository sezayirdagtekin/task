package com.commerzbank.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.commerzbank.entity.User;
import com.commerzbank.repository.UserRepository;

@Service
public class UserProcessor {

	Logger log= LoggerFactory.getLogger(UserProcessor.class);
	
	private UserRepository userRepository;

	public UserProcessor(UserRepository userRepository) {

		this.userRepository = userRepository;
	}

	@Scheduled(fixedRate = 5000)
	public void processUsers() {
		var users = userRepository.findByProcessedFalse();
		for (User user : users) {

			log.info("Processing users: "+user.toString());
			user.setProcessed(true);
			userRepository.save(user);
		}

	}

}
