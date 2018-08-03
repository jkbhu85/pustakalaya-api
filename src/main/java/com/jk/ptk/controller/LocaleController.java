package com.jk.ptk.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jk.ptk.util.LocaleUtil;

@RestController
@RequestMapping("/ptk/locale")
public class LocaleController {
	
	@GetMapping
	public Set<String> getLocales() {
		return LocaleUtil.allLocales();
	}

}
