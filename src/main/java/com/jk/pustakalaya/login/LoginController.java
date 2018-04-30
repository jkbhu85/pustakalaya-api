package com.jk.pustakalaya.login;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {
	private LoginService loginService = new LoginServiceImpl();

	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.TEXT_PLAIN_VALUE)
	public String login(@RequestBody LoginCredentials loginCred )
			throws InvalidCredentialsException {
		return loginService.login(loginCred);
	}
}
