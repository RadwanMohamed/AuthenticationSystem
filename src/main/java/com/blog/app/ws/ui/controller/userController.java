package com.blog.app.ws.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.ws.service.UserService;
import com.blog.app.ws.shared.dto.UserDto;
import com.blog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.blog.app.ws.ui.model.response.UserRest;


@RestController
@RequestMapping("users")
public class userController {

	@Autowired
	UserService userService;
	@GetMapping
	public String getUser() {
		return "hello users";
	}
	
	@PostMapping
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
		UserRest returnValue = new UserRest();
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		UserDto createadUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createadUser, returnValue);
		
		return returnValue;
	}
	@PutMapping
	public String updateUser() {
		return "hello users";
	}
	@DeleteMapping
	public String deleteUser() {
		return "hello users";
	}
}
