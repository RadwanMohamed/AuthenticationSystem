package com.blog.app.ws.service.impl;

import javax.management.RuntimeErrorException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.ws.UserRepistory;
import com.blog.app.ws.io.entity.UserEntity;
import com.blog.app.ws.service.UserService;
import com.blog.app.ws.shared.Utils;
import com.blog.app.ws.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepistory userRepistory;
	
	@Autowired
	Utils utils; 
	
	@Override
	public UserDto createUser(UserDto user) {
		
		UserEntity storedUserDetails = userRepistory.findByEmail(user.getEmail());
		if(storedUserDetails != null) throw new RuntimeException("Record Already exists");
		
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		userEntity.setEncryptedPassword("test");
		userEntity.setUserId(utils.generateUserId(30));
		storedUserDetails = userRepistory.save(userEntity);
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUserDetails, returnValue);
		return returnValue;
	}

	

}
