package com.kosign.wecafe.services;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosign.wecafe.entities.Category;
import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.entities.Unit;
import com.kosign.wecafe.entities.User;
import com.kosign.wecafe.forms.ProductForm;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserService userService;
	
	@Override
	@Transactional
	public List<Product> findAllProducts(Pagination pagination) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Product.class);
			criteria.addOrder(Order.desc("status"));
			criteria.addOrder(Order.desc("productId")); 
			criteria.setFirstResult(pagination.offset());
			criteria.setMaxResults(pagination.getPerPage());
			List<Product> products = (List<Product>)criteria.list();
			return products;
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		return null;
	}
	
	@Override
	@Transactional
	public Product findProductById(Long id) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			Product product = session.get(Product.class, id);
			return product;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	@Transactional
	public Long count(){
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			return (Long) session.createCriteria(Product.class).setProjection(Projections.rowCount()).uniqueResult();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0L;
	}
	 
	@Override
	@Transactional
	public boolean addNewProduct(Product product) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			Category category = session.get(Category.class, product.getCategory().getCatId());
			Unit unit = session.get(Unit.class, product.getUnit().getUnitId());
			User user = userService.findUserByUsername(getPrincipal());
			product.setCategory(category);
			product.setUnit(unit);
			//product.setCreatedBy(user);
			//product.setCreatedBy(userService.findUserByUsername(getPrincipal()));
			sessionFactory.getCurrentSession().flush();
			sessionFactory.getCurrentSession().clear();  
			session.saveOrUpdate(product);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		return false;
	}
	
	@Transactional
	@Override
	public boolean deleteProduct(Long id) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			Product product = session.get(Product.class, id);
			product.setStatus(false);
			session.update(product);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	@Transactional
	@Override
	public boolean updateProduct(ProductForm newProduct) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			Category category = session.get(Category.class, newProduct.getCategoryId());
			Product product = session.get(Product.class, newProduct.getProductId());
			Unit unit = session.get(Unit.class, newProduct.getUnitId());
			System.out.println("PRODUCT QUANTITY="+ newProduct.getQuantity());
			product.setProductName(newProduct.getProductName());
			product.setCostPrice(newProduct.getCostPrice());
			product.setUnitPrice(newProduct.getUnitPrice());
			product.setSalePrice(newProduct.getSalePrice());
			product.setUnit(unit);
			product.setCategory(category);
			product.setLastUpdatedDate(new Date());
			product.setImage(newProduct.getImage());
			session.saveOrUpdate(product);	
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	@Override
	@Transactional
	public boolean updateProductStatus(Long id) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			Product product = session.load(Product.class, id);
			product.setStatus(!product.getStatus());
			product.setLastUpdatedDate(new Date());
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
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
	public List<Product> searchProducts(Pagination pagination, String str, Boolean ispagination) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Product.class);
			criteria.add(Restrictions.ilike("productName", str + "%"));
			criteria.addOrder(Order.desc("status"));
			criteria.addOrder(Order.desc("productId")); 
			if(ispagination){
			criteria.setFirstResult(pagination.offset());
			criteria.setMaxResults(pagination.getPerPage());
			}
			List<Product> products = (List<Product>)criteria.list();
			return products;
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		return null;
	}
}
