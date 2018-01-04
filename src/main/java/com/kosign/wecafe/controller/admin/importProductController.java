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

import com.kosign.wecafe.entities.ImportDetail;
import com.kosign.wecafe.entities.ImportProduct;
import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.entities.ProductFilter;
import com.kosign.wecafe.entities.Supplier;
import com.kosign.wecafe.forms.ImportForm;
import com.kosign.wecafe.services.ImportService;
import com.kosign.wecafe.services.ProductService;
import com.kosign.wecafe.services.SupplierService;

@Controller
public class importProductController {
	
	@Inject ImportService importService;
	
	@Inject ProductService productService;
	
	@Inject SupplierService supplierService;
	
	@RequestMapping(value="/admin/get_import_print", method=RequestMethod.GET)
	public String getImportPrint(){		
		return "admin/print_report_import_list";
	}
	
	@RequestMapping(value="/admin/importlist", method=RequestMethod.GET)
	public String listAllImpProduct(Map<String, Object>model){
		//model.put("importproducts",importService.listAllImportProduct());
		return "admin/importlist";
	}
	
	@RequestMapping(value="/admin/getimportlist", method=RequestMethod.GET) 
	public ResponseEntity<Map<String, Object>> getAllProducts(ProductFilter filter, Pagination pagination,
			@RequestParam(value="start_date") String strStartDate, @RequestParam(value="end_date") String strEndDate) throws ParseException{
		strStartDate = strStartDate + " 00:00:00";
		strEndDate = strEndDate + " 23:59:59";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("imports", importService.listAllImportProduct(pagination, startDate,endDate,true));
		
		map.put("total_amount", importService.getTotalAmount(startDate, endDate));
		pagination.setTotalCount(importService.count(startDate, endDate));
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination",pagination);
		System.out.println("data From" + map.get("imports").toString());
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	@RequestMapping(value="/admin/getimportlist_print", method=RequestMethod.GET) 
	public ResponseEntity<Map<String, Object>> getAllProducts_print(ProductFilter filter, Pagination pagination,
			@RequestParam(value="start_date") String strStartDate, @RequestParam(value="end_date") String strEndDate) throws ParseException{
		strStartDate = strStartDate + " 00:00:00";
		strEndDate = strEndDate + " 23:59:59";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startDate = simpleDateFormat.parse(strStartDate);
		Date endDate = simpleDateFormat.parse(strEndDate);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("imports_print", importService.listAllImportProduct(pagination, startDate,endDate,false)); 
		map.put("total_amount_print", importService.getTotalAmount(startDate, endDate));   
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	@RequestMapping(value="/admin/searchproduct", method=RequestMethod.POST)
	public @ResponseBody List<Product> searchProductName(){
		return importService.listAllProduct();
	}
	
	@RequestMapping(value="/admin/searchsupplier", method=RequestMethod.POST)
	public @ResponseBody List<Supplier> searchSupplierName(){
		return importService.listAllSupplier();
	}
	
	@RequestMapping(value="/admin/importadd", method=RequestMethod.GET)
	public String impProductAdd(){
		return "admin/importadd";
	}
	
	@RequestMapping(value="/admin/addImport", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Boolean saveImpProduct(@RequestBody List<ImportForm> importForm)	{
		ImportDetail importDetails = new ImportDetail();
		System.out.println(importForm.size());
		try {			
			return importService.saveImportPro(importForm);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	@RequestMapping(value="/admin/importupdate/{id}", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Boolean importupdate(@RequestBody List<ImportForm> importform, @PathVariable("id") Long impId){
		
		System.out.println("simpId +" + impId);
		 try {
			return importService.updateImportPro(importform, impId);
			//return importService.deleteImportPro(importform, impId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}  
		return false;
	}
	
	@RequestMapping(value="admin/getimportdetail/{id}", method=RequestMethod.GET)
		public @ResponseBody List<Map> importdetail(@PathVariable("id") Long impid, Map<String, Object>model){
			   
		return importService.listAllImportDetail(impid);
			
		}
	 
	@RequestMapping(value="/admin/viewById/{id}", method=RequestMethod.GET)
	public String viewById(@PathVariable("id") Long impid, Map<String, Object>model){
		 
		model.put("importpro", importService.listAllImportDetail(impid));
		model.put("IMPORT_ID", impid);
		  
		 
		return "admin/importupdate";
	}
	

}
