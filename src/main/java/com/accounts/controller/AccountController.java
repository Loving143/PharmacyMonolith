package com.accounts.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accounts.config.response.LoginResponse;
import com.accounts.dto.AccountDto;
import com.accounts.dto.ChangePasswordRequest;
import com.accounts.dto.ErrorResponse;
import com.accounts.helper.CustomUserDetails;
import com.accounts.helper.JwtUtil;
import com.accounts.response.PasswordChangeResult;
import com.accounts.service.AccountService;
import com.accounts.serviceImpl.EmailServiceImpl;
import com.accounts.serviceImpl.OtpServiceImpl;
import com.email.request.EmailRequest;
import com.otp.entity.Otp;

@RestController
@RequestMapping("/auth")
public class AccountController {
		
		private final AccountService accountService;
		private final EmailServiceImpl emailService;
		private final AuthenticationManager authenticationManager;
		private static final String OTP_CB = "otpService";
		private final JwtUtil jwtUtil;
		private final OtpServiceImpl otpService;
	    public AccountController(AuthenticationManager authenticationManager,AccountService accountService
	    		,JwtUtil jwtUtil, EmailServiceImpl emailService, OtpServiceImpl otpService) {
	        this.accountService = accountService;
			this.emailService = emailService;
			this.authenticationManager = authenticationManager;
			this.jwtUtil = jwtUtil;
			this.otpService = otpService;
	    }

	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody AccountDto accountDto) {
	        try {
	            Authentication authenticationRequest =
	                    new UsernamePasswordAuthenticationToken(accountDto.getUserName(), accountDto.getPassword());

	            Authentication authenticationResponse =
	                    authenticationManager.authenticate(authenticationRequest);

	            CustomUserDetails userDetails =(CustomUserDetails) authenticationResponse.getPrincipal();
	            
	            Otp otp = otpService.generateOtpWithhCircuitBreaker(accountDto.getUserName());
	            
	            Map<String, Object> model = new HashMap<>();
	            model.put("name",otp.getUserName());
	            model.put("email", otp.getUserName());
	            model.put("subscription", "Premium");
	            model.put("otp",otp.getOtp());
	            model.put("bankName","Medicare");
	            
	            EmailRequest req = new EmailRequest(otp.getUserName(),"Your otp for login to Medicare account is : "
	  	      		  +otp.getOtp() +".Please do not share it with any one.","OTP Verification",model);
	            String otpResult = emailService.send(req);
	            String jwtToken = jwtUtil.generateToken(accountDto.getUserName(),userDetails.getAuthorities());
	            LoginResponse response = new LoginResponse(jwtToken,accountDto.getUserName());
	            return ResponseEntity.ok().body(response);
	        }
	        catch (AuthenticationException ex) {
	        	throw new UsernameNotFoundException("Invalid username and password!!");
	        }
	    }
	    
	    @PostMapping("/signup")
	    public ResponseEntity<?>signUp(@RequestBody AccountDto account){
	    	accountService.signup(account);
	    	return ResponseEntity.ok("User registered successfully!");
	    }
	    
	    @PostMapping("/changePassword")
	    public PasswordChangeResult changePassword(@RequestBody ChangePasswordRequest req){
	    	return accountService.changePassword(req);
	    }
	    
	

}
