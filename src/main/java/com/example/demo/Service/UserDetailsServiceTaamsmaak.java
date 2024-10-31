package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.example.demo.Principle.UserPrincipleTaamsmaak;
import com.example.demo.model.UserEntity;
import com.example.demo.repositry.UserRepo;

@Service
public class UserDetailsServiceTaamsmaak implements UserDetailsService{
	
	@Autowired
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity=userRepo.findByUsername(username);
		if(userEntity==null) {
			System.out.println("user not found");
			throw new UsernameNotFoundException("oh ithink you are not existed");
			
		}
	return new UserPrincipleTaamsmaak(userEntity);
	}
	
	
	}

