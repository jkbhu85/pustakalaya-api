package com.jk.ptk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jk.ptk.util.MailConsts;

@Controller
public class DemoController {

	@RequestMapping("/test/mail")
	public String getMessage() {
		return "html/" + MailConsts.TEMPLATE_COMPLETE_REGISTRATION;
	}
}
