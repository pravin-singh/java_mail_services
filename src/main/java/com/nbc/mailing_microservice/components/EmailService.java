package com.nbc.mailing_microservice.components;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.nbc.mailing_microservice.config.ApplicationLogger;
import com.nbc.mailing_microservice.model.Email;

@Component
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;

	private static final ApplicationLogger logger = ApplicationLogger.getInstance();

	public void send(Email eParams,boolean isHtml) {

		if (isHtml==true) {
			try {
				sendHtmlMail(eParams,isHtml);
			} catch (MessagingException e) {
				logger.error("Could not send email to : {} Error = {}", eParams.toAsList(), e.getMessage());
			}
		} else {
			sendPlainTextMail(eParams,isHtml);
		}

	}

	private void sendHtmlMail(Email eParams,boolean isHtml) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(eParams.getTo().toArray(new String[eParams.getTo().size()]));
		helper.setReplyTo(eParams.getFrom());
		helper.setFrom(eParams.getFrom());
		helper.setSubject(eParams.getSubject());
		helper.setText("", eParams.getMsgBody());

		if (eParams.getCc().size() > 0) {
			helper.setCc(eParams.getCc().toArray(new String[eParams.getCc().size()]));
		}
		mailSender.send(message);
	}

	private void sendPlainTextMail(Email eParams,boolean isHtml) {
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		eParams.getTo().toArray(new String[eParams.getTo().size()]);
		mailMessage.setTo(eParams.getTo().toArray(new String[eParams.getTo().size()]));
		mailMessage.setReplyTo(eParams.getFrom());
		mailMessage.setFrom(eParams.getFrom());
		mailMessage.setSubject(eParams.getSubject());
		mailMessage.setText(eParams.getMsgBody());

		if (eParams.getCc().size() > 0) {
			mailMessage.setCc(eParams.getCc().toArray(new String[eParams.getCc().size()]));
		}

		mailSender.send(mailMessage);
	}
}
