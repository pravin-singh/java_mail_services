package com.nbc.mailing_microservice.components;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nbc.mailing_microservice.model.Email;
import com.nbc.mailing_microservice.model.EmailTemplate;

@Component
public class AppMailSender {
	@Autowired
	EmailService emailService;

	public void sendTextMail() {

		String from = "example@example.com";
		String to = "example@example.com";
		String subject = "Java Mail with Spring Boot - Plain Text";
		List<String> cc = new ArrayList<>();
		cc.add("example@example.com");
		cc.add("example@example.com");

		EmailTemplate template = new EmailTemplate("hello-world-plain.txt");

		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("user", "All");
		replacements.put("today", String.valueOf(new Date()));

		String message = template.getTemplate(replacements);

//		Email email = new Email(from, to, subject, message);
		
		Email email = new Email( from , to , cc , subject , message );

		emailService.send(email);
	}

	public void sendHtmltMail() {

		String from = "example@example.com";
		String to = "example@example.com";
		String subject = "Java Mail with Spring Boot Html Mail";

		EmailTemplate template = new EmailTemplate("hello-world.html");

		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("user", "username");
		replacements.put("today", String.valueOf(new Date()));

		String message = template.getTemplate(replacements);

		Email email = new Email(from, to, subject, message);
		email.setHtml(true);
		emailService.send(email);
	}

}
