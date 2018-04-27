package com.jk.pustakalaya.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {
	@Autowired
	private LoginService loginService;

	@GetMapping(produces=MediaType.TEXT_PLAIN_VALUE)
	public String login(@RequestBody LoginCredentials loginCred )
			throws InvalidCredentialsException {
		return loginService.login(loginCred);
	}
}
