package com.jk.pustakalaya.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jk.pustakalaya.util.MailConsts;

@Controller
public class DemoController {

	@RequestMapping("/test/mail")
	public String getMessage() {
		return "html/" + MailConsts.TEMPLATE_COMPLETE_REGISTRAION;
	}
}
