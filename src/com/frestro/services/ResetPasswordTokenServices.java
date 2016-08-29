package com.frestro.services;

import com.frestro.model.ResetPasswordToken;

public interface ResetPasswordTokenServices {
	boolean createOrUpdateResetPasswordToken(ResetPasswordToken resetPasswordToken);
	ResetPasswordToken getResetPasswordTokenByToken(String token);
	boolean deleteResetPasswordToken(long id);
}
