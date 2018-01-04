package com.kosign.wecafe.controller.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosign.wecafe.entities.ImportProduct;
import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.ProductFilter;
import com.kosign.wecafe.forms.DateForm;
import com.kosign.wecafe.services.report.AdminReportService;

@Controller
public class ReportAdminController {

	@Inject AdminReportService adminReportService;
	
	@RequestMapping(value="/admin/sellreport", method=RequestMethod.GET)
	public String ListReportSell(Map<String, Object>model){
		model.put("reportSell", adminReportService.getReportListAllSellProduct());
//		for (Map product : adminReportService.getreportListAllProduct()) {
//			System.out.println("productName" + product.get("productName"));
//			
//		}
		return "admin/reportSell";
	}
	
	@RequestMapping(value="/admin/orderreport", method=RequestMethod.GET)
	public String ListReportOrder(Map<String, Object>model){
		model.put("reportOrder", adminReportService.getReportListAllOrderProduct());
		return "admin/reportOrder";
	}
	
	@RequestMapping(value="/admin/purchasereport", method=RequestMethod.GET)
	public String ListReportpurchase(){
	//	model.put("reportPurchase", adminReportService.getReportListAllPurchase());
		return "admin/purchase-report";
	}
	
	@RequestMapping(value="/admin/sale_report", method=RequestMethod.GET)
	public String ListSaleReport(){
	//	model.put("reportPurchase", adminReportService.getReportListAllPurchase());
		return "admin/sale_report";
	}
	
	@RequestMapping(value="/admin/purchasereportdetail", method=RequestMethod.GET)
	public @ResponseBody List<Map> ListReportDetailPurchase(Map<String, Object>model){
		return adminReportService.getListReportDetailPurchase();
		
	}
	
	@RequestMapping(value="/admin/getsearchsellbydate", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Map> searchSalebyDate(@RequestBody DateForm dateForm){
		return adminReportService.getSearchSellbyDate(dateForm);
	}
	@RequestMapping(value="/admin/getsearchorderbydate", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Map> searchOrderbyDate(@RequestBody DateForm dateForm){
		return adminReportService.getSearchOrderbyDate(dateForm);
	}
	
	@RequestMapping(value="/admin/beveragestock", method=RequestMethod.GET)
	public String ListAllBeverageStock(Map<String, Object>model){
		
		return "admin/reportbeveragestock";
	}
	
	@RequestMapping(value="/admin/getsearchBeveragebydate", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getsearchBeveragebydate(Pagination pagination, @RequestParam(value="start_date") String strStartDate) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("beverage", adminReportService.getReportListAllBeverageStock(simpleDateFormat.parse(strStartDate), pagination, true));
		List<Map> recorde = adminReportService.getReportListAllBeverageStock(simpleDateFormat.parse(strStartDate), pagination, false);
		pagination.setTotalCount(Long.parseLong(recorde.size()+ ""));
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination",pagination); 
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	} 
	
	@RequestMapping(value="/admin/getsearchBeveragebydate_print", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getsearchBeveragebydate_print(Pagination pagination, @RequestParam(value="start_date") String strStartDate) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("beverage", adminReportService.getReportListAllBeverageStock(simpleDateFormat.parse(strStartDate), pagination, false));
		//List<Map> recorde = adminReportService.getReportListAllBeverageStock(simpleDateFormat.parse(strStartDate), pagination, false);
		//pagination.setTotalCount(Long.parseLong(recorde.size()+ ""));
		//pagination.setTotalPages(pagination.totalPages());
		//map.put("pagination",pagination); 
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	} 
	
	@RequestMapping(value="/admin/cupstock")
	public String ListAllCupStock(){
		return "admin/reportcupstock";
	}
	
	@RequestMapping(value="/admin/getsearchCupbydate", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Object[]> getsearchCupbyday(@RequestBody DateForm dateForm){
		
		return adminReportService.getReportListAllCupStockbyDate(dateForm);
	}
}
