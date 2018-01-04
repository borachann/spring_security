import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.kosign.wecafe.util.HibernateUtil;

public class AppBeverage {

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.getTransaction().begin();
//			String sql = "select  s.sup_name,p.pro_name, p.qty as carried_over, id.pro_qty as purhase, aa.pro_qty as sales,(p.qty  - aa.pro_qty )as balance "
//					+ " from product p"
//					+ " left join (select od.pro_qty,od.pro_id, od.order_id,wo.order_date from order_detail od"
//					+ " Inner join wecafe_order wo"
//					+ " on od.order_id = wo.order_id"
//					+ " where wo.status =2 and  wo.order_date BETWEEN '2015-11-07' and '2015-11-19 23:59') as aa"
//					+ " on p.pro_id = aa.pro_id "
//					+ " left join import_detail id on p.pro_id = id.pro_id"
//					+ " left join supplier s on id.sup_id = s.sup_id"
//					+ "	GROUP BY p.pro_name, p.qty, id.pro_qty ,  aa.pro_qty, s.sup_name";
			String sql = "select c.cat_name,p.pro_id,aa.imp_date,p.pro_name, p.qty as carried_over, id.pro_qty as purhase,ta.qty as takeut,(p.qty  - ta.qty) as InStock from product p"
					+ " left join import_detail id on p.pro_id = id.pro_id"
					+ " left join import aa on id.imp_id = aa.imp_id"
					+ " left join  takeout ta on p.pro_id = ta.pro_id"
					+ " inner join category c on p.cat_id = c.cat_id"
					+ " where aa.imp_date BETWEEN '2015-11-30' and '2015-12-03 23:59'"
					+ " GROUP BY p.pro_name, p.qty, id.pro_qty ,   p.pro_id, aa.imp_date, ta.qty,c.cat_name";
			Query query = session.createSQLQuery(sql);
			List<Object[]> result = query.list();
			for(int i=0;i<result.size();i++)
			{
			    System.out.println("Details of Employee " + (i+1));
			     
			    Object[] data = result.get(i);
			    System.out.println("Employee ID " + data[0]);
			    System.out.println("Employee Name " + data[1]);
			    System.out.println("Email Address " + data[2]);
			    System.out.println("=====================");        
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.getTransaction().rollback();
		}finally {
			session.close();
		}
	}
	
}
