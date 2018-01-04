package com.kosign.wecafe.services;



import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kosign.wecafe.entities.Expense;
import com.kosign.wecafe.entities.ExpenseDetail;
import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.forms.ExpenseForm;
import com.kosign.wecafe.forms.ImportForm;;

public interface ExpenseService {

	public   List<Expense> listAllExpense(Pagination pagination, Date startDate, Date endDate,boolean ispagination) ;
	public   List<ExpenseDetail> listExpenseDetail(Long id) ;
	public Boolean saveExpense(List<ExpenseForm> expenseForm);
	public Boolean updateExpense(List<ExpenseForm> expenseform, Long id);
	public Long count(Date startDate, Date endDate);

	public Object getTotalAmount(Date startDate, Date endDate);
}
