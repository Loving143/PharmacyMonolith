package com.otp.serviceimpl;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otp.entity.Otp;
import com.otp.repository.OtpRepository;
import com.otp.service.OtpService;
@Service
public class OtpServiceImpl implements OtpService{

	@Autowired
	OtpRepository otpRepository;
	@Override
	public Otp generateOtp(String userName) {
		String otpCode = String.format("%06d", new Random().nextInt(999999));
        Otp otp = new Otp();
        otp.setUserName(userName);
        otp.setOtp(otpCode);
        otp.setExpiresAt(Instant.now().plus(Duration.ofMinutes(5))); // 5 minutes expiry
//        otp.setAttempts(null);
        otp.setType("EMAIL");
        return otpRepository.save(otp);
	}
	@Override
	public boolean validateOtp(String userName, String otp) {
		
		Optional<Otp> otpOpt = otpRepository.findByUserNameAndOtp(userName, otp);

	    // 1️⃣ Log properly
	    System.out.println("Validating OTP for user: " + userName + ", input OTP: " + otp);

	    // 2️⃣ Check if record exists
	    if (otpOpt.isEmpty()) {
	        System.out.println("❌ No OTP record found for user or OTP mismatch.");
	        return false;
	    }

	    // 3️⃣ Extract the OTP safely
	    Otp otpEntity = otpOpt.get();
	    System.out.println("✅ Found OTP record: " + otpEntity);

	    // 4️⃣ Validate expiry
	    if (otpEntity.getExpiresAt() == null) {
	        System.out.println("❌ OTP record has no expiry time.");
	        return false;
	    }

	    if (otpEntity.getExpiresAt().isBefore(Instant.now())) {
	        System.out.println("❌ OTP expired for user: " + userName);
	        return false;
	    }

	    // 5️⃣ Compare OTP values
	    boolean valid = otpEntity.getOtp().equals(otp);
	    System.out.println(valid ? "✅ OTP is valid." : "❌ OTP mismatch.");

	    return valid;
	}
	

}
