package com.frestro.services;

import com.frestro.dto.AdminDTO;
import com.frestro.model.Admin;

public interface AdminServices {
	boolean addOrUpdateAdmin(Admin admin);
	boolean checkUniqueUsername(String username);
	boolean login(AdminDTO adminDTO);
	Admin getAdminByUsername(String username);
}
