package com.frestro.dao;

import com.frestro.model.ResetPasswordToken;

public interface ResetPasswordTokenDao {
	boolean createOrUpdateResetPasswordToken(ResetPasswordToken resetPasswordToken);
	ResetPasswordToken getResetPasswordTokenByToken(String token);
	boolean deleteResetPasswordToken(long id);
}
