package com.kosign.wecafe.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.forms.RequestForm;
import com.kosign.wecafe.services.RequestService;

@Controller
public class RequestStockController {
	
	@Inject RequestService requestService;
	
	
	@RequestMapping(value="/admin/update_req_qty", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Boolean updateReqQty(@RequestBody RequestForm  req)	{
	
		return requestService.updateReqQty(req);
		
	}
	
	@RequestMapping(value="/admin/approve_request", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Boolean approveRequest(@RequestBody List<RequestForm>  req)	{
	
		return requestService.approveRequest(req);
	}
		
	@RequestMapping(value="/admin/list_request_stock_detail", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> listRequestStockDetail(@RequestParam(name="req_id",defaultValue="")String reqId,@RequestBody Pagination pagination)	{
		
		String reqId1="";
		if(reqId.equals("0") || reqId.equals("")){
			reqId1="";
		}else{
			reqId1=reqId;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
	
		pagination.setTotalCount(requestService.count(reqId1));
		pagination.setTotalPages(pagination.totalPages());
		map.put("RSD", requestService.listRequestDetail(reqId1,pagination,true));
		map.put("RS",requestService.listRequestStock());
		map.put("pagination",pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		
	}
	
	

}
