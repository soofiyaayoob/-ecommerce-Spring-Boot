package com.example.demo.Aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	  @Autowired
	    private JavaMailSender mailSender;

	    public void sendOtpEmail(String to, String otp) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(to);
	        message.setSubject("Your OTP Code");
	        message.setText("Your OTP code is: " + otp);
	        mailSender.send(message);
	    }
//	  @Value("${spring.mail.username}")  // Correct syntax
//	    private String emailfrom;
//	    
//	    public void sendOtpEmail(String to) {
//	    	System.out.println("iam here email");
//	        SimpleMailMessage message = new SimpleMailMessage();
//	        message.setFrom(emailfrom);
//	        message.setTo(to);
//	        message.setSubject("Your OTP Code");
//	        message.setText("Your OTP code is: ");
//	        mailSender.send(message);
//	    }

	    public Boolean sendResetPasswordMail(String url, String email) {
	        try {
	            SimpleMailMessage message = new SimpleMailMessage();
	            message.setTo(email);
	            message.setSubject("Reset Password");
	            message.setText("To change your password, click the link below:\n" + url);
	            
	            mailSender.send(message); 
	            return true; 
	        } catch (Exception e) {
	            e.printStackTrace(); 
	            return false;
	        }
	    }
}
