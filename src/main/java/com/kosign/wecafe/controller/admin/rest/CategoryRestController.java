package com.kosign.wecafe.controller.admin.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosign.wecafe.services.CategoryService;

@RestController
@RequestMapping("/admin/rest")
public class CategoryRestController {


	@Autowired
	CategoryService categoryService;
	
	@RequestMapping(value="/categories/{pageNumber}/{perPage}")
	public Map<String, Object> getAllCategories(@PathVariable("pageNumber") int pageNumber, @PathVariable("perPage") int perPage){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PAGINATION","PAGINATION");
		return map;
	}
	
	@RequestMapping(value="/categories/")
	public Map<String, Object> getAllCategories(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PAGINATION","PAGINATION");
		map.put("CATEGORIES", categoryService.getAllCategories());
		return map;
	}
}
