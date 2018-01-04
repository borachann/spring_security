package com.kosign.wecafe.controller.admin.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosign.wecafe.services.SellService;
import com.kosign.wecafe.services.SupplierService;

@RestController
@RequestMapping("/admin/rest")
public class SupplierRestController {


	@Autowired
	SupplierService supplierService;
	
	@RequestMapping(value="/suppliers/{pageNumber}/{perPage}")
	public Map<String, Object> getAllSuppliers(@PathVariable("pageNumber") int pageNumber, @PathVariable("perPage") int perPage){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PAGINATION","PAGINATION");
		return map;
	}
	
	@RequestMapping(value="/suppliers/")
	public Map<String, Object> getAllSuppliers(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PAGINATION","PAGINATION");
		map.put("SUPPLIERS", supplierService.getAllSupplier());
		return map;
	}
}
