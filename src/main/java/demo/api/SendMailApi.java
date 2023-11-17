package demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import demo.modelApi.EmailDto;
import demo.models.User;
import demo.services.EmailService;
import demo.services.UserService;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/email")
public class SendMailApi {
	@Autowired
	private EmailService emailService;
	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@GetMapping("/forgotPass/{email}")
	public ResponseEntity<String> sendEmail(@PathVariable("email") String email) {
		
		// tìm kiếm user có email
		try {
			 User user=this.userService.findByEmail(email);
		
			if(user ==null) {
				return new ResponseEntity<>("Email chưa đăng ký tài khoản", HttpStatus.BAD_REQUEST);
			}
			// tạo otp
			else {
				String otp=emailService.codeOtp();
				user.setCodeOtp(otp);
				if(this.userService.update(user)!=null) {
					Context context = new Context();
					context.setVariable("name", user.getFullName());
					context.setVariable("otp", user.getCodeOtp());
					// gửi mail
					try {
						emailService.sendMail(user.getEmail(), "Đặt lại mật khẩu", "otp", context);
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return new ResponseEntity<>("Gửi mail thành công", HttpStatus.OK);
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}    
		return new ResponseEntity<>("Lỗi", HttpStatus.BAD_REQUEST);
	}
	@GetMapping("/checkOtp")
	public ResponseEntity<String> checkOtp(@RequestParam("otp") String otp,@RequestParam("email") String email) {
		User user=this.userService.findByEmail(email);
		if(user!=null) {
			if(user.getCodeOtp().equals(otp)) {
				return new ResponseEntity<>("True", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>("Mã không đúng", HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>("Lỗi", HttpStatus.BAD_REQUEST);
	}
	@PutMapping("/updatePass")
	public ResponseEntity<String> updatePass(@RequestParam("passnew") String passnew,@RequestParam("email") String email){
		User user=this.userService.findByEmail(email);
		if(user!=null) {
			user.setPassWord(passwordEncoder.encode(passnew));
			if(this.userService.update(user)!=null) {
				return new ResponseEntity<>("thành công", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>("thất bại", HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>("Lỗi", HttpStatus.BAD_REQUEST);
	}
}
