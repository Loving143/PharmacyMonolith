package com.user.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user.entity.UserProfiles;
import com.user.exceptions.BadRequestException;
import com.user.repository.UserRepository;
import com.user.request.UserProfileRequest;
import com.user.response.UserProfileResponse;
import com.user.service.UserService;
@Service
@Transactional
public class UserServiceImpl implements UserService{

    
    @Autowired
    private UserRepository userRepository;
    
    // CREATE - Create new user profile
    public UserProfileResponse createUserProfile(UserProfileRequest request)  {
        // Check if profile already exists
    	UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken)(SecurityContextHolder.getContext().getAuthentication());
        String userName = authToken.getName();
    	if (userRepository.existsByUserName(userName)) {
            throw new BadRequestException("User profile already exists for user: " + userName);
        }
        UserProfiles profile = mapToEntity(userName, request);
        UserProfiles savedProfile = userRepository.save(profile);
        
        return mapToResponse(savedProfile);
    }
    
    public UserProfileResponse updateUserProfile(String userName, UserProfileRequest request){
        UserProfiles existingProfile = userRepository.findByUserName(userName)
                .orElseThrow(() -> new BadRequestException("User with User Name not found!!"));
        updateEntityFromRequest(existingProfile, request);
        UserProfiles updatedProfile = userRepository.save(existingProfile);
        
        return mapToResponse(updatedProfile);
    }
    
    public List<UserProfileResponse> getProfilesByBloodGroup(String bloodGroup) {
        List<UserProfiles> profiles = userRepository.findByBloodGroup(bloodGroup);
        return profiles.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public UserProfileResponse updateBloodGroup(String userName, String bloodGroup){
        UserProfiles profile = userRepository.findByUserName(userName)
                .orElseThrow(() -> new BadRequestException("UserProfile"));
        profile.setBloodGroup(bloodGroup);
        UserProfiles updatedProfile = userRepository.save(profile);
        return mapToResponse(updatedProfile);
    }
    
    private UserProfiles mapToEntity(String userName, UserProfileRequest request) {
    	UserProfiles profile = new UserProfiles(userName, request);
    	return profile;
    }
    
    private void updateEntityFromRequest(UserProfiles profile, UserProfileRequest request) {
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setBloodGroup(request.getBloodGroup());
        profile.setProfilePicture(request.getProfilePicture());
        profile.setHealthCondition(request.getHealthCondition());
    }
    
    private UserProfileResponse mapToResponse(UserProfiles profile) {
    	UserProfileResponse response = new UserProfileResponse(profile);
    	return response;
    }

	@Override
	public UserProfileResponse getUserProfileByUserName(String userName)  {
		UserProfiles userProfiles = userRepository.findByUserName(userName).
				orElseThrow(()->new BadRequestException("User with the User Name not found!"));
		UserProfileResponse response = new UserProfileResponse(userProfiles);
		return response;
	}

	@Override
	public void deleteUserProfile(String userName) {
		UserProfiles userProfile =userRepository.findByUserName(userName).
				orElseThrow(()->new BadRequestException("User with userName not found!!"));
		userRepository.delete(userProfile);
	}
}