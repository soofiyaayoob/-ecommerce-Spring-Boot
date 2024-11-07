package com.example.demo.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.web.servlet.server.Session.Cookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.CustomHandler.SucessHandler;
import com.example.demo.Service.UserDetailsServiceTaamsmaak;
import com.example.demo.filter.JwtFilter;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;


@Configuration
@EnableWebSecurity
public class confi {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	public UserDetailsService detailsService() {
		return new UserDetailsServiceTaamsmaak();
	}
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new SucessHandler();
	}
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
		  httpSecurity.cors().and()
	        .csrf(csrf -> csrf.disable()); 
		  httpSecurity
	         .authorizeHttpRequests(req -> req
	             .requestMatchers("/register", "/login", "/css/**","/test","/verification","login","/profile","/perform_login","/**").permitAll() 
	         
	             .anyRequest().authenticated()
	         );
	     
	       httpSecurity .formLogin(form -> form
	            .loginPage("/login")
	            .loginProcessingUrl("/perform_login")
	           
	            .failureUrl("/login?error=true")
	            .permitAll())
	        .logout(logout -> logout
	            .logoutUrl("/logout")
	            .invalidateHttpSession(true)
	            .clearAuthentication(true)
	            .deleteCookies("JSESSIONID", "jwt")
	            .logoutSuccessUrl("/login?logout")
	            .permitAll());
	
		  httpSecurity.httpBasic(Customizer.withDefaults());
	
	        httpSecurity.formLogin().disable();
	        httpSecurity.httpBasic().disable();

	    httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	    httpSecurity
	    .sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED); // Ensures each request is authenticated individually

	    return httpSecurity.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService( detailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
		
	}
	
	 @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**") // Allow all endpoints
	                        .allowedOrigins("http://127.0.0.1:5500") // Your front-end URL
	                        .allowedMethods("GET", "POST", "PUT", "DELETE")
	                        .allowedHeaders("*") // Allow all headers
	                        .allowCredentials(true); // Allow credentials if needed
	            }
	        };

	 }
	 @Bean
	    public JavaMailSender javaMailSender() {
	        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	        mailSender.setHost("smtp.gmail.com");
	        mailSender.setPort(587);
	        mailSender.setUsername("soofiyasoof8@gmail.com");
	        mailSender.setPassword("onezxunokjufrvyx");

	        Properties props = mailSender.getJavaMailProperties();
	        props.put("mail.transport.protocol", "smtp");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.debug", "true");

	        return mailSender;
	    }
	 
//	 @Bean
//	 public CookieSerializer cookieSerializer() {
//	     DefaultCookieSerializer serializer = new DefaultCookieSerializer();
//	     serializer.setCookieName("JSESSIONID");
//	     serializer.setSameSite("None"); // or "None" if you need cross-origin requests
//	  
//	     serializer.setUseSecureCookie(false); // Ensure secure attribute is true for HTTPS
//	        serializer.setUseHttpOnlyCookie(true);
//	     serializer.setCookiePath("/");
//	     return serializer;
//	 }
//	
//	 @Bean
//	    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//	        RedisTemplate<String, Object> template = new RedisTemplate<>();
//	        template.setConnectionFactory(redisConnectionFactory);
//	        return template;
//	    }


	 
}
