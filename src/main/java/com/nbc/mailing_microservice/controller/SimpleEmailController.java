package com.nbc.mailing_microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nbc.mailing_microservice.components.AppMailSender;

@RestController
public class SimpleEmailController {
	@Autowired
	AppMailSender appMailSender;

	@RequestMapping("/")
	public String welcome() {
		return "welcome to the new world";
	}

	@RequestMapping("/mail")
	public String home() {
		try {
			appMailSender.sendTextMail();
			appMailSender.sendHtmltMail();
			return "Email Sent!";
		} catch (Exception ex) {
			return "Error in sending email: " + ex;
		}
	}
}
