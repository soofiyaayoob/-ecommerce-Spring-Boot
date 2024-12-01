package com.example.demo.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.Principle.UserDetailsServiceTaamsmaak;
import com.example.demo.Service.jwtServiceTaamsmaak;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtFilter extends OncePerRequestFilter{
	@Autowired
	jwtServiceTaamsmaak jwtServiceTaamsmaak;
	@Autowired
	UserDetailsServiceTaamsmaak detailsServiceTaamsmaak;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 
		
				String token=null;
				Cookie[] cookies=request.getCookies();
				
				if(cookies !=null) {
					for(Cookie cookie : cookies) {
						if(cookie.getName().equals("jwt")) {
							token=cookie.getValue();
							break;
						}
					}
				}
				
				String username=null;
				if(token !=null) {
					username=jwtServiceTaamsmaak.extractUsername(token);
				}
				if (username !=null && SecurityContextHolder.getContext().getAuthentication()==null){
					UserDetails userDetails=detailsServiceTaamsmaak.loadUserByUsername(username);
					
					if(jwtServiceTaamsmaak.validateToken(token,userDetails)) {
						UsernamePasswordAuthenticationToken authenticationtoken=
								new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
						authenticationtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authenticationtoken);
					}
					
				}filterChain.doFilter(request, response);
				
			}

		

		
	}


