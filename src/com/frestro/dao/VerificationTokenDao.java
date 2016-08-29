package com.frestro.dao;


import com.frestro.model.VerificationToken;

public interface VerificationTokenDao {

	boolean createOrUpdateVerificationToken(VerificationToken verificationToken);
	VerificationToken getVerificationTokenByToken(String token);
	boolean deleteVerificationToken(long verificationId);
}
