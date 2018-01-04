package com.kosign.wecafe.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.kosign.wecafe.entities.RequestStock;
import com.kosign.wecafe.entities.Sale;
import com.kosign.wecafe.entities.User;
import com.kosign.wecafe.forms.Cart;
import com.kosign.wecafe.util.HibernateUtil;

@Service

@SuppressWarnings("unchecked")
public class SellProductServiceImpl implements SellProductsService {

	@Inject
	UserService userService;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Product> getAllProducts() {

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();

			Query query = session.createQuery("FROM Product WHERE status = true");

			List<Product> products = query.list();

			session.getTransaction().commit();
			return products;
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
			// HibernateUtil.getSessionFactory().close();
		}
		return null;
	}

	@Override
	public List<Order> getOrdered() {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("From Order Where status = 1");
			List<Order> ordered = query.list();
			session.getTransaction().commit();
			return ordered;
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return null;
	}
	@Override
	@Transactional
	public List<RequestStock> getRequestStock() {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("From RequestStock Where status ='t'");
			List<RequestStock> requestStock = query.list();
			return requestStock;
		} catch (Exception ex) {
			ex.printStackTrace();
			
		} 
		return null;
	}

	@Override
	@Transactional
	public Boolean addNewSaleProducts(List<Cart> carts) {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();// HibernateUtil.getSessionFactory().openSession();
			// session.beginTransaction();

			Sale sale = new Sale();
			sale.setSaleDatetime(new Date()); 
			User user = userService.findUserByUsername(getPrincipal());
			sale.setUser(user);

			System.out.println("SALE DATE=" + sale.getSaleDatetime());

			/*
			 * Order order = new Order(); order.setOrderDate(new Date());
			 * order.setCusId(1L);
			 * 
			 * List<Product> products = new ArrayList<>();
			 * 
			 * if(sessions.getAttribute("products") != null){ products =
			 * (ArrayList<Product>)sessions.getAttribute("products");
			 * 
			 * } products.add(product);
			 * 
			 * for (int i = 0; i < products.size(); i++) {
			 * 
			 * OrderDetail orderDetaill = new OrderDetail();
			 * orderDetaill.setProduct(product); orderDetaill.setOrder(order);
			 * orderDetaill.setProComment("comment"); orderDetaill.setProQty(new
			 * Long(1)); orderDetaill.setProUnitPrice(product.getSalePrice());
			 * 
			 * order.getOrderDetail().add(orderDetaill); }
			 */

			Order order = new Order();
			order.setOrderDate(new Date());
			// order.setCustomer(userService.findUserByUsername("GENERAL"));
			order.setStatus(2);

			for (Cart cart : carts) {
				Product product = session.get(Product.class, new Long(cart.getProductId()));
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrder(order);
				orderDetail.setProduct(product);
				orderDetail.setProComment("");
				orderDetail.setProQty(cart.getQuantity());
				orderDetail.setProUnitPrice(product.getSalePrice());
				sale.setTotalAmount(sale.getTotalAmount()
						.add((product.getSalePrice().multiply(new BigDecimal(cart.getQuantity())))));

				product.setQuantity(product.getQuantity() - cart.getQuantity());
				session.update(product);

				order.getOrderDetail().add(orderDetail);
			}

			sale.setOrder(order);
			session.saveOrUpdate(sale);
			// session.getTransaction().commit();
			// session.clear();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			// session.getTransaction().rollback();
		} finally {
			// session.close();
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
	public List<OrderDetail> getOrderedDetail(Long orderID) {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			// SELECT * FROM OrderDetail
			Query query = session.createQuery("from OrderDetail OD " + "INNER JOIN OD.pk.order "
					+ "INNER JOIN OD.pk.product where OD.pk.order.orderId = ?");
			query.setParameter(0, orderID);
			return (List<OrderDetail>) query.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public Boolean cancelOrder(Long orderID) {
		// TODO Auto-generated method stub
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			Order order = session.get(Order.class, orderID);
			order.setStatus(0);
			session.save(order);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	@Override
	@Transactional
	public Boolean addOrderToSale(Long orderID) {
		// TODO Auto-generated method stub
		Session session = null;
		try {
		session = sessionFactory.getCurrentSession();
		Sale sale = new Sale();
		User user = userService.findUserByUsername(getPrincipal());
		Order order = session.get(Order.class, orderID);
		sale.setSaleDatetime(new Date());		
		sale.setTotalAmount(order.getOrderAmount());		
		order.setStatus(2);		
		sale.setUser(user);
		sale.setOrder(order);
		session.save(sale);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

//	@Override
//	public Boolean updateOrderProduct(OrderDetail orderDetail) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Boolean updateOrderProduct(Long orderId, Long productId, Long qty) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.getTransaction().begin();
			Query query = session.createQuery("FROM OrderDetail od "
												+ "where od.pk.order.orderId = ?  and od.pk.product.productId =? ");
			query.setParameter(0, orderId);
			query.setParameter(1, productId);
			OrderDetail orderDetail= (OrderDetail)query.uniqueResult();
			orderDetail.setProQty(qty);
			session.update(orderDetail);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			session.getTransaction().rollback();
		}finally {
			session.close();
		}
		return null;
	}

	
	public Boolean deleteOrderProduct(Long orderId, Long productId) {
		Session session =HibernateUtil.getSessionFactory().openSession();
		try{
			session.getTransaction().begin();
			session.getTransaction().begin();
			Query query = session.createQuery("FROM OrderDetail od "
												+ "where od.pk.order.orderId = ?  and od.pk.product.productId =? ");
			query.setParameter(0, orderId);
			query.setParameter(1, productId);
			OrderDetail orderDetail= (OrderDetail)query.uniqueResult();
			session.delete(orderDetail);
			session.getTransaction().commit();
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			session.getTransaction().rollback();
		}finally {
			session.close();
		}
		return null;
	}
	

}
