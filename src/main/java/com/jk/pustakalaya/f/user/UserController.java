package com.jk.pustakalaya.f.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/user")
public class UserController {
	@Autowired
	private UserService service;

	@GetMapping("/{id}")
	public User getUser(@PathVariable("id") Long id) {
		return service.getUser(id);
	}

	@PostMapping()
	public void addUser(@RequestBody Map<String, String> body) {
		String firstName = body.get("firstName");
		String lastName = body.get("lastName");
		String email = body.get("email");
		String localeStr = body.get("locale");

		service.addUser(email, firstName, lastName, localeStr);
	}

	@PutMapping("/password")
	public void updatePassword(@RequestBody Map<String, String> body) {
		Long id = Long.parseLong(body.get("id"));
		String oldPassword = body.get("oldPassword");
		String newPassword = body.get("newPassword");

		service.updatePassword(id, oldPassword, newPassword);
	}


	@PutMapping("/question")
	public void updateSecurityQuestion(@RequestBody Map<String, String> body) {
		Long id = Long.parseLong(body.get("id"));
		String password = body.get("password");
		String question = body.get("question");
		String answer = body.get("answer");

		service.updateSecurityQuestion(id, password, question, answer);
	}
}
