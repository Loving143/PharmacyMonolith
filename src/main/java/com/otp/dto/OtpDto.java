package com.otp.dto;

import java.time.Instant;

import com.otp.entity.Otp;

public class OtpDto {

	private String userName;
    private String type;
    private Instant expiresAt;
    private Integer attempts;
    private Instant consumedAt;
    private Instant createdAt;
    private String codeHash;
    
	public OtpDto(Otp otp) {
		this.userName = otp.getUserName();
		this.type = otp.getType();
		this.expiresAt = otp.getExpiresAt();
		this.attempts = otp.getAttempts();
		this.codeHash = otp.getOtp();
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Instant getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(Instant expiresAt) {
		this.expiresAt = expiresAt;
	}
	public Integer getAttempts() {
		return attempts;
	}
	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}
	public Instant getConsumedAt() {
		return consumedAt;
	}
	public void setConsumedAt(Instant consumedAt) {
		this.consumedAt = consumedAt;
	}
	public Instant getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
	public String getCodeHash() {
		return codeHash;
	}
	public void setCodeHash(String codeHash) {
		this.codeHash = codeHash;
	}
}
