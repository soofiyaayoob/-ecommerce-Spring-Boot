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

	public String createCheckoutSession() throws StripeException {
		
		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes()))
				.getRequest();
		HttpSession sessions=request.getSession();
		Double total = (Double) sessions.getAttribute("total"); // Ensure it's a Double
		Long unitAmount = (long) (total * 100);
		
		  Stripe.apiKey = secretKey; 
		  
		   SessionCreateParams params = SessionCreateParams.builder()
		            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
		            .setMode(SessionCreateParams.Mode.PAYMENT)
		            .setSuccessUrl("https://localhost:8080/order-confirmation")
		            .setCancelUrl("https://localhost:8080/error")
		            .addLineItem(
		                SessionCreateParams.LineItem.builder()
		                    .setQuantity(1L) // Quantity is mandatory
		                    .setPriceData(
		                        SessionCreateParams.LineItem.PriceData.builder()
		                            .setCurrency("usd") // Currency for the payment
		                            .setUnitAmount(unitAmount) // Amount in cents (e.g., 2000 = $20)
		                            .setProductData(
		                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
		                                    .setName("Generic Product") // Placeholder name
		                                    .build()
		                            )
		                            .build()
		                    )
		                    .build()
		            )
		            .build();

		        // Create session on Stripe
		        Session session = Session.create(params);

		return session.getId();
	}
	
	
}
