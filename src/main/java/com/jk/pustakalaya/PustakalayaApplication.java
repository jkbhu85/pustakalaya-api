package com.jk.pustakalaya;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Starting point for this Spring application.
 *
 * @author Jitendra
 *
 */

@SpringBootApplication
@EnableWebMvc
public class PustakalayaApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PustakalayaApplication.class);
	}

	public static void main(String[] args) {
		List<String> list = Arrays.asList(args);
		SpringApplication.run(PustakalayaApplication.class, list.toArray(new String[] {}));
	}
}
