package com.example.demo.Service.utilty;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Principle.UserPrincipleTaamsmaak;
import com.example.demo.model.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
@Component
public class Commonutil {

	 public static <T> T convertImageDataToBase64(T entity) {
	        try {
	           
	            Class<?> clazz = entity.getClass();

	           
	            Field imageDataField = clazz.getDeclaredField("imageData");
	            imageDataField.setAccessible(true);

	          
	            Field imageBase64Field = clazz.getDeclaredField("imageBase64");
	            imageBase64Field.setAccessible(true);
	          
	          
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
    
 public UserEntity getCurrentUser() {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipleTaamsmaak) {
           
            UserPrincipleTaamsmaak userPrinciple = (UserPrincipleTaamsmaak) authentication.getPrincipal();
            return userPrinciple.getUser();  
        }
        
        return null;  
    }
 
 public void removeSessionMessage() {
		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes()))
				.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute("OrderId");
		session.removeAttribute("error");
		session.removeAttribute("order");
		
	
	}


public byte[] saveaspng(MultipartFile imageFile) throws IOException {
   
    BufferedImage image = ImageIO.read(imageFile.getInputStream());

    if (image == null) {
        throw new IOException("Invalid image file.");
    }

    
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    boolean success = ImageIO.write(image, "png", byteArrayOutputStream);

   
    if (!success) {
        throw new IOException("Failed to convert image to PNG format.");
    }

   
    return byteArrayOutputStream.toByteArray();
}


}
