package com.kosign.wecafe.services;

import java.util.List;

import com.kosign.wecafe.entities.Category;
import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Unit;

public interface UnitService {
	public List<Unit> getAllUnits(Pagination pagination);
	public List<Unit> searchUnits(String str);
	public List<Unit> getAllUnits();
	public Boolean addNewUnit(Unit unit);
	public Unit findUnit(Long unitid);
	public Unit findUnitByID(Long unitid);
	public Boolean updateUnit(Unit unit);
	public Long count();
}
