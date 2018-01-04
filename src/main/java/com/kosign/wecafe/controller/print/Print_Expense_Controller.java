package com.kosign.wecafe.controller.print;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Print_Expense_Controller {
@Autowired
ServletContext servletContext;

@RequestMapping(value = "/admin/print_report_expense_list", method = RequestMethod.GET)
public String print_expense_list(){
	return "admin/print_report_expense_list";
}
}
