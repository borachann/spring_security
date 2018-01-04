import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.kosign.wecafe.util.HibernateUtil;

public class test {

	public static void main(String[] args) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.getTransaction().begin();
			Query query = session.createQuery("SELECT new Map("
					+ "io.proQty as proQty"
					+ ",ip.impId as impId"
					+ ", io.unitPrice as unitPrice"
					+ ", io.proStatus as status"
					+ ",product.productName as productName"
					+ ", product.productId as productId"
					+ ",ip.userId as userId"
					+ ",ip.impDate as impDate"
					+ ",ip.totalAmount as totalAmount)"
					+ "FROM ImportDetail io "
					+ "INNER JOIN io.pk1.product product "
					+ "INNER JOIN io.pk1.importProduct ip "
					
//					+ "INNER JOIN io.supplier sp "
//					+ "WHERE ip.impId = ? "
					);
			List<Map> products = (ArrayList<Map>)query.list();
			System.out.println("products.size()" + products.size());
			for(Map product : products){
				System.out.println("product.productName" + product.get("productName"));
				System.out.println("qty" + product.get("proQty"));
				System.out.println("user id" +  product.get("userId"));
				System.out.println("status " +  product.get("status"));
				System.out.println("status " +  product.get("unitPrice"));
				System.out.println("totalAmount " +  product.get("totalAmount"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			session.getTransaction().rollback();
		}finally {
			session.close();
		}
	}

}
