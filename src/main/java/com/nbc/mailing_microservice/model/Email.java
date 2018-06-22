package com.nbc.mailing_microservice.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nbc.mailing_microservice.utility.AppUtil;

/**
 * @author ps145598
 *
 */
public class Email {
	
	private String from;

	private List<String> to;

	private List<String> cc;

	private String subject;

	private String message;
	
	private boolean isHtml;

	private Email(String from, List<String> to, List<String> cc, String subject, String message, boolean isHtml) {
		super();
		this.from = from;
		this.to = to;
		this.cc = cc;
		this.subject = subject;
		this.message = message;
		this.isHtml = isHtml;
	}
	public Email(String from, String to, List<String> cc, String subject, String message) {
		this();
		this.from = from;
		this.to.addAll(Arrays.asList(splitByComma(to)));
		this.cc = cc;
		this.subject = subject;
		this.message = message;
	}

	public Email() {
		this.to = new ArrayList<String>();
		this.cc = new ArrayList<String>();
	}

	public Email(String from, String toList, String subject, String message) {
		this();
		this.from = from;
		this.subject = subject;
		this.message = message;
		this.to.addAll(Arrays.asList(splitByComma(toList)));
	}

	public Email(String from, String toList, String ccList, String subject, String message) {
		this();
		this.from = from;
		this.subject = subject;
		this.message = message;
		this.to.addAll(Arrays.asList(splitByComma(toList)));
		this.cc.addAll(Arrays.asList(splitByComma(ccList)));
	}

	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	
	public List<String> getTo() {
		return to;
	}

	
	public void setTo(List<String> to) {
		this.to = to;
	}

	
	public List<String> getCc() {
		return cc;
	}

	
	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	
	public String getSubject() {
		return subject;
	}

	
	public void setSubject(String subject) {
		this.subject = subject;
	}

	
	public String getMessage() {
		return message;
	}

	
	public void setMessage(String message) {
		this.message = message;
	}

	
	public boolean isHtml() {
		return isHtml;
	}
	
	
	public void setHtml(boolean isHtml) {
		this.isHtml = isHtml;
	}

	private String[] splitByComma(String toMultiple) {
		String[] toSplit = toMultiple.split(",");
		return toSplit;
	}

	public String getToAsList() {
		return AppUtil.concatenate(this.to, ",");
	}
}
