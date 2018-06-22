package com.nbc.mailing_microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nbc.mailing_microservice.components.AppMailSender;
import com.nbc.mailing_microservice.model.Email;

import springfox.documentation.annotations.ApiIgnore;

@RestController
public class SimpleEmailController {
	@Autowired
	AppMailSender appMailSender;

	@RequestMapping("/")
	@ApiIgnore
	public String welcome() {
		return "welcome to the mailing_microservice";
	}

	@RequestMapping(value="/mail/{data}" , method = RequestMethod.POST)
	public String home(@RequestBody Email data) {
		try {
			appMailSender.sendTextMail(data);
//			appMailSender.sendHtmltMail(data);
			return "Email Sent!";
		} catch (Exception ex) {
			return "Error in sending email: " + ex;
		}
	}
}
