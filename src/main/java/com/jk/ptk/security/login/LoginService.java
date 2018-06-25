package com.jk.ptk.security.login;

import org.springframework.http.ResponseEntity;

public interface LoginService {
	ResponseEntity<String> login(LoginCredentials loginCred);
}
