package com.kosign.wecafe.controller.admin.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosign.wecafe.services.SlideService;

@RestController
@RequestMapping(value="/admin/rest")
public class SlideRestController {

	@Autowired
	SlideService slideService;
	
	@RequestMapping(value="/slides/")
	public Map<String, Object> getAllCategories(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PAGINATION","PAGINATION");
		map.put("SLIDES", slideService.getAllSlides()); 
		return map;
	}
}
