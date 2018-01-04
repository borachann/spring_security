package com.kosign.wecafe.controller.admin;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.hibernate.jpa.criteria.predicate.PredicateImplementor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosign.wecafe.entities.Category;
import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Supplier;
import com.kosign.wecafe.services.SupplierService;

@Controller
public class SupplierController {
	
	@Inject
	private SupplierService SupplierService;
	
	@RequestMapping(value = "/admin/supplierlist", method = RequestMethod.GET)
	public String listAllSuppliers(/*Map<String, Object> model*/) {
		//model.put("suppliers", SupplierService.getAllSupplier());
	
		return "admin/supplierlist";
	}
 
	
	@RequestMapping(value="/admin/listsupplier", method=RequestMethod.GET) 
	public ResponseEntity<Map<String, Object>> getAllSuppliers(Pagination pagination){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("suppliers", SupplierService.getAllSuppliers(pagination));
		pagination.setTotalCount(SupplierService.count());
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination",pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/admin/searchsupplier/{str}", method=RequestMethod.GET) 
	public ResponseEntity<Map<String, Object>> searchSuppliers(@PathVariable("str") String supplierName){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("suppliers", SupplierService.searchSupplier(supplierName)); 
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/admin/supplieradd", method = RequestMethod.GET)
	public String SupplierAdd(){
		return "admin/supplieradd";
	}
	
	@RequestMapping(value="/admin/addSupplier", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Boolean addNewSupplier(@RequestBody Supplier supplierForm, Principal pricipal){
		
		try {
			Supplier suppliers = new Supplier();
			suppliers.setCreatedBy(pricipal.getName());
			suppliers.setCreatedDate(new Date());
			suppliers.setLastUpdatedBy(pricipal.getName());
			suppliers.setLastUpdatedDate(new Date());
			suppliers.setSupplierAddress(supplierForm.getSupplierAddress());
			suppliers.setSupplierEmail(supplierForm.getSupplierEmail());
			suppliers.setSupplierName(supplierForm.getSupplierName());
			suppliers.setSupplierNumber(supplierForm.getSupplierNumber());
			System.out.println("suppliername  = " + supplierForm.getSupplierName());
			return SupplierService.saveSupplier(suppliers);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	@RequestMapping(value="/admin/updateSupplier/{id}", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Boolean updateSupplier(@RequestBody Supplier supplierForm,@PathVariable("id") Long id, Principal principal){
		
		try {
			
			System.out.println("id= " + id);
			Supplier suppliers = new Supplier(); 
			suppliers.setSupId(id);
			suppliers.setCreatedBy(principal.getName());
			suppliers.setCreatedDate(new Date());
			suppliers.setLastUpdatedBy(principal.getName());
			suppliers.setLastUpdatedDate(new Date());
			suppliers.setSupplierAddress(supplierForm.getSupplierAddress());
			suppliers.setSupplierEmail(supplierForm.getSupplierEmail());
			suppliers.setSupplierName(supplierForm.getSupplierName());
			suppliers.setSupplierNumber(supplierForm.getSupplierNumber());
			return SupplierService.updateSupplier(suppliers);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	@RequestMapping(value="/admin/viewupdate/{id}", method=RequestMethod.GET)
	public String updatesup(@PathVariable Long id, Map<String, Object> model){
		model.put("supplier", (Supplier)SupplierService.findSupplierById(id));
		
		System.out.println(((Supplier)(model.get("supplier"))).getSupplierName());
		return "admin/supplierupdate";		
	}
	
	@RequestMapping(value="/admin/delete/{id}" , method=RequestMethod.POST)
	public @ResponseBody Boolean deleteSupplier(@PathVariable Long id){
		System.out.println(id);
		
		try{
			Supplier supplier = new Supplier();
			supplier.setSupId(id);
			//category.setLastUpdatedBy(userService.findUserByUsername(principal.getName()));
			return SupplierService.deleteSupplier(supplier);
			
		}catch(Exception e){
			System.out.println("You failed to delete the category.");
			e.printStackTrace();
		}
		return false;
		
	}
}
