package com.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.email.request.EmailRequest;
import com.email.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController  {

	@Autowired
	private EmailService emailService;
	
	@PostMapping("/send-otp")
	public ResponseEntity<?> sendEmail(@RequestBody EmailRequest req){
		emailService.send(req);
		return ResponseEntity.ok("Email sent successfully!");
	}
}
