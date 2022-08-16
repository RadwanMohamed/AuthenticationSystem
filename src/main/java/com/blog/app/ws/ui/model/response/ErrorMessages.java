package com.blog.app.ws.ui.model.response;

public enum ErrorMessages {
	MISSING_REQUIRED_FIELD("Missing required field. please check documentation for required fields"),
	RECORD_ALREADY_EXISTS("Record already exists"),
	INTERNAL_SERVER_ERROR("Internal server error"),
	NO_RECORD_FOUND("Record with provided id is not found"),
	AUTHENTICATION_FAILED("Authentication failed"),
	COULD_NOT_UPDATE_RECORD("coulld not update record"),
	COULD_NOT_DELETE_RECORD("could not delete record"),
	EMAIL_ADDRESS_NOT_VERIFIED("email address could not be verified");
	private String errorMessage;
	private ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
