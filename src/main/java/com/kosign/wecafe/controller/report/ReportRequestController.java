package com.kosign.wecafe.controller.report;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.forms.DateForm;
import com.kosign.wecafe.services.RequestService;
import com.kosign.wecafe.services.UserService;
import com.kosign.wecafe.services.report.AdminReportRequestService;
import com.kosign.wecafe.services.report.AdminReportService;

//TODO: Product REST Controller
@RestController
@RequestMapping("admin/reports")
public class ReportRequestController {
	
	@Inject AdminReportService adminReportService;
	@Inject AdminReportRequestService report;
	@Inject RequestService reqService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/get_report_request", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> reportRequestDetail(DateForm date, Pagination pagination) throws ParseException{
		
		Map<String, Object> map = new HashMap<String, Object>();	
		map.put("REPORT_REQUEST_DETAIL",report.getListReportDetailRequest(pagination,date) );
		map.put("total_qty", report.getTotalQtyDetailRequest(date));
		pagination.setTotalCount(report.countDetail(date));
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination", pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/get_request_detail", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getRequestDetail(@RequestParam(name="req_id",defaultValue="")String reqId, Pagination pagination) throws ParseException{
		Map<String, Object> map = new HashMap<String, Object>();	
		map.put("REQUEST_DETAIL",reqService.listRequestDetail(reqId, pagination,false));
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/get_request_dailly", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getpurchasereportdaily(DateForm date, Pagination pagination) throws ParseException{
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportdaily", report.getListReportDailyRequest(pagination, date)); 
		map.put("total_qty_pro",report.getTotalProQty(date));
		pagination.setTotalCount(report.getTotalDailyRequest(date));	
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination", pagination);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/get_request_dailly_print", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getpurchasereportdaily_print(DateForm date, Pagination pagination) throws ParseException{
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportdaily", report.getListReportDailyRequest(pagination, date)); 
		map.put("total_qty_pro",report.getTotalProQty(date));
		//pagination.setTotalCount(report.getTotalDailyRequest(date));	
		//pagination.setTotalPages(pagination.totalPages());
		//map.put("pagination", pagination);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/get_request_weekly", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getpurchasereportweekly(DateForm date, 
			Pagination pagination) throws ParseException{
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportweekly", report.getListReportWeeklyRequest(pagination,date,true));
		pagination.setTotalCount(report.countWeekly(date));
	
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination", pagination);
		map.put("total_qty",report.getTotalQtyWeeklyRequest(date));
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);		
	}
	
	@RequestMapping(value="/get_request_weekly_print", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getpurchasereportweekly_print(DateForm date, 
			Pagination pagination) throws ParseException{
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportweekly", report.getListReportWeeklyRequest(pagination,date,false)); 
		map.put("total_qty",report.getTotalQtyWeeklyRequest(date));
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);		
	}
	
  @RequestMapping(value="/get_request_monthly", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getpurchasereportmonthly(@RequestParam(value="start_date") String strStartDate, @RequestParam(value="end_date") String strEndDate, 
			Pagination pagination) throws ParseException{
	  
	  	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate);
	  
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportmonthly",report.getListReportMonthlyRequest(startDate,endDate, pagination,true));
		map.put("total_qty",report.getTotalQtyMonthlyRequest(startDate, endDate));
		pagination.setTotalCount(report.countMonthly(startDate,endDate));
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination", pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		
	}
  @RequestMapping(value="/get_request_monthly_print", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getpurchasereportmonthly_print(@RequestParam(value="start_date") String strStartDate, @RequestParam(value="end_date") String strEndDate, 
			Pagination pagination) throws ParseException{
	  
	  	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate); 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportmonthly",report.getListReportMonthlyRequest(startDate,endDate, pagination,false));
		map.put("total_qty",report.getTotalQtyMonthlyRequest(startDate, endDate)); 
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		
	}
  @RequestMapping(value="/get_request_yearly", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getpurchasereportyearly(Pagination pagination,
			   @RequestParam(value="start_date") String strStartDate, 
			   @RequestParam(value="end_date") String strEndDate) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate);
		Map<String, Object> map = new HashMap<String, Object>(); 		
		map.put("reportyear", report.getListReportYearlyRequest(pagination, startDate, endDate,true));	
		map.put("total_qty", report.getTotalQtyYearlyRequest(startDate, endDate));
		pagination.setTotalCount(report.countYearly(startDate,endDate));
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination", pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);  
	}
  @RequestMapping(value="/get_request_yearly_print", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getpurchasereportyearly_print(Pagination pagination,
			   @RequestParam(value="start_date") String strStartDate, 
			   @RequestParam(value="end_date") String strEndDate) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate);
		Map<String, Object> map = new HashMap<String, Object>(); 		
		map.put("reportyear", report.getListReportYearlyRequest(pagination, startDate, endDate,false));	
		map.put("total_qty", report.getTotalQtyYearlyRequest(startDate, endDate)); 
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);  
	}
}
