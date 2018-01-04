package com.kosign.wecafe.controller.admin.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosign.wecafe.services.SellService;
import com.kosign.wecafe.services.UserService;

@RestController
@RequestMapping("/admin/rest")
public class UserRestController {


	@Autowired
	UserService userService;
	
	@RequestMapping(value="/users/{pageNumber}/{perPage}")
	public Map<String, Object> getAllSales(@PathVariable("pageNumber") int pageNumber, @PathVariable("perPage") int perPage){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PAGINATION","PAGINATION");
		return map;
	}
	
	@RequestMapping(value="/users/")
	public Map<String, Object> getAllSales(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PAGINATION","PAGINATION");
		map.put("USERS", userService.getAllUsers());
		return map;
	}
}
