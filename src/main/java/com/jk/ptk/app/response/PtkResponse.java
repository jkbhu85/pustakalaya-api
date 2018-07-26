package com.jk.ptk.app.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PtkResponse {
	
	private ResponseCode responseCode;
	private String message;
	private Object errors;
	private Object data;
	
	public PtkResponse() {}
	
	public PtkResponse(String message) {
		this.message = message;
	}
	
	public PtkResponse(ResponseCode responseCode, String message) {
		this.responseCode = responseCode;
		this.message = message;
	}

	public ResponseCode getResponseCode() {
		return responseCode;
	}

	public PtkResponse setResponseCode(ResponseCode responseCode) {
		this.responseCode = responseCode;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public PtkResponse setMessage(String message) {
		this.message = message;
		return this;
	}

	public Object getErrors() {
		return errors;
	}

	public PtkResponse setErrors(Object errors) {
		this.errors = errors;
		return this;
	}

	public Object getData() {
		return data;
	}

	public PtkResponse setData(Object data) {
		this.data = data;
		return this;
	}
}
