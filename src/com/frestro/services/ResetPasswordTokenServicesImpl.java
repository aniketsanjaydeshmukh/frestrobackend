package com.frestro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.frestro.dao.ResetPasswordTokenDao;
import com.frestro.model.ResetPasswordToken;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
@Service("resetPasswordTokenServices")
public class ResetPasswordTokenServicesImpl implements ResetPasswordTokenServices{

	@Autowired
	ResetPasswordTokenDao resetPasswordTokenDao;

	@Override
	public boolean createOrUpdateResetPasswordToken(
			ResetPasswordToken resetPasswordToken) {
		return resetPasswordTokenDao.createOrUpdateResetPasswordToken(resetPasswordToken);
	}

	@Override
	public ResetPasswordToken getResetPasswordTokenByToken(String token) {
		return resetPasswordTokenDao.getResetPasswordTokenByToken(token);
	}

	@Override
	public boolean deleteResetPasswordToken(long id) {
		return resetPasswordTokenDao.deleteResetPasswordToken(id);
	} 
}
