package com.otp.service;

import org.springframework.http.ResponseEntity;

import com.otp.dto.ValidateOtpRequest;
import com.otp.entity.Otp;

public interface OtpService {

	Otp generateOtp(String userName);

	boolean validateOtp(String userName, String otp);

}
