package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import demo.services.CustomUserDetailService;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests((auth)->auth.
		requestMatchers("/*").permitAll().
		requestMatchers("/**").permitAll().
		requestMatchers("/book-detail/**").permitAll().
		requestMatchers("/api/**").permitAll().
		requestMatchers("/find-category/**").permitAll().
		requestMatchers("/find-book/**").permitAll().
		requestMatchers("/admin/**").hasAuthority("ADMIN").
		anyRequest().authenticated()).
		formLogin(login->login.loginPage("/logon").loginProcessingUrl("/logon")
		.usernameParameter("username").passwordParameter("password").
		defaultSuccessUrl("/admin",true)).logout(logout->logout.logoutUrl("/admin-logout").logoutSuccessUrl("/logon")).
		formLogin(login->login.loginPage("/login").loginProcessingUrl("/login")
		.usernameParameter("username").passwordParameter("password").
		defaultSuccessUrl("/",true).failureUrl("/login?error=true")).logout(logout->logout.logoutUrl("/logout").logoutSuccessUrl("/"));
		
		return http.build();
	} 
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web)->web.ignoring().requestMatchers("/static/**","/fe/**","assets/**","/templates/**","/uploads/**","/asset/**");
	}
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
