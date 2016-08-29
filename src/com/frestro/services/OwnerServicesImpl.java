package com.frestro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.frestro.dao.OwnerDao;
import com.frestro.dto.CustomerDTO;
import com.frestro.dto.OwnerDTO;
import com.frestro.model.Owner;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
@Service("ownerServices")
public class OwnerServicesImpl implements OwnerServices{

	@Autowired
	OwnerDao ownerDao;
	
	@Override
	public boolean addOrUpdateOwner(Owner customer) {
		return ownerDao.addOrUpdateOwner(customer);
	}

	@Override
	public boolean checkUnique(String username) {
		return ownerDao.checkUnique(username);
	}

	@Override
	public boolean login(CustomerDTO ownerDTO) {
		return ownerDao.login(ownerDTO);
	}

	@Override
	public Owner getOwnerByUsername(String username) {
		return ownerDao.getOwnerByUsername(username);
	}

	@Override
	public Owner getOwnerById(long ownerId) {
		return ownerDao.getOwnerById(ownerId);
	}

	@Override
	public boolean checkUniqueEmail(String emailID) {
		return ownerDao.checkUniqueEmail(emailID);
	}

	@Override
	public Owner getOwnerByEmail(String emailID) {
		return ownerDao.getOwnerByEmail(emailID);
	}

	@Override
	public boolean checkUniquePhoneNO(long phoneNO) {
		return ownerDao.checkUniquePhoneNO(phoneNO);
	}

	

}
