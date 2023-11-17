package demo.services;

import org.thymeleaf.context.Context;

import demo.modelApi.EmailDto;
import jakarta.mail.MessagingException;

public interface EmailService {
	void sendMail(String to,String subject,String template,Context context) throws MessagingException;
	String codeOtp();
}
