package com.nbc.mailing_microservice.components;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.nbc.mailing_microservice.model.Email;
import com.nbc.mailing_microservice.model.EmailTemplate;

@Component
public class AppMailSender {
	@Autowired
	EmailService emailService;

	public void sendTextMail(Email data , MultipartFile path) throws MessagingException {
		String from = data.getFrom();
		List<String> to = data.getTo();
		String subject = data.getSubject();
		List<String> cc = data.getCc();

		EmailTemplate template = new EmailTemplate("hello-world-plain.txt");
		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("user", data.getRecieverName());
		replacements.put("body", data.getMsgBody());
		replacements.put("sender", data.getSenderName());
		String body = template.getTemplate(replacements);
		data.setMsgBody(body);		
		boolean isHtml = false;
		Email email = new Email(from, to, cc, subject, data.getMsgBody());
		emailService.send(email, isHtml , path);
	}

	public void sendHtmltMail(Email data , MultipartFile file) throws MessagingException {
		
		String from = data.getFrom();
		List<String> to = data.getTo();
		String subject = data.getSubject();
		List<String> cc = data.getCc();
		
		EmailTemplate template = new EmailTemplate("hello-world.html");
		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("user", data.getRecieverName());
		replacements.put("body", data.getMsgBody());
		replacements.put("sender", data.getSenderName());
		replacements.put("today", String.valueOf(new Date()));
		String msgBody = template.getTemplate(replacements);
		data.setMsgBody(msgBody);
		boolean isHtml = true;
		Email email = new Email( from , to , cc , subject , data.getMsgBody() );
		emailService.send(email,isHtml, file);		
   }	
}
