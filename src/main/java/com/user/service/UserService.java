package com.user.service;

import java.util.List;

import com.user.request.UserProfileRequest;
import com.user.response.UserProfileResponse;

public interface UserService {
	
	UserProfileResponse createUserProfile(UserProfileRequest request);
	UserProfileResponse getUserProfileByUserName(String userName);
	UserProfileResponse updateUserProfile(String userName, UserProfileRequest request);
	void deleteUserProfile(String userName);
	List<UserProfileResponse> getProfilesByBloodGroup(String bloodGroup);

}
