package com.blog.app.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.blog.app.ws.io.entity.AddressEntity;
import com.blog.app.ws.io.entity.UserEntity;
import com.blog.app.ws.io.repistories.AddressRepistory;
import com.blog.app.ws.io.repistories.UserRepistory;
import com.blog.app.ws.service.AddressService;
import com.blog.app.ws.shared.dto.AddressDto;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	UserRepistory userRepistory;
	@Autowired
	AddressRepistory addressRepistory;
	
	@Override
	public List<AddressDto> getAddresses(String user_id) {
		List<AddressDto> returnValue= new ArrayList<>();
		ModelMapper model = new ModelMapper();
		UserEntity userEntity = userRepistory.findByUserId(user_id);
		if(userEntity == null) return returnValue; 
		Iterable<AddressEntity> addresses = addressRepistory.findAllByUserDetails(userEntity);
		for(AddressEntity addressEntity:addresses) {
			returnValue.add(model.map(addressEntity, AddressDto.class));
		}
		return returnValue;
	}

	@Override
	public AddressDto getAddress(String addressId) {
		AddressDto returnValue = new AddressDto();
		AddressEntity addressEntity = addressRepistory.findByAddressId(addressId);
		if(addressEntity != null) {
			returnValue = new ModelMapper().map(addressEntity, AddressDto.class);
		}
		return returnValue;
	}

}
