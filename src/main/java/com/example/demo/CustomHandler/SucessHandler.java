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
		
		  String jwt = jwtServiceTaamsmaak.generateToken(authentication.getName());
	        System.out.println("Generated JWT Token: " + jwt);

	        // Set the JWT in a cookie
	        ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
	                .httpOnly(true)
	                .path("/")
	                .maxAge(7 * 24 * 60 * 60) // 1 week
	                .secure(true)
	                .build();
	        response.addHeader("Set-Cookie", cookie.toString());

	        // Determine redirect URL based on roles
	        String redirectUrl;
	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
	            redirectUrl = "/adminpanel";
	        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
	            redirectUrl = "/home";
	        } else {
	            redirectUrl = "/login?error=true"; // fallback in case no role is found
	        }

	        // Perform the redirect
	        response.sendRedirect(redirectUrl);
	    }
	}