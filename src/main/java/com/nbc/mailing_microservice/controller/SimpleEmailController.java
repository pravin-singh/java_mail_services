package com.nbc.mailing_microservice.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbc.mailing_microservice.components.AppMailSender;
import com.nbc.mailing_microservice.model.Email;

import io.swagger.annotations.ApiParam;
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
	
	final String json = "{ \"cc\": [\"String\"],\"from\": \"String\",\"msgBody\": \"String\",\r\n" + 
			"  \"recieverName\": \"String\",\"senderName\": \"String\",\r\n" + 
			"  \"subject\": \"String\",\"to\": [\"String\"]}";

	@RequestMapping(value = "/mail", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, method = RequestMethod.POST)	
    public ResponseEntity<String> uploadFile( @ApiParam(value = "Sample value : "+json)@RequestPart String data, @RequestPart(required = false ) MultipartFile file) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		Email mail = new Email();
		mail = mapper.readValue(data, Email.class);
		
		try { 
//			appMailSender.sendTextMail(mail , file);
			appMailSender.sendHtmltMail(mail , file);
			return new ResponseEntity<String>("Email Sent!", HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<String>("Error in sending email: " + ex, HttpStatus.OK);
		}
    }

}
