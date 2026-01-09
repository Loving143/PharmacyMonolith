package com.otp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otp.dto.OtpDto;
import com.otp.dto.ValidateOtpRequest;
import com.otp.entity.Otp;
import com.otp.service.OtpService;

@RestController
@RequestMapping("/otp")
public class OtpController {

	@Autowired
	OtpService otpService;
	@PostMapping("/generate")
	public ResponseEntity<?>generateOtp(@RequestParam("username") String userName ){
		Otp otp = otpService.generateOtp(userName);
		OtpDto otpDto = new OtpDto(otp);
		return ResponseEntity.ok(otpDto);
	}
	
	@PostMapping("/validate/otp")
	public boolean validateOtp(@RequestParam("userName") String userName , @RequestParam("otp") String otp ){
		System.out.println("Tis is otp");
		return otpService.validateOtp(userName , otp);
	}
}
