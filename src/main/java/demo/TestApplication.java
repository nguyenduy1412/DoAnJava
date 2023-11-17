package demo;

import java.util.Properties;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import demo.services.StorageService;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {

			storageService.init();
		};
	}
	
}
