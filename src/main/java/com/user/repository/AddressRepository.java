package com.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{

	List<Address> findByUserNameOrderByIsDefaultDescIdDesc(String userName);
	Optional<Address> findByIdAndUserName(Integer id, String userName);
	Optional<Address> findByUserNameAndIsDefaultTrue(String user);
	List<Address> findByUserName(String user);
}
