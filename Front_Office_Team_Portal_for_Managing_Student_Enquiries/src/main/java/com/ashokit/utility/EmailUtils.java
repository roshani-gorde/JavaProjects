package com.ashokit.utility;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
	
	@Autowired
	private JavaMailSender mailSender;
	
	 public void sendEmail(String to, String subject, String body) {
		 try {
		 MimeMessage mimeMessage = mailSender.createMimeMessage();
		 MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		 helper.setTo(to);
		 helper.setSubject(subject);
		 helper.setText(body,true);
		 mailSender.send(mimeMessage);
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	    }

}
