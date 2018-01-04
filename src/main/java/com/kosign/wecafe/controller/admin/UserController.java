package com.kosign.wecafe.controller.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.ProductFilter;
import com.kosign.wecafe.entities.User;
import com.kosign.wecafe.services.UserService;

@Controller
public class UserController {

	@Inject
	UserService userService;
	@RequestMapping(value="/admin/userlist")
	public String getAllUsers(Map<String, Object> model){
		model.put("roles", userService.getAllUserRoles());
		return "admin/userlist";
	}
 
	
	@RequestMapping(value="/admin/listuser", method=RequestMethod.GET) 
	public ResponseEntity<Map<String, Object>> getUsers(Pagination pagination){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("users", userService.getAllUser(pagination));
		pagination.setTotalCount(userService.count());
		pagination.setTotalPages(pagination.totalPages());
		map.put("pagination",pagination);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/admin/searchuser/{str}", method=RequestMethod.GET) 
	public ResponseEntity<Map<String, Object>> SearchUsers(@PathVariable("str") String userName){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("users", userService.searchUser(userName)); 
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/admin/useradd")
	public String useradd(Map<String, Object> model){
		model.put("roles", userService.getAllUserRoles());
		return "admin/useradd";
	}
	
	@RequestMapping(value="/admin/users/add", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody Boolean addNewUser(@RequestBody User user){
		//System.out.println("userRoles ID = " + user.getUserRoles().toString());
		return userService.saveUser(user);
	}
	
	@RequestMapping(value="/admin/user/{id}")
	public String updateUserView(@PathVariable("id") Long id, Map<String, Object> model){
		User user = userService.findUserById(id);
		System.out.println(user.getFirstName());
		model.put("user", user);
		model.put("roles", userService.getAllUserRoles());
		return "admin/userupdate";
	}
	
	@RequestMapping(value="/admin/users/update", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody Boolean updateUser(@RequestBody User user){
		System.out.println("getUserRoles = " + user.getUserRoles().toString());
		//user.setUserRoles(user.getUserRoles());
		return userService.updateUser(user);
	}
	
	@RequestMapping(value="/admin/users/delete/{id}", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody Boolean deleteUser(@RequestBody @PathVariable("id") Long id){
		
		return userService.deleteUser(id);
	}
	
	
	
	
	@RequestMapping(value = "/admin/users/status/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean changeProductStatus(@RequestBody @PathVariable("id") Long id) {
		
		return userService.updateUserStatus(id);
	}
	
}
