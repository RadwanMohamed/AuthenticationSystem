package com.blog.app.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.app.ws.exceptions.UserServiceException;
import com.blog.app.ws.io.entity.UserEntity;
import com.blog.app.ws.io.repistories.UserRepistory;
import com.blog.app.ws.service.UserService;
import com.blog.app.ws.shared.Utils;
import com.blog.app.ws.shared.dto.AddressDto;
import com.blog.app.ws.shared.dto.UserDto;
import com.blog.app.ws.ui.model.response.ErrorMessages;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepistory userRepistory;
	
	@Autowired
	Utils utils; 
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	@Override
	public UserDto createUser(UserDto user) {
		
		UserEntity storedUserDetails = userRepistory.findByEmail(user.getEmail());
		if(storedUserDetails != null) throw new RuntimeException("Record Already exists");
		
		for(int i=0; i<user.getAddresses().size();i++) {
			AddressDto address = user.getAddresses().get(i);
			address.setUserDetails(user);
			address.setAddressId(utils.generateAddressId(30));
			user.getAddresses().set(i, address);
		}
		
		ModelMapper modelMapper = new ModelMapper();
		// UserEntity userEntity = new UserEntity();
		//BeanUtils.copyProperties(user, userEntity);
		UserEntity userEntity = modelMapper.map(user, UserEntity.class);
		
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId(utils.generateUserId(30));
		storedUserDetails = userRepistory.save(userEntity);
		UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);
		//BeanUtils.copyProperties(storedUserDetails, returnValue);
		
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepistory.findByEmail(email);
		if(userEntity== null) throw new UsernameNotFoundException(email);
		
		
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepistory.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
	//	UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepistory.findByUserId(userId);
		if(userEntity == null) {
			throw new UsernameNotFoundException(userId);
		}
		UserDto returnValue =	new ModelMapper().map(userEntity, UserDto.class);
	//	BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDto updateUser(String userId, UserDto user) {
		UserDto returnValue= new UserDto();
		UserEntity userEntity = userRepistory.findByUserId(userId);
		if(userEntity == null) {
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		}
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		UserEntity updatedUserDetails = userRepistory.save(userEntity);
		BeanUtils.copyProperties(updatedUserDetails, returnValue);
		return returnValue;
	}

	@Override
	public void deleteUser(String userId) {
		
		UserEntity userEntity = userRepistory.findByUserId(userId);
		if(userEntity == null) {
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		}
		userRepistory.delete(userEntity);
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		List<UserDto> returnValue = new ArrayList<>();
		Pageable pageableRequest =  PageRequest.of(page, limit);
		Page<UserEntity> usersPage = userRepistory.findAll(pageableRequest);
		List<UserEntity> users = usersPage.getContent();
		for(UserEntity userEntity : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}
		return returnValue;
		
	}

	

}
