package com.jk.ptk.security.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jk.ptk.app.response.PtkResponse;
import com.jk.ptk.app.response.ResponseCode;

@RestController
@RequestMapping("/ptk/login")
public class LoginController {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;

	@PostMapping
	public ResponseEntity<PtkResponse> login(@RequestBody LoginCredentials loginCred) {
		PtkResponse response = new PtkResponse();
		HttpStatus status;

		try {
			String jwt = loginService.login(loginCred);
			response
				.setResponseCode(ResponseCode.OPERATION_SUCCESSFUL)
				.setData(jwt);
			status = HttpStatus.OK;
		} catch (InvalidCredentialsException e) {
			response
				.setResponseCode(ResponseCode.INVALID_CREDENTIALS)
				.setMessage("ERROR_LOGIN_UNSUCCESSFUL");
			status = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (AccountLockedException e) {
			response
				.setResponseCode(ResponseCode.ACCOUNT_LOCKED)
				.setMessage("ERROR_LOGIN_UNSUCCESSFUL");
			status = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (AccountRevokedException e) {
			response
				.setResponseCode(ResponseCode.ACCOUNT_ACCESS_REVOKED)
				.setMessage("ERROR_LOGIN_UNSUCCESSFUL");
			status = HttpStatus.UNPROCESSABLE_ENTITY;
		} catch (Exception e) {
			response
				.setResponseCode(ResponseCode.UNKNOWN_ERROR)
				.setMessage("ERROR_UNKNOWN");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			log.error("Error in authentication.{}", e);
		}

		ResponseEntity<PtkResponse> re = new ResponseEntity<>(response, status);
		return re;
	}
}
