package com.boulow.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boulow.user.model.Address;
import com.boulow.user.model.AddressType;
import com.boulow.user.repository.AddressRepository;

@Service("address-service")
@Transactional
public class AddressService  {
	
	@Autowired
	private AddressRepository addressRepository;

	public Optional<Address> findById(Long id) {
		return addressRepository.findById(id);
	}

	public Iterable<Address> findByType(AddressType type) {
		return addressRepository.findByType(type);
	}

	public Iterable<Address> findAll() {
		return addressRepository.findAll();
	}

	public Address save(Address address) {
		return addressRepository.save(address);
	}

	public void update(Address obsolete, Address latest) {
		obsolete.setCity(latest.getCity());
		obsolete.setStreet(latest.getStreet());
		obsolete.setSuite(latest.getSuite());
		obsolete.setProvince(latest.getProvince());
		obsolete.setType(latest.getType());
	}

}
