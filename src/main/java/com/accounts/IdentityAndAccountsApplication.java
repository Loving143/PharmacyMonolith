package com.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.accounts", "com.user", "com.pharmacy", "com.otp", "com.email"})
@EnableDiscoveryClient
@EnableFeignClients
public class IdentityAndAccountsApplication { 

	public static void main(String[] args) {
		SpringApplication.run(IdentityAndAccountsApplication.class, args);
	}

}
