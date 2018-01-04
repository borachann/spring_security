package com.kosign.wecafe.controller.admin;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Slide;
import com.kosign.wecafe.entities.User;
import com.kosign.wecafe.services.SlideService;
import com.kosign.wecafe.services.UserService;

@Controller
public class SlideController {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SlideService slideService;
	
	@RequestMapping(value="/admin/slides")
	public String listAllSlides(Map<String , Object> model){
		return "admin/slidelist";		
	}
	
	@RequestMapping(value="/admin/listslide", method=RequestMethod.GET) 
	public ResponseEntity<Map<String, Object>> getSlide(Pagination pagination){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("slides", slideService.getAllSlidesPagination(pagination, true));
		List<Slide> totalRecord=slideService.getAllSlidesPagination(pagination, false); 
		pagination.setTotalCount(Long.parseLong(totalRecord.size()+""));
		//pagination.setTotalCount(slideService.count());
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination",pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/admin/slideadd")
	public String categoryadd(){
		return "admin/slideadd";
	}
	
	@RequestMapping(value="/admin/slide/add", method=RequestMethod.POST)
	public  @ResponseBody Boolean addNewSlide(Slide slide, Principal principal){
		
		User user = userService.findUserByUsername(principal.getName());
        
        slide.setCreatedBy(user);
        slide.setCreatedDate(new Date());
        slide.setStatus(true);
        
		System.out.println(user.getUsername());
		return slideService.addNewSlide(slide);
		
	}
	
	@RequestMapping(value="/admin/slide/delete/{id}")
	public @ResponseBody Boolean deleteSlide(@PathVariable("id") Integer id){
		return slideService.deleteSlide(id);
	}
	
	@RequestMapping(value="/admin/slide/update/{id}")
	public String updateSlide(Map<String, Object> model, @PathVariable("id") Integer id){
		System.out.println(id);
		Slide slide = slideService.findSlideById(id);
		System.out.println(slide.getImage());
		model.put("slide", slide);
		return "admin/slideupdate";
	}
	
	@RequestMapping(value="/admin/slide/update", method=RequestMethod.POST)
	public @ResponseBody Boolean updateSlide(Slide slide){
		return slideService.updateSlide(slide);
	}
	
}
