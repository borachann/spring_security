package com.kosign.wecafe.services.report;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.kosign.wecafe.entities.Pagination;

public interface AdminReportSaleService {
	public List<Map> getListReportDetailSaleRest(Pagination pagination,int byYear);
	public List<Map> getListReportDailySaleRest(Pagination pagination, Date startdate, boolean ispagination);
	public Object getListReportWeeklySaleRest(Pagination pagination,Date startdate, Date enddate, boolean isPagination);
	 
	public Object getListReportMonthlySaleRest(Pagination pagination,Date startDate, Date endDate, boolean isPagination);
	public Object getListReportYearlySale(Pagination pagination,Date startDate, Date endDate,boolean isPagination);	
	public Map<String, Object> getAllSaleMonthlyReportsTotal(Date startDate, Date endDate);	
	public List<Map> listAllsaleDetail(Long id);
	public Long countDetail(int year) throws ParseException;
	public Long countDaily(Date dateTime) throws HibernateException, ParseException;
	//public Long countWeekly(Date startDate, Date endDate);
	public Long count();
	public Long getAllTotalAmount(Date startdate, Date enddate);
}
