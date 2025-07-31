package com.example.demo.Service;

import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;




@Service
public class PaymentService {

	@Value("${stripe.secretKey}")
	private String secretKey;

	@Value("${app.Url}")
	private String url;
	
	public String createCheckoutSession() throws StripeException {
		
		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes()))
				.getRequest();
		HttpSession sessions=request.getSession();
		Double total = (Double) sessions.getAttribute("total"); 
		Long unitAmount = (long) (total * 100);
		
		  Stripe.apiKey = secretKey; 
		  
		   SessionCreateParams params = SessionCreateParams.builder()
		            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
		            .setMode(SessionCreateParams.Mode.PAYMENT)
		            .setSuccessUrl(url+"/order-confirmation")
		            .setCancelUrl(url+"/error")
		            .addLineItem(
		                SessionCreateParams.LineItem.builder()
		                    .setQuantity(1L) 
		                    .setPriceData(
		                        SessionCreateParams.LineItem.PriceData.builder()
		                            .setCurrency("INR") 
		                            .setUnitAmount(unitAmount) 
		                            .setProductData(
		                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
		                                    .setName("Generic Product") 
		                                    .build()
		                            )
		                            .build()
		                    )
		                    .build()
		            )
		            .build();

		        
		        Session session = Session.create(params);

		return session.getId();
	}
	
	
}
