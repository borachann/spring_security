
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.kosign.wecafe.util.HibernateUtil;

public class AppSelectOrder {

	public static void main(String[] args) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
	
		try {
			session.getTransaction().begin();
			
			int pageNumber = 1;
			int pageSize = 2;
//			Order A = new Order();
//			OrderDetail B = new OrderDetail();
//			Map<Object,String> col = new HashMap<>();
//			col.put(A,"A");
//			col.put(B,"B");
			//Query query = session.createQuery("select * FROM wecafe_order A INNER JOIN order_detail B where A.order_id = B.order_id");
			/*Query query = session.createQuery("SELECT new Map(P.productName As ProductName, P.quantity As Quantity, P.category.catName As CategoryName) FROM Product P");*/
			Query query = session.createQuery("SELECT new Map(O.proQty As Quantity, O.pk.product.productName As ProductName, O.pk.product.category.catName As CategoryName) "
					+ "						   FROM OrderDetail O "
					+ "						   INNER JOIN O.pk.product "
					+ "						   INNER JOIN O.pk.product.category Where O.pk.order.orderId=:orderId");
			query.setParameter("orderId", new Long(10));
			query.setFirstResult((pageNumber-1)*pageSize);
			query.setMaxResults(pageSize);
			List<Map> products = (ArrayList<Map>)query.list();
			/*System.out.println(products.size());*/
			for(Map product : products){
				System.out.println(product.get("productName"));
				System.out.println(product.get("Quantity"));
				System.out.println(product.get("CategoryName"));
			}
		/*	
			while(orderDetails.iterator().hasN5ext()){
				System.out.println(orderDetails.iterator().next().getProduct().getProductName());
			}*/
			
			/*Order order = (Order) session.get(Order.class, new Long(10));
			Iterator<OrderDetail> orderDetails = order.getOrderDetail().iterator();
			while(orderDetails.hasNext()){
				OrderDetail orderDetail = orderDetails.next();
				System.out.println("QUANTITY="+ orderDetail.getProQty());
				System.out.println("PRODUCT NAME=" + orderDetail.getProduct().getProductName());
			}	*/
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.getStackTrace();
			session.getTransaction().rollback();
		}
		finally {
			session.close();
		}
	}
}
