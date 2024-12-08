package com.example.demo.CustomHandler;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.Principle.UserPrincipleTaamsmaak;
import com.example.demo.Service.jwtServiceTaamsmaak;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class SucessHandler implements AuthenticationSuccessHandler{
	 String redirectUrl=null;
	  
	    

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println("perform login here customise authentication ");
		 Long userid = ((UserPrincipleTaamsmaak) authentication.getPrincipal()).getUserid();
		  String jwt = jwtServiceTaamsmaak.generateToken(authentication.getName(),userid);
	        System.out.println("Generated JWT Token: " + jwt);
	       

	       
	        ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
	                .httpOnly(true)
	                .path("/")
	                .maxAge(7 * 24 * 60 * 60) 
	                .secure(true)
	                .build();
	        response.addHeader("Set-Cookie", cookie.toString());
	       
	        
	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	        System.out.println("User Roles: " + authorities);


	       
	        String redirectUrl;
	       
	        if (authorities.contains(new SimpleGrantedAuthority("ADMIN"))) {
	        	
	            redirectUrl = "/admin/dash";
	        } else if (authorities.contains(new SimpleGrantedAuthority("USER"))) {
	            redirectUrl = "/";
	        }else if (authorities.contains(new SimpleGrantedAuthority("RESTAURANT"))) {
	            redirectUrl = "/restaurant/main";
	        }else {
	        	System.out.println("no user role");
	            redirectUrl = "/login?error"; 
	        }

	       
	        response.sendRedirect(redirectUrl);
	    }
	}