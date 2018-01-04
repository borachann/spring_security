package com.kosign.wecafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/error")
public class HTTPErrorController {

	@RequestMapping(value="/500")
	public String error500(){
		return "error/500";
	}
	
	@RequestMapping(value="/404")
	public String error404(){
		return "error/404";
	}
	
	@RequestMapping(value="/400")
	public String error400(){
		return "error/400";
	}
}
