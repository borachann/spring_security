package com.kosign.wecafe.services;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.criterion.Order;
import com.kosign.wecafe.entities.Category;
import com.kosign.wecafe.entities.ImportProduct;
import com.kosign.wecafe.entities.Pagination;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	UserService userService;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Category> getAllCategories() {
		 try{
			return (List<Category>)sessionFactory.getCurrentSession()
												 .createCriteria(Category.class)
												 .add(Restrictions.eq("status", true)).list();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		} 
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Category> getAllCategories(Pagination pagination) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Category.class ); 
			criteria.add(Restrictions.eq("status", true));
			criteria.addOrder( Order.desc("catId") );
			criteria.setFirstResult(pagination.offset());
			criteria.setMaxResults(pagination.getPerPage());			
			List<Category>	categorylist = (List<Category>)criteria.list();	
			return categorylist;
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null; 
	}

	@Override
	@Transactional
	public Boolean addNewCategory(Category category) {
		try{
			sessionFactory.getCurrentSession().save(category);
			return true;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public Boolean updateCategory(Category category) {
		try{
			category.setLastUpdatedDate(new Date());
			sessionFactory.getCurrentSession().update(category);
			return true;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public Boolean deleteCategory(Category deleteCategory) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			Category category = session.get(Category.class, deleteCategory.getCatId());
			category.setStatus(false);
			category.setLastUpdatedDate(new Date());
			//category.setLastUpdatedBy(deleteCategory.getLastUpdatedBy());
			session.saveOrUpdate(category);
			return true;			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public Category findCategoryById(Long id) {
		try{
			return sessionFactory.getCurrentSession().get(Category.class, id);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public Long count() {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			return (Long) session.createCriteria(Category.class)
								 .add(Restrictions.eq("status", true))
								 .setProjection(Projections.rowCount()).uniqueResult();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0L;
	}
	@Override
	@Transactional
	public List<Category> searchCategories(String categoryName) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Category.class ); 
			criteria.add(Restrictions.ilike("catName", categoryName + "%"));
			criteria.add(Restrictions.eq("status", true));
			criteria.addOrder( Order.desc("catId") ); 			
			List<Category>	categorylist = (List<Category>)criteria.list();	
			return categorylist;
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null; 
	}
	
	

}
