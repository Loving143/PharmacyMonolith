package com.email.service;

import com.email.request.EmailRequest;

public interface EmailService {
	
	String send(EmailRequest req);
}
