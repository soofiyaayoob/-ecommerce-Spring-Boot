package com.example.demo.Aspect;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class OtpService {
	
	private static final int OTP_LENGTH = 6;
    private static final int EXPIRY_DURATION = 5 * 60 * 1000; 
    private final SecureRandom secureRandom = new SecureRandom();
    private final Map<String, OtpData> otpStore = new HashMap<>();

    public String generateOtp(String username) {
        String otp = String.format("%06d", secureRandom.nextInt(1_000_000));
        otpStore.put(username, new OtpData(otp, System.currentTimeMillis() + EXPIRY_DURATION));
        return otp;
    }

    public boolean validateOtp(String username, String otp) {
        OtpData otpData = otpStore.get(username);
        System.out.println(otpData);
        if (otpData != null && otpData.getOtp().equals(otp) && System.currentTimeMillis() < otpData.getExpiryTime()) {
            otpStore.remove(username); // Remove OTP after successful validation
            return true;
        }
        return false;
    }

    private static class OtpData {
        private final String otp;
        private final long expiryTime;

        public OtpData(String otp, long expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }

        public String getOtp() {
            return otp;
        }

        public long getExpiryTime() {
            return expiryTime;
        }
    }

}
