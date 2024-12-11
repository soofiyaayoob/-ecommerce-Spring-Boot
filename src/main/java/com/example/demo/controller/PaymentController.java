package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.exception.StripeException; 
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;


@Controller
public class PaymentController {
	

	@Value("${stripe.secretKey}")
	private String secretKey;
	

//	@PostMapping("/api/stripe/create-payment-intent") 
//	@ResponseBody
//	public ResponseEntity<Map<String, String>> createPaymentIntent() { Stripe.apiKey = secretKey; Map<String, String> responseData = new HashMap<>(); 
//	try { Map<String, Object> params = new HashMap<>(); params.put("amount", 2000); // Amount in cents (20.00)
//	params.put("currency", "usd"); PaymentIntent paymentIntent = PaymentIntent.create(params);
//	responseData.put("clientSecret", paymentIntent.getClientSecret());
//	}
//	catch (StripeException e)
//	{ System.err.println("StripeException: " + e.getMessage()); responseData.put("error", "Failed to create PaymentIntent");
//	
//	} 
//	return ResponseEntity.ok(responseData); 
//	}
	
	@PostMapping("/create-checkout-session")
	public ResponseEntity<Map<String, String>> createCheckoutSession(HttpSession sessions) {
	    Stripe.apiKey = secretKey;  // Replace with your actual secret key

	    Map<String, String> responseData = new HashMap<>();
	    try {
	       
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
	                            .setUnitAmount((Long) sessions.getAttribute("total")*100) // Amount in cents (e.g., 2000 = $20)
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

	        // Return the session ID
	        responseData.put("id", session.getId());
	    } catch (StripeException e) {
	    	  // Handle error and return it
	        responseData.put("error", e.getMessage());
	    }

	    return ResponseEntity.ok(responseData);
	}
	}

	

