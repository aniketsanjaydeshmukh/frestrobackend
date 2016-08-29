package com.frestro.services;

import com.frestro.dto.CustomerDTO;
import com.frestro.dto.OwnerDTO;
import com.frestro.model.Owner;

public interface OwnerServices {
	boolean addOrUpdateOwner(Owner customer);
	boolean checkUnique(String username);
	boolean checkUniqueEmail(String emailID);
	boolean checkUniquePhoneNO(long phoneNO);
	boolean login(CustomerDTO ownerDTO);
	Owner getOwnerByUsername(String username);
	Owner getOwnerById(long ownerId);
	Owner getOwnerByEmail(String emailID);
}
