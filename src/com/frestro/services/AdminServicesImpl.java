package com.frestro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.frestro.dao.AdminDao;
import com.frestro.dto.AdminDTO;
import com.frestro.model.Admin;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
@Service("adminServices")
public class AdminServicesImpl implements AdminServices{

	@Autowired
	AdminDao adminDao;
	
	@Override
	public boolean addOrUpdateAdmin(Admin admin) {
		return adminDao.addOrUpdateAdmin(admin);
	}

	@Override
	public boolean checkUniqueUsername(String username) {
		return adminDao.checkUniqueUsername(username);
	}

	@Override
	public boolean login(AdminDTO adminDTO) {
		return adminDao.login(adminDTO);
	}

	@Override
	public Admin getAdminByUsername(String username) {
		return adminDao.getAdminByUsername(username);
	}

	
}
