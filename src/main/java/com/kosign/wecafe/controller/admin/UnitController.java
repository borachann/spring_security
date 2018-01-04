package com.kosign.wecafe.controller.admin;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosign.wecafe.entities.Category;
import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Unit;
import com.kosign.wecafe.forms.UnitForm;
import com.kosign.wecafe.services.UnitService;

@Controller
public class UnitController {

	@Inject UnitService unitService;
	
	@RequestMapping(value="/admin/unitlist")
	public String getListAllUnit(/*Map<String, Object>model*/){
		//model.put("units", unitService.getAllUnits());
		return "admin/unitlist";
	}
	@RequestMapping(value="/admin/listunit", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listunit(Pagination pagination){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("units", unitService.getAllUnits(pagination));
		pagination.setTotalCount(unitService.count());
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination",pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/admin/searchunit/{str}", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> searchUnit(@PathVariable("str") String unitName){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("unitSearch", unitService.searchUnits(unitName));
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/admin/unitadd")
	public String setunit(Map<String, Object>model){
		
		model.put("units", unitService.getAllUnits());
		return "admin/unitadd";
	}
	
	@RequestMapping(value="/admin/updateunit/{unitid}", method = RequestMethod.GET)
	public String updateunit(@PathVariable("unitid") Long unitids ,Map<String, Object>model){
		System.out.println(" unitids = " + unitids);
		model.put("findunit", unitService.findUnit(unitids));	
		
		return "admin/unitupdate";
	}
	
	
	@RequestMapping(value="/admin/unitupdate", method = RequestMethod.POST)
	public @ResponseBody Boolean unitupdate(UnitForm form ,Map<String, Object>model){ 
		 Unit unit = unitService.findUnitByID(form.getUnitId());
		 unit.setUnitName(form.getUnitName());
		 unit.setQty(form.getUnitqty());
		 unit.setTo(form.getUnittype());
	        
			return unitService.updateUnit(unit);
	}
	
	@RequestMapping(value="/admin/addnewunit", method=RequestMethod.POST)
	public  @ResponseBody Boolean addNewunit(UnitForm form, Principal principal, HttpServletRequest request){
		
		
		Unit unit = new Unit();
		unit.setUnitName(form.getUnitName());
		unit.setQty(form.getUnitqty());
		unit.setTo(form.getUnittype());
		
		return unitService.addNewUnit(unit); 
		
	}
}
