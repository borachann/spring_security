package com.kosign.wecafe.controller.admin;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosign.wecafe.entities.Expense;
import com.kosign.wecafe.entities.ExpenseDetail;
import com.kosign.wecafe.entities.ImportDetail;
import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.ProductFilter;
import com.kosign.wecafe.forms.ExpenseForm;
import com.kosign.wecafe.forms.ImportForm;
import com.kosign.wecafe.services.ExpenseService; 

@Controller
public class ExpesneController {
	
	@Inject ExpenseService expenseService;
		
	@RequestMapping(value="/admin/expenselist", method=RequestMethod.GET)
	public String listAllExpense(){ 
		
		//model.put("expenses", expenseService.listAllExpense());
		return "admin/expenselist";
	} 
	
	@RequestMapping(value="/admin/getexpenselist", method=RequestMethod.GET) 
	public ResponseEntity<Map<String, Object>> getAllProducts(ProductFilter filter, Pagination pagination,
			@RequestParam(value="start_date") String strStartDate, @RequestParam(value="end_date") String strEndDate) throws ParseException{
		strStartDate = strStartDate + " 00:00:00";
		strEndDate = strEndDate + " 23:59:59";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("expense", expenseService.listAllExpense(pagination, startDate,endDate,true));
		map.put("total_amount", expenseService.getTotalAmount(startDate, endDate)); 
		pagination.setTotalCount(expenseService.count(startDate, endDate));
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination",pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	@RequestMapping(value="/admin/getexpenselist_print", method=RequestMethod.GET) 
	public ResponseEntity<Map<String, Object>> getAllProducts_print(ProductFilter filter, Pagination pagination,
			@RequestParam(value="start_date") String strStartDate, @RequestParam(value="end_date") String strEndDate) throws ParseException{
		strStartDate = strStartDate + " 00:00:00";
		strEndDate = strEndDate + " 23:59:59";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("expense_print", expenseService.listAllExpense(pagination, startDate,endDate,false));
		map.put("total_amount_print", expenseService.getTotalAmount(startDate, endDate)); 
		//pagination.setTotalCount(expenseService.count(startDate, endDate));
		//pagination.setTotalPages(pagination.totalPages());
		//map.put("pagination",pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	@RequestMapping(value="/admin/expenseadd", method=RequestMethod.GET)
	public String expProductAdd(){
		return "admin/expenseadd";
	}
	
	@RequestMapping(value="/admin/expenseupdate/{id}", method=RequestMethod.GET)
	public String viewById(@PathVariable("id") Long expid, Map<String, Object>model){
		 
		model.put("expensepro", expenseService.listExpenseDetail(expid));
		model.put("EXPENSE_ID", expid);		  
		 
		return "admin/expenseupdate";
	}
	
	@RequestMapping(value="/admin/saveupdate/{id}", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Boolean saveupdated(@RequestBody List<ExpenseForm> expenseform, @PathVariable("id") Long expId){
		 
		 try {
			return expenseService.updateExpense(expenseform, expId); 
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}  
		return false;
	}
	
	@RequestMapping(value="/admin/saveExpense", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Boolean saveexpense(@RequestBody List<ExpenseForm> expenseForm)	{
		ExpenseDetail expenseDetails = new ExpenseDetail();
		System.out.println("adfasfd" + expenseForm.size());
		try {
			
			
			return expenseService.saveExpense(expenseForm);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	} 
	
	@RequestMapping(value="/admin/getexpensedetail/{expid}", method=RequestMethod.GET) 
		public @ResponseBody List<ExpenseDetail> expensedetail(@PathVariable("expid") Long expid, Map<String, Object>model){ 
		 
		return expenseService.listExpenseDetail(expid);
	}
	 
}
