package com.jk.pustakalaya;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PustakalayaApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PustakalayaApplication.class);
	}

	public static void main(String[] args) {
		List<String> list = Arrays.asList(args);
		list.add("--debug");
		SpringApplication.run(PustakalayaApplication.class, list.toArray(new String[] {}));
	}
}
