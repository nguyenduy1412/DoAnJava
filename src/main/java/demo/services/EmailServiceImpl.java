package demo.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;
	@Override
	public void sendMail(String to, String subject, String template, Context context) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);

        // Tạo nội dung email từ template Thymeleaf
        String htmlContent = templateEngine.process(template, context);
        helper.setText(htmlContent, true); // Đặt tham số true để xác định là nội dung là HTML

        emailSender.send(message);
		
	}
	@Override
	public String codeOtp() {
		Random random = new Random();
		int n=random.nextInt(4)+5;
		String s="";
        // Tạo số nguyên ngẫu nhiên trong khoảng từ 0 đến 9
		for(int i=0;i<n;i++) {
			Integer randomNumber = random.nextInt(10);
			s+=randomNumber.toString();
		}
		return s;
	}

}
