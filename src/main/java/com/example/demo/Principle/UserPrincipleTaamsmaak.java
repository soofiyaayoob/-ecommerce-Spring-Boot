package com.example.demo.Principle;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.UserEntity;

public class UserPrincipleTaamsmaak implements UserDetails {
	
	
private UserEntity userEntity;



	public UserPrincipleTaamsmaak(UserEntity userEntity) {
	
	this.userEntity = userEntity;
}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(userEntity.getRole().name()));
	}

	@Override
	public String getPassword() {
		return userEntity.getPassword();
	}

	@Override
	public String getUsername() {
		
		return userEntity.getUsername();
	}
	  public boolean isActive() {
	        return userEntity.isActive(); 
	    }
	public String getEmail() {
		
		return userEntity.getEmail();
	}
	 public Long getUserid() {
	        return userEntity.getId();
	    }
	  public UserEntity getUser() {
	        return userEntity;
	    }


}
