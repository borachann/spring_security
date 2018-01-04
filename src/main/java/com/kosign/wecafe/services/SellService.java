package com.kosign.wecafe.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kosign.wecafe.entities.Order;
import com.kosign.wecafe.entities.Pagination;

public interface SellService {

	public List<Map> getSellAllList(Pagination pagination);
	public List<Map<String, Object>> getDetailSellProduct(long id);
	public List<Order> getAllOrders();
	public Long getAllSellCount();
	public List<Map<String, Object>> getAllSaleDailyReports(Date startDate, Date endDate);
	public Map<String, Object> getAllSaleDailyReportsTotal(Date startDate, Date endDate);
	public List<Map<String, Object>> getAllSaleWeeklyReports(Date startDate, Date endDate);
	public Map<String, Object> getAllSaleWeeklyReportsTotal(Date startDate, Date endDate);
	public List<Map<String, Object>> getAllSaleMonthlyReports(Date startDate, Date endDate);
	public Map<String, Object> getAllSaleMonthlyReportsTotal(Date startDate, Date endDate);
	public List<Map<String, Object>> getAllSaleYearlyReports();
	public Map<String, Object> getAllSaleYearlyReportsTotal();
}
