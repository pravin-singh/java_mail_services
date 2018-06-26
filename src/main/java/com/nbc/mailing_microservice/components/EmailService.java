package com.nbc.mailing_microservice.components;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nbc.mailing_microservice.config.ApplicationLogger;
import com.nbc.mailing_microservice.model.Email;

@Component
public class EmailService {
	
	
	@Autowired
	private JavaMailSender mailSender;

	private static final ApplicationLogger logger = ApplicationLogger.getInstance();

	public void send(Email eParams,boolean isHtml , MultipartFile file) throws MessagingException {

		if (isHtml==true) {
			try {
				sendHtmlMail(eParams,file);
			} catch (MessagingException e) {
				logger.error("Could not send email to : {} Error = {}", eParams.toAsList(), e.getMessage());
			}
		} else {
			sendPlainTextMail(eParams, file);
		}

	}

	private void sendHtmlMail(Email eParams , MultipartFile file) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();	   	
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(eParams.getTo().toArray(new String[eParams.getTo().size()]));
		helper.setReplyTo(eParams.getFrom());
		helper.setFrom(eParams.getFrom());
		helper.setSubject(eParams.getSubject());
		helper.setText("", eParams.getMsgBody());
		helper.setText(eParams.getMsgBody() , true );
		if(file!=null) {
			helper.addAttachment(StringUtils.cleanPath(file.getOriginalFilename()), file);
		}
		
		if (eParams.getCc().size() > 0) {
			helper.setCc(eParams.getCc().toArray(new String[eParams.getCc().size()]));
		}
		mailSender.send(message);
	}

	private void sendPlainTextMail(Email eParams, MultipartFile file) throws MessagingException {
		
//		SimpleMailMessage mailMessage = new SimpleMailMessage();		
//		eParams.getTo().toArray(new String[eParams.getTo().size()]);
//		mailMessage.setTo(eParams.getTo().toArray(new String[eParams.getTo().size()]));
//		mailMessage.setReplyTo(eParams.getFrom());
//		mailMessage.setFrom(eParams.getFrom());
//		mailMessage.setSubject(eParams.getSubject());
//		mailMessage.setText(eParams.getMsgBody());		
//		if (eParams.getCc().size() > 0) {
//			mailMessage.setCc(eParams.getCc().toArray(new String[eParams.getCc().size()]));
//		}
//		mailSender.send(mailMessage);
		
		MimeMessage message = mailSender.createMimeMessage();	   	
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(eParams.getTo().toArray(new String[eParams.getTo().size()]));
		helper.setReplyTo(eParams.getFrom());
		helper.setFrom(eParams.getFrom());
		helper.setSubject(eParams.getSubject());		
		helper.setText(eParams.getMsgBody(), false);
		if(file!=null) {
		helper.addAttachment(StringUtils.cleanPath(file.getOriginalFilename()), file);
		}
		if (eParams.getCc().size() > 0) {
			helper.setCc(eParams.getCc().toArray(new String[eParams.getCc().size()]));
		}
		mailSender.send(message);
	}
}
