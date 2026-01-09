package com.email.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import com.email.request.EmailRequest;
import com.email.service.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.mail.internet.MimeMessage;
@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	private Configuration freemarkerConfig;
	
	@Value("${spring.mail.username")
	private String fromEmailId;
	 private static final String OTP_CB = "otpService";
	@Override
	public String send(EmailRequest req) {
		 try {
			 
	            Template template = freemarkerConfig.getTemplate("emailTemplate.ftl");
	            String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(template, req.getModel());
	            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	            helper.setFrom(fromEmailId);
	            helper.setTo(req.getRecipient());
	            helper.setSubject(req.getSubject());
	            helper.setText(htmlBody, true); // `true` enables HTML content
	            ClassPathResource logoResource = new ClassPathResource("static/images/medicare.jpg");
	            helper.addInline("logoImage", logoResource);
	            javaMailSender.send(mimeMessage);
	            return "Email sent successfully!!";
		 } catch (Exception e) {
	            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
	        }
	}

}
