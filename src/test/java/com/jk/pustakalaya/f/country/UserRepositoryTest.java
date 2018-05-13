package com.jk.pustakalaya.f.country;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jk.pustakalaya.PustakalayaApplication;
import com.jk.pustakalaya.f.user.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=PustakalayaApplication.class)
public class UserRepositoryTest {
	private Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);

	@Autowired
	private UserRepository repo;

	@Test
	public void findUserInfo_email() {
		String email = "krishna.murildhar@m.com";
		log.info("UserAuthInfo -> {}", repo.findUserAuthInfo(email));
	}
}
