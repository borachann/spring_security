import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.kosign.wecafe.util.HibernateUtil;

public class testsql {

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.getTransaction().begin();
//			
//			Query query = session.createQuery("SELECT new Map("
//				//	+ "O.proQty As Quantity "
//					+ " COUNT(O.pk.product.productId) As productCount "
//					+ ", O.pk.product.productId As prouductID "					
//					+ ", O.pk.product.productName As productName "
//					+ ", SUM(O.proQty) As proQty )"
////					+ ", O.pk.order as orderId"
//					
//					
//					//+ ", O.pk.order.orderId "
//					//+ ", O.pk.order.sale.saleId) "
//					+ "						   FROM OrderDetail O "
////					+ "						   INNER JOIN O.pk.product product "
//					+ "						   INNER JOIN O.pk.order "
//					+ "INNER JOIN O.pk.order.sale  "
////					+ "Where O.pk.order.orderId=:orderId "
//					+ "Where O.pk.order.sale.saleDatetime BETWEEN :startDate AND :endDate "
//					+ "GROUP BY O.pk.product.productId, O.pk.product.productName");
//		
			Query query = session.createQuery("SELECT new Map("
//							+ " O.pk.order.orderId as orderID "
							+ " O.pk.product.productId As prouductID "
							+ ", O.pk.product.productName as productName "	

							+ ", SUM(O.proQty) As proQty "
							+ ", O.proUnitPrice As UnitPrice "
							+ ", SUM(O.proQty * O.proUnitPrice) as Total)"
							
							+ " FROM OrderDetail O "
							+ " INNER JOIN O.pk.order "
							+ " INNER JOIN O.pk.order.sale "
							+ " Where O.pk.order.sale.saleDatetime BETWEEN :startDate AND :endDate "
							+ " and O.pk.order.status = 2roductId, O.pk.product.productName, O.proUnitPrice");
			
//			query.setParameter("orderId", new Long(94));
			SimpleDateFormat dateformat3 = new SimpleDateFormat("dd/MM/yyyy");
			 Date date1 = dateformat3.parse("26/10/2015");
			 Date date2 = dateformat3.parse("29/11/2015");
			query.setParameter("startDate", date1);
			query.setParameter("endDate", date2);
			List<Map> products = (ArrayList<Map>)query.list();
			System.out.println("products.size()" + products.size());
			for(Map product : products){
				System.out.println("productName" + product.get("productName"));
				System.out.println("prouductID " +  product.get("prouductID"));
				System.out.println("proQty " +  product.get("proQty"));
				System.out.println("UnitPrice " +  product.get("UnitPrice"));
				System.out.println("Total" + product.get("Total"));
				
			}
			
			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			session.close();
		}
	}
}
