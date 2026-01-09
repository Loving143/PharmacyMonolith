package com.accounts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accounts.dto.OtpDto;
import com.otp.entity.Otp;
import com.otp.service.OtpService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class OtpServiceImpl {
	
	private static final String OTP_CB = "otpService";
	
	@Autowired
	private OtpService otpService;
	
	@CircuitBreaker(name = OTP_CB, fallbackMethod = "otpFallback")
	public Otp generateOtpWithhCircuitBreaker(String userName) {
		return otpService.generateOtp(userName);
	}

	public OtpDto otpFallback(String userName, Throwable ex) {
		OtpDto otp = new OtpDto();
		otp.setRes("OTP service temporarily unavailable. OTP could not be sent.");
		return otp;
	}

}
