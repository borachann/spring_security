package com.kosign.wecafe.services;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosign.wecafe.entities.Order;
import com.kosign.wecafe.entities.OrderDetail;
import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.forms.Cart;
import com.kosign.wecafe.util.HibernateUtil;

@Service
public class OrderProductServiceImpl implements OrderProductService{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Inject UserService userService;
	
	@Transactional
	@Override
	public boolean addNewOrder(List<Cart> carts) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			
			Order order = new Order();
			order.setOrderDate(new Date());
			//order.setCusId(1L);
			order.setCustomer(userService.findUserByUsername(getPrincipal()));
			order.setStatus(1);
			
			for(Cart cart : carts){
				Product product = session.get(Product.class, new Long(cart.getProductId()));
				
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrder(order);
				orderDetail.setProduct(product);
				orderDetail.setProComment(cart.getComment());
				orderDetail.setProQty(cart.getQuantity());
				orderDetail.setProUnitPrice(product.getSalePrice());
				order.getOrderDetail().add(orderDetail);
			} 
			session.save(order);			
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			
		}
		return false;	
	}
	
	@Override
	public List<Product> getProduct(){
		Session session = null;
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			
			Query query = session.createQuery("FROM Product Where status = true");
			
			List<Product> products = query.list();
			
			return products;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			session.close();
		}
		return null;		
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
}
