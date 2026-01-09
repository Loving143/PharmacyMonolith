package com.user.service;

import java.util.List;

import com.user.dto.AddressRequest;
import com.user.entity.Address;
import com.user.response.AddressResponse;

public interface AddressService {

	void addAddress(AddressRequest request);

	List<AddressResponse> getAddresses();

	void setDefaultAddress(Integer id);

	void deleteAddress(Integer id);

	void updateAddress(Integer id, AddressRequest request);

	AddressResponse findById(Integer id);

}
