package com.kosign.wecafe.controller.admin.rest;
import java.security.Principal;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.entities.ProductFilter;
import com.kosign.wecafe.entities.User;
import com.kosign.wecafe.services.ProductService;
import com.kosign.wecafe.services.UserService;
import com.kosign.wecafe.services.report.AdminReportSaleService;
import com.kosign.wecafe.services.report.AdminReportService;

//TODO: Product REST Controller
@RestController
@RequestMapping("api/admin/reports")
public class ReportSaleController {
	
	@Inject AdminReportSaleService adminReportSaleService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/saledetail", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getpurchasereportdetail(ProductFilter filter, Pagination pagination) throws ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportdetail", adminReportSaleService.getListReportDetailSaleRest(pagination,filter.getByYear())); 
		pagination.setTotalCount(adminReportSaleService.countDetail(filter.getByYear()));
		pagination.setTotalPages(pagination.totalPages()); 
		map.put("pagination", pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);		
		 
	}
	@RequestMapping(value="/salereportdaily", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getsalereportdaily(@RequestParam(value="startDate") String strStartDate, Pagination pagination) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportdaily", adminReportSaleService.getListReportDailySaleRest(pagination, startDate, true));
		map.put("getTotalAmount", adminReportSaleService.getAllTotalAmount(startDate, startDate));
		pagination.setTotalCount(adminReportSaleService.countDaily(startDate));
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination", pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK); 
	}
	@RequestMapping(value="/salereportdaily_print", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getsalereportdaily_print(@RequestParam(value="startDate") String strStartDate, Pagination pagination) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportdaily_print", adminReportSaleService.getListReportDailySaleRest(pagination, startDate, false));
		map.put("getTotalAmount_print", adminReportSaleService.getAllTotalAmount(startDate, startDate)); 
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK); 
	}
	@RequestMapping(value="/salereportweekly", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getsalereportweekly(@RequestParam(value="start_date") String strStartDate, @RequestParam(value="end_date") String strEndDate, 
			Pagination pagination) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate); 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportweekly", adminReportSaleService.getListReportWeeklySaleRest(pagination,startDate, endDate, true));
		map.put("getTotalAmount", adminReportSaleService.getAllTotalAmount(startDate, endDate));
		List<Map> totalRecord = (List<Map>) adminReportSaleService.getListReportWeeklySaleRest(pagination, startDate,endDate,false); 
		pagination.setTotalCount(Long.parseLong(totalRecord.size()+""));
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination", pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);		
	}
	@RequestMapping(value="/salereportweekly_print", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getsalereportweekly_print(@RequestParam(value="start_date") String strStartDate, @RequestParam(value="end_date") String strEndDate, 
			Pagination pagination) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate); 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportweekly_print", adminReportSaleService.getListReportWeeklySaleRest(pagination,startDate, endDate, false));
		map.put("getTotalAmount_print", adminReportSaleService.getAllTotalAmount(startDate, endDate)); 
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);		
	}
	@RequestMapping(value="/salereportmonthly", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getpurchasereportmonthly(@RequestParam(value="start_date") String strStartDate, @RequestParam(value="end_date") String strEndDate, 
			Pagination pagination) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportmonthly", adminReportSaleService.getListReportMonthlySaleRest(pagination, startDate, endDate, true));
		map.put("getTotalAmount", adminReportSaleService.getAllTotalAmount(startDate, endDate));
		List<Map> totalRecord = (List<Map>) adminReportSaleService.getListReportMonthlySaleRest(pagination, startDate,endDate,false);  
		pagination.setTotalCount(Long.parseLong(totalRecord.size()+""));
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination", pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK); 
	} 
	@RequestMapping(value="/salereportmonthly_print", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getpurchasereportmonthly_print(@RequestParam(value="start_date") String strStartDate, @RequestParam(value="end_date") String strEndDate, 
			Pagination pagination) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportmonthly_print", adminReportSaleService.getListReportMonthlySaleRest(pagination, startDate, endDate, false));
		map.put("getTotalAmount_print", adminReportSaleService.getAllTotalAmount(startDate, endDate));  
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK); 
	} 
	@RequestMapping(value="/salereportyearly", method=RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getpurchasereportyearly(
			   @RequestParam(value="start_date") String strStartDate, 
			   @RequestParam(value="end_date") String strEndDate, Pagination pagination) throws ParseException{
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate);
		Map<String, Object> map = new HashMap<String, Object>(); 
		System.out.println(strStartDate);
		System.out.println(strEndDate);
		
		map.put("reportyear", adminReportSaleService.getListReportYearlySale(pagination,startDate, endDate,true));
		map.put("getTotalAmount", adminReportSaleService.getAllTotalAmount(startDate, endDate));
		List<Map> totalRecord = (List<Map>) adminReportSaleService.getListReportYearlySale(pagination, startDate,endDate,false);  
		pagination.setTotalCount(Long.parseLong(totalRecord.size()+""));
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination", pagination); 
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK); 
		
	} 
	@RequestMapping(value="/salereportyearly_print", method=RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getpurchasereportyearly_print(
			   @RequestParam(value="start_date") String strStartDate, 
			   @RequestParam(value="end_date") String strEndDate, Pagination pagination) throws ParseException{
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate);
		Map<String, Object> map = new HashMap<String, Object>(); 
		System.out.println(strStartDate);
		System.out.println(strEndDate); 
		map.put("reportyear_print", adminReportSaleService.getListReportYearlySale(pagination,startDate, endDate,false));
		map.put("getTotalAmount_print", adminReportSaleService.getAllTotalAmount(startDate, endDate)); 
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK); 
		
	}
	@RequestMapping(value="/getsaledetail/{id}", method=RequestMethod.POST)
	public  ResponseEntity<Map<String, Object>> saledetail(@PathVariable("id") Long impid, Map<String, Object>model){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("saleDetail", adminReportSaleService.listAllsaleDetail(impid));
		
	return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		
	}
}
