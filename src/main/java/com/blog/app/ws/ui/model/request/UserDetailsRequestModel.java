package com.blog.app.ws.ui.model.request;


import java.util.List;

import javax.validation.constraints.Email;

import org.springframework.web.multipart.MultipartFile;

public class UserDetailsRequestModel {

	
	
	private String firstName;
	private String lastName;
//	private List <AddressRequestModel> addresses;
	
	@Email(message = "must be an email")
	private String email;
	private String password;
	private MultipartFile image;
	public MultipartFile getImage() {
		return image;
	}
	/*public List<AddressRequestModel> getAddresses() {
		return addresses;
	}
	public void setAddress(List<AddressRequestModel> address) {
		this.addresses = address;
	}*/
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
