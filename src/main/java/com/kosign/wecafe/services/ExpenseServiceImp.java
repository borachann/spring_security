package com.kosign.wecafe.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Transactional;

import com.kosign.wecafe.entities.Expense;
import com.kosign.wecafe.entities.ExpenseDetail;
import com.kosign.wecafe.entities.ImportDetail;
import com.kosign.wecafe.entities.ImportProduct;
import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.entities.Supplier;
import com.kosign.wecafe.entities.User;
import com.kosign.wecafe.forms.ExpenseForm;

import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

@Service
public class ExpenseServiceImp implements ExpenseService {
	@Inject UserService userService;
	@Autowired SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public List<Expense> listAllExpense(Pagination pagination, Date startDate, Date endDate, boolean ispagination) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Expense.class );
			//criteria.addOrder(Order.desc("createdDate"));
			criteria.add(Restrictions.between("exp_date", startDate, endDate));
			if(ispagination){
			criteria.setFirstResult(pagination.offset());
			criteria.setMaxResults(pagination.getPerPage());
			}
			List<Expense>	expenseProducts = (List<Expense>)criteria.list();	
							
			return expenseProducts;
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			
		}		
		return null;
	}
	
	@Override
	@Transactional
	public List<ExpenseDetail> listExpenseDetail(Long id) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
		//	Query query = session.createQuery("from ExpenseDetail ED INNER JOIN ED.expense where ED.expense.expId=" + id);
			Query query = session.createQuery("from ExpenseDetail where expense.expId=" + id);
			List<ExpenseDetail>	expensedetail = (List<ExpenseDetail>)query.list();	
			return expensedetail;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally {			
		}
		return null;
	}

	@Override
	@Transactional
	public Boolean saveExpense(List<ExpenseForm> expenseForm) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			Expense expense = new Expense();
			expense.setExp_date(new Date());
			User user = userService.findUserByUsername(getPrincipal());
			//expense.setExp_user_id(user.getId()); 
			expense.setExp_user(user); 
			for(int i=0; i < expenseForm.size();i++){
				ExpenseDetail expensedetail = new ExpenseDetail();
				expensedetail.setExp_qty(expenseForm.get(i).getQuantity());
				expensedetail.setExp_unitprice(expenseForm.get(i).getUnitPrice());
				expensedetail.setCustomer(expenseForm.get(i).getSuppliername());
				expensedetail.setRemark(expenseForm.get(i).getRemark());
				expensedetail.setExp_description(expenseForm.get(i).getProname());
				expensedetail.setExpense(expense);
				expense.getExpenseDetails().add(expensedetail);
			}
			session.save(expense);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally {			
		}
		
		return false;
	}
	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

	@Override
	@Transactional
	public Boolean updateExpense(List<ExpenseForm> expenseform, Long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		 try {
			 
			 	/*Expense expense = (Expense)session.get(Expense.class, id);
			 	expense.getExpenseDetails().clear();*/
			 
			 	Query query = session.createSQLQuery("delete from tbl_expense_detail where expense_id=?");
			 	query.setParameter(0, id);
			 	
			 	query.executeUpdate();
			 	
			 
				//Query query = session.createQuery("from ExpenseDetail where expense_id=" + id);
				//Set<ExpenseDetail>	expensedetail = (Set<ExpenseDetail>)query.list();
				//session.delete(expensedetail);
				Expense expense = sessionFactory.getCurrentSession().get(Expense.class, id); 
				//ExpenseDetail expenseDetails = sessionFactory.getCurrentSession().get(ExpenseDetail.class, id);
				//System.out.println("IMPORT DETAILS SIZE=" + expenseDetails);
				 /*Set<ExpenseDetail> expenseDetails = expense.getExpenseDetails();
				 System.out.println("IMPORT DETAILS SIZE=" + expenseDetails);
				 sessionFactory.getCurrentSession().delete(expenseDetails); */
				 
			    expense.getExpenseDetails().clear();
			    //System.out.println("IMPORT DETAILS SIZE=" + expenseDetails);
			    System.out.println("IMPORT DETAILS SIZE=" + expense); 
				for (int i=0;i<expenseform.size();i++){
					ExpenseDetail expensedetail = new ExpenseDetail();
					expensedetail.setExp_qty(expenseform.get(i).getQuantity());
					expensedetail.setExp_unitprice(expenseform.get(i).getUnitPrice());
					expensedetail.setCustomer(expenseform.get(i).getSuppliername());
					expensedetail.setRemark(expenseform.get(i).getRemark());
					expensedetail.setExp_description(expenseform.get(i).getProname());
					expensedetail.setExpense(expense);
					expense.getExpenseDetails().add(expensedetail);	
					expensedetail.setExpense(expense);
					
				}
				sessionFactory.getCurrentSession().update(expense); 
				return true;
		 }catch(Exception e){ 
			 e.printStackTrace();
			 System.out.println(e.getMessage());
			}finally {			
			}
		return false;
	}

	@Override
	@Transactional
	public Long count(Date startDate, Date endDate) {

		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			return (Long) session.createCriteria(Expense.class)
					.add(Restrictions.between("exp_date", startDate, endDate))
					.setProjection(Projections.rowCount()).uniqueResult();  
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0L;
	}

	@Override
	@Transactional
	public Object getTotalAmount(Date startDate, Date endDate) {

		Session session = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			session = sessionFactory.getCurrentSession();
			SQLQuery query = session.createSQLQuery("SELECT sum(B.expense_qty* B.expense_unitprice) as total_amount"
					+ " FROM tbl_expense A INNER JOIN tbl_expense_detail B on A.expense_id = B.expense_id "
					+ " WHERE A.expense_date BETWEEN '" + sdf.format(startDate) + "' and '" + sdf.format(endDate) + "'");
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map<String, Object>> sales= (List<Map<String, Object>>)query.list();
			return sales;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0;
	}
}
