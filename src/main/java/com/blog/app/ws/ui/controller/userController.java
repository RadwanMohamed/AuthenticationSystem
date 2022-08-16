package com.blog.app.ws.ui.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.ws.exceptions.UserServiceException;
import com.blog.app.ws.service.AddressService;
import com.blog.app.ws.service.UserService;
import com.blog.app.ws.shared.dto.AddressDto;
import com.blog.app.ws.shared.dto.UserDto;
import com.blog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.blog.app.ws.ui.model.response.AddressRest;
import com.blog.app.ws.ui.model.response.ErrorMessages;
import com.blog.app.ws.ui.model.response.OperationStatusModel;
import com.blog.app.ws.ui.model.response.RequestOperationStatus;
import com.blog.app.ws.ui.model.response.UserRest;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/users")
public class userController {

	@Autowired
	UserService userService;

	@Autowired
	AddressService addressesService;

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest getUser(@PathVariable String id) {

		UserDto userDto = userService.getUserByUserId(id);

		// BeanUtils.copyProperties(userDto, returnValue);
		UserRest returnValue = new ModelMapper().map(userDto, UserRest.class);
		return returnValue;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_ATOM_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })

	public UserRest createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) throws Exception {
		if (userDetails.getFirstName().isEmpty())
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		UserRest returnValue = new UserRest();
		// UserDto userDto = new UserDto();
		// BeanUtils.copyProperties(userDetails, userDto);
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		UserDto createadUser = userService.createUser(userDto);
		// BeanUtils.copyProperties(createadUser, returnValue);
		returnValue = modelMapper.map(createadUser, UserRest.class);
		return returnValue;
	}

	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_ATOM_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
		UserRest returnValue = new UserRest();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		UserDto updatedUser = userService.updateUser(id, userDto);
		BeanUtils.copyProperties(updatedUser, returnValue);
		return returnValue;
	}

	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		userService.deleteUser(id);
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}

	@GetMapping(produces = { MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public List<UserRest> getUser(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "2") int limit) {
		if (page > 0)
			page--;
		List<UserRest> returnValue = new ArrayList<>();
		List<UserDto> users = userService.getUsers(page, limit);
		for (UserDto userDto : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}
		return returnValue;
	}

	@GetMapping(path = "/{id}/addresses", produces = { MediaType.APPLICATION_ATOM_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, "application/hal+json" })
	public CollectionModel<AddressRest> getUserAddresses(@PathVariable String id) {
		List<AddressRest> returnValue = new ArrayList<>();
		List<AddressDto> addressesDto = addressesService.getAddresses(id);
		
		if (addressesDto != null && !addressesDto.isEmpty()) {
			Type listType = new TypeToken<List<AddressRest>>() {
			}.getType();
			returnValue = new ModelMapper().map(addressesDto, listType);
			for (AddressRest adddressRest : returnValue) {
				Link addressLink = linkTo(methodOn(userController.class).getUserAddress(id, adddressRest.getAddressId())).withSelfRel();
				adddressRest.add(addressLink);
				Link userLink    = linkTo(methodOn(userController.class).getUser(id)).withRel("user");
				adddressRest.add(userLink);
			}
		}

		return  CollectionModel.of(returnValue);
	}

	@GetMapping(path = "/{id}/addresses/{addressId}", produces = { MediaType.APPLICATION_ATOM_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public EntityModel<AddressRest>getUserAddress(@PathVariable String id, @PathVariable String addressId) {
		AddressDto addressesDto = addressesService.getAddress(addressId);

		AddressRest addressRest = new ModelMapper().map(addressesDto, AddressRest.class);

		Link addressLink = linkTo(methodOn(userController.class).getUserAddress(id, addressId)).withSelfRel();
		Link userLink    = linkTo(methodOn(userController.class).getUser(id)).withRel("user");
		Link addressesLink = linkTo(methodOn(userController.class).getUserAddresses(id)).withRel("addresses");

		addressRest.add(addressLink);
		addressRest.add(addressesLink);
		addressRest.add(userLink);

		return EntityModel.of(addressRest);
	}

}
