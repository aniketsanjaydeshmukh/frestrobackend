package com.frestro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.frestro.dao.VerificationTokenDao;
import com.frestro.model.VerificationToken;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
@Service("verificationTokenServices")
public class VerificationTokenServicesImpl implements VerificationTokenServices{

	@Autowired
	VerificationTokenDao verificationTokenDao;
	
	@Override
	public boolean createOrUpdateVerificationToken(VerificationToken verificationToken) {
		return verificationTokenDao.createOrUpdateVerificationToken(verificationToken);
	}

	@Override
	public VerificationToken getVerificationTokenByToken(String token) {
		return verificationTokenDao.getVerificationTokenByToken(token);
	}

	@Override
	public boolean deleteVerificationToken(long verificationId) {
		return verificationTokenDao.deleteVerificationToken(verificationId);
	}

}
