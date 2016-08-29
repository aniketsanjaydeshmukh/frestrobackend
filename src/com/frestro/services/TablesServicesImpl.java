package com.frestro.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.frestro.dao.TablesDao;
import com.frestro.model.Tables;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) 
@Service("tablesServices")
public class TablesServicesImpl implements TablesServices {

	@Autowired
	TablesDao tablesDao;
	
	@Override
	public boolean addOrUpdateTable(Tables table) {
		return tablesDao.addOrUpdateTable(table);
	}

	@Override
	public Set<Tables> getTablesByRestaurant(long restaurantId) {
		return tablesDao.getTablesByRestaurant(restaurantId);
	}

	@Override
	public Tables getTablesById(long tableId) {
		return tablesDao.getTablesById(tableId);
	}

	@Override
	public boolean deleteTables(long tableId) {
		return tablesDao.deleteTables(tableId);
	}

	@Override
	public Tables getLastTable(long restaurantId) {
		return tablesDao.getLastTable(restaurantId);
	}

}
