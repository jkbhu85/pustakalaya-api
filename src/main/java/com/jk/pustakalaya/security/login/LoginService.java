package com.jk.pustakalaya.security.login;

import org.springframework.http.ResponseEntity;

public interface LoginService {
	ResponseEntity<String> login(LoginCredentials loginCred);
}
