package com.commerzbank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.commerzbank.entity.User;
import com.commerzbank.service.UserSevice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "User Controller", description = "Controller for user operation")
public class UserController {

	private UserSevice userService;

	public UserController(UserSevice userService) {
		this.userService = userService;
	}

	@PostMapping("/save")
    @Operation(summary ="Create a new user" ,description = "Creates a new user for the application")
	public ResponseEntity<User> save(@RequestBody User user) {

		User savedUser = userService.save(user);
		return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);

	}



}
