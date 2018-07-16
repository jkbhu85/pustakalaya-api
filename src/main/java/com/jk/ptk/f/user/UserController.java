package com.jk.ptk.f.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jk.ptk.security.login.InvalidCredentialsException;

@RestController("/api/user")
public class UserController {
	@Autowired
	private UserService service;

	@GetMapping("/{id}")
	public User getUser(@PathVariable("id") Long id) {
		return service.getUser(id);
	}

	@PutMapping("/password")
	public void updatePassword(@RequestBody Map<String, String> body) {
		Long id = Long.parseLong(body.get("id"));
		String oldPassword = body.get("oldPassword");
		String newPassword = body.get("newPassword");

		try {
			service.updatePassword(id, oldPassword, newPassword);
		} catch (InvalidCredentialsException e) {
			e.printStackTrace();
		}
	}


	@PutMapping("/question")
	public void updateSecurityQuestion(@RequestBody Map<String, String> body) {
		Long id = Long.parseLong(body.get("id"));
		String password = body.get("password");
		String question = body.get("question");
		String answer = body.get("answer");

		try {
			service.updateSecurityQuestion(id, password, question, answer);
		} catch (InvalidCredentialsException e) {
			e.printStackTrace();
		}
	}
}
