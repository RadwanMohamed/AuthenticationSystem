package com.blog.app.ws.service;

import java.util.List;

import com.blog.app.ws.shared.dto.AddressDto;

public interface AddressService {

	List<AddressDto> getAddresses(String user_id);
	AddressDto getAddress(String addressId);
	
}
