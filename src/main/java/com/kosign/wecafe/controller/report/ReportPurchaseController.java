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
import com.kosign.wecafe.entities.ProductFilter;
import com.kosign.wecafe.services.UserService;
import com.kosign.wecafe.services.report.AdminReportService;

//TODO: Product REST Controller
@RestController
@RequestMapping("api/admin/reports")
public class ReportPurchaseController {
	
	@Inject AdminReportService adminReportService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/purchasereportdetail", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getpurchasereportdetail(ProductFilter filter, Pagination pagination) throws ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportdetail", adminReportService.getListReportDetailPurchaseRest(pagination, filter.getByYear()));
		pagination.setTotalCount(adminReportService.countDetail(filter.getByYear()));
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination", pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/purchasereportdaily", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getpurchasereportdaily(@RequestParam(value="startDate") String strStartDate, Pagination pagination) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("Date = " + startDate);
		map.put("reportdaily", adminReportService.getListReportDailyPurchaseRest(pagination, startDate,true));  
		map.put("get_total_amount", adminReportService.getListReportDailyPurchaseRest(pagination, startDate,false));
		List<Map> totalRecord=adminReportService.getListReportDailyPurchaseRest(pagination, startDate,false); 
		pagination.setTotalCount(Long.parseLong(totalRecord.size()+""));
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination", pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK); 
	}
	@RequestMapping(value="/purchasereportdaily_print", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getpurchasereportdaily_print(@RequestParam(value="startDate") String strStartDate, Pagination pagination) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("Date = " + startDate);
		map.put("reportdaily_print", adminReportService.getListReportDailyPurchaseRest(pagination, startDate,true));  
		map.put("get_total_amount_print", adminReportService.getListReportDailyPurchaseRest(pagination, startDate,false)); 
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK); 
	}
	@RequestMapping(value="/purchasereportweekly", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getpurchasereportweekly(@RequestParam(value="start_date") String strStartDate, @RequestParam(value="end_date") String strEndDate, 
			Pagination pagination) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportweekly", adminReportService.getListReportWeeklyPurchaseRest(pagination,startDate, endDate,true));
		map.put("get_total_amount", adminReportService.getListReportWeeklyPurchaseRest(pagination, startDate,endDate ,false));
		List<Map> totalRecord=(List<Map>) adminReportService.getListReportWeeklyPurchaseRest(pagination, startDate, endDate,false); 
		pagination.setTotalCount(Long.parseLong(totalRecord.size()+""));  
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination", pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);		
	}
	@RequestMapping(value="/purchasereportweekly_print", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getpurchasereportweekly_print(@RequestParam(value="start_date") String strStartDate, @RequestParam(value="end_date") String strEndDate, 
			Pagination pagination) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportweekly_print", adminReportService.getListReportWeeklyPurchaseRest(pagination,startDate, endDate,false));
		map.put("get_total_amount_print", adminReportService.getListReportWeeklyPurchaseRest(pagination, startDate,endDate ,false)); 
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);		
	}
	
	@RequestMapping(value="/purchasereportmonthly", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getpurchasereportmonthly(@RequestParam(value="start_date") String strStartDate, @RequestParam(value="end_date") String strEndDate, 
			Pagination pagination) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportmonthly", adminReportService.getListReportMonthlyPurchaseRest(pagination,startDate, endDate,true));
		map.put("get_total_amount", adminReportService.getListReportMonthlyPurchaseRest(pagination, startDate,endDate ,false));
		List<Map> totalRecord=(List<Map>) adminReportService.getListReportMonthlyPurchaseRest(pagination, startDate, endDate,false); 
		pagination.setTotalCount(Long.parseLong(totalRecord.size()+"")); 
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination", pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK); 
	}
	@RequestMapping(value="/purchasereportmonthly_print", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getpurchasereportmonthly_print(@RequestParam(value="start_date") String strStartDate, @RequestParam(value="end_date") String strEndDate, 
			Pagination pagination) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportmonthly_print", adminReportService.getListReportMonthlyPurchaseRest(pagination,startDate, endDate,false));
		map.put("get_total_amount_print", adminReportService.getListReportMonthlyPurchaseRest(pagination, startDate,endDate ,false)); 
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK); 
	}
	@RequestMapping(value="/purchasereportyearly", method=RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getpurchasereportyearly(Pagination pagination,
			   @RequestParam(value="start_date") String strStartDate, 
			   @RequestParam(value="end_date") String strEndDate) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate);
		Map<String, Object> map = new HashMap<String, Object>(); 
		System.out.println(strStartDate);
		System.out.println(strEndDate); 
		map.put("reportyear", adminReportService.getListReportYearlyPurcase(pagination,startDate, endDate, true));
		map.put("get_total_amount", adminReportService.getListReportYearlyPurcase(pagination, startDate,endDate ,false));
		List<Map> totalRecord=(List<Map>) adminReportService.getListReportYearlyPurcase(pagination, startDate, endDate,false); 
		pagination.setTotalCount(Long.parseLong(totalRecord.size()+""));
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination", pagination);
		map.put("total_reportyear", adminReportService.getAllPurchaseMonthlyReportsTotal(startDate, endDate));
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK); 
		
	}
	@RequestMapping(value="/purchasereportyearly_print", method=RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getpurchasereportyearly_print(Pagination pagination,
			   @RequestParam(value="start_date") String strStartDate, 
			   @RequestParam(value="end_date") String strEndDate) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate);
		Map<String, Object> map = new HashMap<String, Object>(); 
	
		map.put("reportyear_print", adminReportService.getListReportYearlyPurcase(pagination,startDate, endDate, false));
		map.put("get_total_amount_print", adminReportService.getListReportYearlyPurcase(pagination, startDate,endDate ,false));
		map.put("total_reportyear", adminReportService.getAllPurchaseMonthlyReportsTotal(startDate, endDate));
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK); 
		
	}
}
