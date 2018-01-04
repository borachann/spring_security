package com.kosign.wecafe.controller.admin;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.services.SellService;

@Controller
public class AdminSellController {

	@Autowired SellService sellService;
	
	@RequestMapping(value="/admin/salelist", method= RequestMethod.GET)
	public String getAllSell(Map<String, Object> model, Principal principal, Pagination pagination){
		System.out.println("sellService.getSellAllList()" + sellService.getSellAllList(pagination));
		model.put("Sale", sellService.getSellAllList(pagination));
		return "admin/salelist";
	}
	
	@RequestMapping(value="/admin/sell/{sellid}", method= RequestMethod.POST)
	public @ResponseBody List<Map<String, Object>> getAllOrders(@PathVariable("sellid") Long id, Map<String, Object> model){
		return sellService.getDetailSellProduct(id);
	}
}
