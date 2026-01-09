package com.user.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.dto.AddressRequest;
import com.user.entity.Address;
import com.user.response.AddressResponse;
import com.user.service.AddressService;

@RestController
@RequestMapping("/address") //address
public class AddressController {

	@Autowired
    private AddressService addressService;

    @PostMapping("/add")
    public ResponseEntity<String> addAddress(@RequestBody AddressRequest request
                                              ) {
    	 addressService.addAddress(request);
        return ResponseEntity.ok("Address added successfully!!");
    }

    @GetMapping("/list")
    public ResponseEntity<List<AddressResponse>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAddresses());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAddress(@PathVariable Integer id,@RequestBody AddressRequest request) {
    	addressService.updateAddress(id, request);
    	return ResponseEntity.ok("Address updated successfully");
        
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Integer id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok("Address deleted successfully");
    }

    @PutMapping("/makeDefault/{id}")
    public ResponseEntity<String> selectAddress(@PathVariable Integer id) {
        addressService.setDefaultAddress(id);
        return ResponseEntity.ok("Address selected for order");
    }
    
    @GetMapping("/fetchAddress/addressId/{addressId}")
    public AddressResponse fetchAddressById(@PathVariable Integer addressId) {
        return addressService.findById(addressId);
    }
}
