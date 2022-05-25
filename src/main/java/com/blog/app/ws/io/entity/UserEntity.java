package com.blog.app.ws.io.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="users")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;
	
	
	@Column(nullable=false)
	private String UserId;
	
	@Column(nullable=false, length=50)
	private String firstName;
	

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(nullable=false, length=50)
	private String lastName;
	
	@Column(nullable=false,length=50, unique = true)
	private String email;
	
	
	@Column(nullable=false)
	private String encryptedPassword;
	
	private String emailVerificationToken;
	
	@Column(nullable=false)
	private Boolean emailVerifiactionStatus = false;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getEmailVerificationToken() {
		return emailVerificationToken;
	}

	public void setEmailVerificationToken(String emailVerificationToken) {
		this.emailVerificationToken = emailVerificationToken;
	}

	public Boolean getEmailVerifiactionStatus() {
		return emailVerifiactionStatus;
	}

	public void setEmailVerifiactionStatus(Boolean emailVerifiactionStatus) {
		this.emailVerifiactionStatus = emailVerifiactionStatus;
	}
	
}
