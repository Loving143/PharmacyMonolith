package com.user.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.user.dto.AddressRequest;
import com.user.entity.Address;
import com.user.repository.AddressRepository;
import com.user.response.AddressResponse;
import com.user.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService{

	@Autowired
	private AddressRepository addressRepository;
	
	@Override
	public void addAddress(AddressRequest request) {
		UsernamePasswordAuthenticationToken userAuthToken = (UsernamePasswordAuthenticationToken)
				(SecurityContextHolder.getContext().getAuthentication());
		String userName = userAuthToken.getName();
		Address address = new Address(request);
		address.setUserName(userName);
		addressRepository.save(address);
	}

	@Override
	public List<AddressResponse> getAddresses() {
		UsernamePasswordAuthenticationToken userAuthToken = (UsernamePasswordAuthenticationToken)
				(SecurityContextHolder.getContext().getAuthentication());
		String userName = userAuthToken.getName();
		return addressRepository.findByUserName(userName).
				stream().map(AddressResponse::new)
					.collect(Collectors.toList());
	}
	
	@Override
	public void setDefaultAddress(Integer id) {
		UsernamePasswordAuthenticationToken userAuthToken = (UsernamePasswordAuthenticationToken)
				(SecurityContextHolder.getContext().getAuthentication());
		String userName = userAuthToken.getName();
	Address a = addressRepository.findByIdAndUserName(id, userName)
	.orElseThrow(() -> new RuntimeException("Address not found"));
	addressRepository.findByUserName(userName).stream().forEach(x->x.setDefault(false));
	a.setDefault(true);
	addressRepository.save(a);
	}

	@Override
	public void deleteAddress(Integer id) {
		Address address = addressRepository.findById(id).orElseThrow(()->new RuntimeException("Address not found!!"));
		addressRepository.delete(address);
	}

	@Override
	public void updateAddress(Integer id, AddressRequest request) {
		Address address = addressRepository.findById(id).orElseThrow(()->new RuntimeException("Address not found!!"));
		address.setAddressLine1(request.getHouse()+" "+request.getArea()+" "+request.getLandmark());
		address.setAddressLine2(request.getAddressLine2());
		address.setCity(request.getCity());
		address.setLatitude(request.getLatitude());
		address.setLongitude(request.getLongitude());
		address.setMobile(request.getMobile());
		address.setName(request.getName());
		address.setPincode(request.getPincode());
		address.setHouse(request.getHouse());
		address.setArea(request.getArea());
		address.setLandmark(request.getLandmark());
		address.setState(request.getState());
		addressRepository.save(address);
	}

	@Override
	public AddressResponse findById(Integer id) {
		Address address = addressRepository.findById(id).orElseThrow(()->new RuntimeException("Address not found!!"));
		AddressResponse resp = new AddressResponse(address);
		return resp;
	}

}
