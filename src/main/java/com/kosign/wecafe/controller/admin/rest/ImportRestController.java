package com.kosign.wecafe.controller.admin.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosign.wecafe.services.ImportService;

@RestController
@RequestMapping("/admin/rest")
public class ImportRestController {


	@Autowired
	ImportService importService;
	
	@RequestMapping(value="/imports/{pageNumber}/{perPage}")
	public Map<String, Object> getAllCategories(@PathVariable("pageNumber") int pageNumber, @PathVariable("perPage") int perPage){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PAGINATION","PAGINATION");
		return map;
	}
	
	@RequestMapping(value="/imports/")
	public Map<String, Object> getAllCategories(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PAGINATION","PAGINATION");
		//map.put("IMPORTS", importService.listAllImportProduct());
		return map;
	}
}
