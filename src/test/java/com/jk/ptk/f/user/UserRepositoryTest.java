package com.jk.ptk.f.user;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jk.ptk.PustakalayaApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=PustakalayaApplication.class)
public class UserRepositoryTest {
	Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);
	private static ObjectMapper om = new ObjectMapper();

	@BeforeClass
	public static void init() {
		om.configure(SerializationFeature.INDENT_OUTPUT, true);
	}

	@Autowired
	private UserRepository repo;

	@Test
	public void getUser() {
		User user = repo.findUser(1L);

		try {
			log.info("user info -> {}", om.writeValueAsString(user));
		} catch (JsonProcessingException e) {
			log.error("Exception in serialization -> {}", e);
		}
	}

	@Test
	public void getUserAuthInfo_id() {
		UserAuthInfo authInfo = repo.findUserAuthInfo(1L);
		log.info("Inside getUserAuthInfo_id");

		try {
			log.info("user auth info -> {}", om.writeValueAsString(authInfo));
		} catch (JsonProcessingException e) {
			log.error("Exception in serialization -> {}", e);
		}
	}


	@Test
	public void getUserAuthInfo_email() {
		UserAuthInfo authInfo = repo.findUserAuthInfo("jitendra.kumar@m.com");
		log.info("Inside getUserAuthInfo_email");

		try {
			log.info("user auth info -> {}", om.writeValueAsString(authInfo));
		} catch (JsonProcessingException e) {
			log.error("Exception in serialization -> {}", e);
		}
	}
}
