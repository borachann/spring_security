package com.kosign.wecafe.services.report;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kosign.wecafe.entities.ImportProduct;
import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.forms.DateForm;

public interface AdminReportService {
	
	public List<Map> getReportListAllSellProduct();	
	public List<Map> getReportListAllOrderProduct();	
	public List<Map> getListReportDetailPurchaseRest(Pagination pagination,int byYear);	
	public List<Map> getListReportDailyPurchaseRest(Pagination pagination,Date startdate, boolean isPagination);	
	public Object getListReportWeeklyPurchaseRest(Pagination pagination,Date startdate, Date enddate, boolean isPagination);
	public Object getListReportMonthlyPurchaseRest(Pagination pagination,Date startdate, Date enddate, boolean isPagination);
	public Object getListReportYearlyPurcase(Pagination pagination,Date startDate, Date endDate,boolean isPagination);	
	public Map<String, Object> getAllPurchaseMonthlyReportsTotal(Date startDate, Date endDate);	
	public List<Map> getListReportYearlyPurchaseRest(int byYear);	
	public List<Map> getListReportDetailPurchase();	
	public List<Map> getSearchSellbyDate(DateForm dateForm);
	public List<Map> getSearchOrderbyDate(DateForm dateForm);	
	//public List<Object[]> getReportListAllBeverageStock(DateForm dateForm);	
	public List<Map> getReportListAllBeverageStock(Date dateForm,Pagination pagination,boolean isPagination);
	public List<Object[]> getReportListAllCupStockbyDate(DateForm dateForm);
	public Long countDetail(int year) throws ParseException;
	public Long countDaily(Date dateTime);
	public Long count();
 
	
}
