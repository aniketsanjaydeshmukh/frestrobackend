package com.frestro.services;

import com.frestro.model.VerificationToken;

public interface VerificationTokenServices {
	boolean createOrUpdateVerificationToken(VerificationToken verificationToken);
	VerificationToken getVerificationTokenByToken(String token);
	boolean deleteVerificationToken(long verificationId);
}
