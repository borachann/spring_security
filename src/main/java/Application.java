import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.kosign.wecafe.entities.Category;
import com.kosign.wecafe.entities.Order;
import com.kosign.wecafe.entities.OrderDetail;
import com.kosign.wecafe.entities.OrderDetailsPK;
import com.kosign.wecafe.entities.Product;	

public class Application {

	public static void main(String[] args) {
		
		// Configuration 
		Configuration configuration = new Configuration();
		
		configuration.addAnnotatedClass(Product.class);
		configuration.addAnnotatedClass(Category.class);
		configuration.addAnnotatedClass(Order.class);
		configuration.addAnnotatedClass(OrderDetail.class);
		configuration.addAnnotatedClass(OrderDetailsPK.class);
		
		configuration.setProperties(new Properties(){
			{
				put("hibernate.connection.username","postgres");
				put("hibernate.connection.password","postgres");
				put("hibernate.connection.driver_class","org.postgresql.Driver");
				put("hibernate.connection.url","jdbc:postgresql://192.168.178.72:5432/WeCafe");
				put("hibernate.hbm2ddl.auto","update");
			}
			
		});
		
		// Building SessionFactory 
		SessionFactory sessionFactory = configuration
				.buildSessionFactory(new StandardServiceRegistryBuilder(
						).applySettings(configuration.getProperties()).build());
		
		// Obtain Session and call Persistence Methods 
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		/*Product product = new Product();
		product.setProductName("BIG COCA COLA");
		product.setQuantity(10L);
		product.setUnitPrice(new BigDecimal(1500));
		product.setCostPrice(new BigDecimal(1300));
		product.setSalePrice(new BigDecimal(1500));*/
		//product.setCategoryId(1L);
		/*product.setImage("BIG COCA COLA");		
		
		Category category = new Category();
		category.setCatName("Drink");
		category.setCreatedDate(new Date());
		category.setLastUpdatedDate(new Date());*/
		//Category category = (Category)session.get(Category.class, 1L);	
		
		/*product.setCategory(category);
		category.getProducts().add(product);
		
		session.save(category);
		session.save(product);*/
		
		/*Query query = session.createQuery("FROM Order");
		List<Order> orders = query.list();*/
		
		
		
/*		Order order = new Order();
		order.setOrderDate(new Date());
		order.setCusId(1L);
		
		Product product = session.get(Product.class, new Long(1));
				
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setProduct(product);
		orderDetail.setOrder(order);
		orderDetail.setProComment("I want to add the more sugar...");
		orderDetail.setProQty(new Long(1));
		orderDetail.setProUnitPrice(product.getSalePrice());
		
		order.getOrderDetail().add(orderDetail);
		
		Product product1 = session.get(Product.class, new Long(2));
		
		OrderDetail orderDetail1 = new OrderDetail();
		orderDetail1.setProduct(product1);
		orderDetail1.setOrder(order);
		orderDetail1.setProComment("I don't want to get the ice...");
		orderDetail1.setProQty(new Long(1));
		orderDetail1.setProUnitPrice(product1.getSalePrice());
		
		order.getOrderDetail().add(orderDetail1);
		
		session.save(order);
		//session.save(orderDetail);
		
		session.getTransaction().commit();
		
		Query query = session.createQuery("SELECT SUM(OD.proUnitPrice) FROM OrderDetail OD WHERE OD.pk.order.orderId=?");
		
		query.setParameter(0, order.getOrderId());
		
		BigDecimal amount = (BigDecimal) query.uniqueResult();
		
		System.out.println("ORDER AMOUNT="+ amount);*/
		
		
		
		
		session.close();
	}
	
	/*public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		
		session.close();
	}*/
}
