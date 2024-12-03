package com.example.demo.Service.utilty;

import java.lang.reflect.Field;
import java.util.Base64;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.demo.Principle.UserPrincipleTaamsmaak;
@Component
public class Commonutil {

	 public static Object convertImageDataToBase64(Object entity) {
	        try {
	           
	            Class<?> clazz = entity.getClass();

	           
	            Field imageDataField = clazz.getDeclaredField("imageData");
	            imageDataField.setAccessible(true);

	          
	            Field imageBase64Field = clazz.getDeclaredField("imageBase64");
	            imageBase64Field.setAccessible(true);

	            // Check if the imageData field is not null
	            byte[] imageData = (byte[]) imageDataField.get(entity);
	            if (imageData != null) {
	              
	                String imageBase64 ="data:image/png;base64,"+ Base64.getEncoder().encodeToString(imageData);

	              
	                imageBase64Field.set(entity, imageBase64);
	            }
	        } catch (NoSuchFieldException | IllegalAccessException e) {
	            
	            System.out.println("Error processing image data: " + e.getMessage());
	        }

	        return entity;
	    }
    
    public Long getCurrentUserId() {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipleTaamsmaak) {
           
            UserPrincipleTaamsmaak userPrinciple = (UserPrincipleTaamsmaak) authentication.getPrincipal();
            return userPrinciple.getUserid();  
        }
        
        return null;  
    }
    
}
