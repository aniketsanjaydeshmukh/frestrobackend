package com.frestro.services;

import java.util.Set;

import com.frestro.model.Tables;

public interface TablesServices {
	boolean addOrUpdateTable(Tables table);
	Set<Tables> getTablesByRestaurant(long restaurantId);
	Tables getTablesById(long tableId);
	boolean deleteTables(long tableId);
	Tables getLastTable(long restaurantId);
}
