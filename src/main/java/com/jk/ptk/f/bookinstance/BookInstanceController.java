package com.jk.ptk.f.bookinstance;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jk.ptk.app.response.PtkResponse;

@RestController
@RequestMapping("/ptk/book/instance")
public class BookInstanceController {
	private static final Logger log = LoggerFactory.getLogger(BookInstanceController.class);
	
	public ResponseEntity<PtkResponse> assignBook(@RequestBody Map<String, String> requestBody) {
		PtkResponse response = null;
		HttpStatus httpStatus = null;
		
		
		
		return new ResponseEntity<>(response, httpStatus);
	}
}
