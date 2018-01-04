package com.kosign.wecafe.controller.admin.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosign.wecafe.services.SellService;

@RestController
@RequestMapping("/admin/rest")
public class SaleRestController {


	@Autowired
	SellService sellService;
	
	@RequestMapping(value="/sales/daily", method=RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getAllSalesDailyReports(@RequestParam(value="start_date", required=false, defaultValue="2015-12-01") String strStartDate, 
														   @RequestParam(value="end_date", required=false, defaultValue="2015-12-31") String strEndDate) throws ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		/*
			Pagination pagination = new Pagination();
			pagination.setCurrentPage(currentPage);
			pagination.setPerPage(limit);
			System.out.println(pagination.getPerPage());
			map.put("SALES", sellService.getSellAllList(pagination));
			System.out.println(sellService.getAllSellCount());
			pagination.setTotalCount(sellService.getAllSellCount());
			pagination.setTotalPages(pagination.totalPages());
			map.put("PAGINATION", pagination);
		*/
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate);
		
		System.out.println(strStartDate);
		System.out.println(strEndDate);
		map.put("sales", sellService.getAllSaleDailyReports(startDate, endDate));
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/sales/monthly", method=RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getAllSalesMonthlyReports(
														   @RequestParam(value="start_date") String strStartDate, 
														   @RequestParam(value="end_date") String strEndDate) throws ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		/*
			Pagination pagination = new Pagination();
			pagination.setCurrentPage(currentPage);
			pagination.setPerPage(limit);
			System.out.println(pagination.getPerPage());
			map.put("SALES", sellService.getSellAllList(pagination));
			System.out.println(sellService.getAllSellCount());
			pagination.setTotalCount(sellService.getAllSellCount());
			pagination.setTotalPages(pagination.totalPages());
			map.put("PAGINATION", pagination);
		*/
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate);
		
		System.out.println(strStartDate);
		System.out.println(strEndDate);

		map.put("sales", sellService.getAllSaleMonthlyReports(startDate, endDate));
		map.put("total_sales", sellService.getAllSaleMonthlyReportsTotal(startDate, endDate));
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
}
