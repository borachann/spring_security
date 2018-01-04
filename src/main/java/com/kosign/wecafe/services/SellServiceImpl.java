package com.kosign.wecafe.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosign.wecafe.entities.Order;
import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Sale;
import com.kosign.wecafe.util.HibernateUtil;

@Service
public class SellServiceImpl implements SellService{

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public List<Map> getSellAllList(Pagination pagination) {
		
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.getTransaction().begin();
			//Query query = session.createQuery("From Sale");
			Query query = session.createQuery("SELECT new Map(S.saleId AS ID, S.totalAmount AS TOTAL, S.saleDatetime AS SALE_DATE, S.user.username AS SELLER, S.order.orderId AS ORDER_ID) FROM Sale S");
			query.setMaxResults(pagination.getCurrentPage()*pagination.getPerPage());
			query.setFirstResult((pagination.previousPage())*pagination.getPerPage());
			List<Map> sales= (List<Map>)query.list();
			session.getTransaction().commit();
			return sales;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			session.getTransaction().rollback();
		}finally {
			session.close();
		}
		return null;
	}
	
	@Override
	public List<Map<String, Object>> getDetailSellProduct(long id) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.getTransaction().begin();
			
			Query query = session.createQuery("SELECT new Map(Product.productName AS productName, "
											+ "Product.salePrice as SalePrice, "
											+ "OD.proQty as ProQty, "
											+ "OD.proUnitPrice as ProUnitPrrice, "
											+ "OD.proComment as ProComment, "
											+ "order.orderDate as orderDate) "
											+ "FROM OrderDetail AS OD "
											+ "INNER JOIN OD.pk.product As Product "
											+ "INNER JOIN OD.pk.product.category AS Category "
											+ "INNER JOIN OD.pk.order AS order  "
											+ "Where OD.pk.order.orderId = ?");
			
			
			query.setParameter(0, id);
			List<Map<String, Object>> products = (List<Map<String, Object>>)query.list();
			session.getTransaction().commit();
			return products;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			session.getTransaction().rollback();
			
		}finally {
			session.close();
		}
		return null;
	}
	
	public List<Order> getAllOrders() {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.getTransaction().begin();
			
			Query query = session.createQuery("FROM Order "
											+ "WHERE status = 1 ORDER BY orderDate");
			
			List<Order> orders = (List<Order>)query.list();
			
			for(Order order: orders){
				System.out.println(order.getCustomer());
				//System.out.println(order.getOrderAmount());
			}
			return orders;
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	@Override
	public Long getAllSellCount() {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.getTransaction().begin();
			return (Long) session.createCriteria(Sale.class).setProjection(Projections.rowCount()).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return new Long(0);
	}
	
	@Transactional
	@Override
	public List<Map<String, Object>> getAllSaleDailyReports(Date startDate, Date endDate) {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			SQLQuery query = 
					session.createSQLQuery(
							"SELECT * " +
							"FROM crosstab('SELECT product.pro_name,A.sale_datetime, COALEASCEA.qty " + 
							"		FROM product " +
							"		LEFT JOIN( " +
							"			SELECT order_detail.pro_id " +
							"				 , sale_datetime::date " +
							"				 , SUM(order_detail.pro_qty) AS qty " +
							"			FROM sale " + 
							"			LEFT JOIN wecafe_order ON sale.ord_id = wecafe_order.order_id " +
							"			LEFT JOIN order_detail ON wecafe_order.order_id = order_detail.order_id AND wecafe_order.status=2 " +
							"			WHERE sale_datetime::date BETWEEN ''2015-12-01'' AND ''2015-12-31'' " +
							"			GROUP BY 1, 2 " +
							"			ORDER BY sale_datetime " +
							"		) A " +
							"		ON product.pro_id = A.pro_id " +
							"		','SELECT period::date FROM generate_series(''2015-12-01''::date,''2015-12-31'', ''1 day'' ) AS period;') " +
							"		 AS (pro_name TEXT, " +
							"				 \"1\" INTEGER, " +
							"				 \"2\" INTEGER, " +
							"				 \"3\" INTEGER, " +
							"				 \"4\" INTEGER, " +
							"				 \"5\" INTEGER, " +
							"				 \"6\" INTEGER, " +
							"				 \"7\" INTEGER, " +
							"				 \"8\" INTEGER, " +
							"				 \"9\" INTEGER, " +
							"				 \"10\" INTEGER, " +
							"				 \"11\" INTEGER, " +
							"				 \"12\" INTEGER, " +
							"				 \"13\" INTEGER, " +
							"				 \"14\" INTEGER, " +
							"				 \"15\" INTEGER, " +
							"				 \"16\" INTEGER, " +
							"				 \"17\" INTEGER, " +
							"				 \"18\" INTEGER, " +
							"				 \"19\" INTEGER, " +
							"				 \"20\" INTEGER, " +
							"				 \"21\" INTEGER, " +
							"				 \"22\" INTEGER, " +
							"				 \"23\" INTEGER, " +
							"				 \"24\" INTEGER, " +
							"				 \"25\" INTEGER, " +
							"				 \"26\" INTEGER, " +
							"				 \"27\" INTEGER, " +
							"				 \"28\" INTEGER, " +
							"				 \"29\" INTEGER, " +
							"				 \"30\" INTEGER, " +
							"				 \"31\" INTEGER  );"); 					
			List<Map<String, Object>> sales= (List<Map<String, Object>>)query.list();
			return sales;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<Map<String, Object>> getAllSaleWeeklyReports(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}
		
	@Transactional
	@Override
	public List<Map<String, Object>> getAllSaleMonthlyReports(Date startDate, Date endDate) {
		Session session = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		System.out.println(sdf.format(startDate));
		System.out.println(sdf.format(endDate));
		Calendar calStartDate = Calendar.getInstance();
		calStartDate.setTime(startDate);
		
		Calendar calEndDate = Calendar.getInstance();
		calEndDate.setTime(endDate);
		
		Calendar calendar = calStartDate;
		String[] months = new String[]{"jan","feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
		StringBuilder sb = new StringBuilder();
		StringBuilder sbSelect = new StringBuilder();
		for(int i=calStartDate.get(Calendar.MONTH) ; i<=calEndDate.get(Calendar.MONTH) ; i++){
			calendar.add(Calendar.MONTH, i);
			System.out.println(months[i]);
			sbSelect.append("COALESCE("+months[i]+"[1],0) AS "+months[i].toUpperCase()+"_QTY ,");
			sbSelect.append("COALESCE("+months[i]+"[2],0) AS "+months[i].toUpperCase()+"_AMOUNT ,");
			sb.append(months[i] + " INTEGER[],");
			
		}
		System.out.println(sb.toString().substring(0, sb.toString().lastIndexOf(",")));
		
		System.out.println(calStartDate.getTime());
		System.out.println(calEndDate.getTime());
		//System.out.println(calStartDate.getTime());
		
		try {
			session = sessionFactory.getCurrentSession();
			SQLQuery query = 
					session.createSQLQuery("SELECT " +
										   "row_name[2] As product, " +
										   	sbSelect.toString().substring(0, sbSelect.toString().lastIndexOf(",")) +
										   "FROM " +
										   "	crosstab ( " +
										   "	'SELECT ARRAY[users.lastname::text, product.pro_name::text] As row_name " +
										   "		   ,to_char(import.imp_date, ''mon'')::text As imp_date " +
										   "		   ,ARRAY[SUM(import_detail.pro_qty), SUM(import_detail.unit_price*import_detail.pro_qty)] AS row " +
										   "	 FROM product " +
										   "	 LEFT JOIN import_detail ON product.pro_id = import_detail.pro_id " +
										   "	 LEFT JOIN import ON import.imp_id = import_detail.imp_id AND import.imp_date BETWEEN ''"+sdf.format(startDate)+"'' AND ''"+sdf.format(endDate)+"'' " +   
										   "	 LEFT JOIN users ON import.user_id = users.id " +
										   "	 GROUP BY 1,2 " +
										   "	 ORDER BY 2', " +
										   "'SELECT to_char(date ''"+sdf.format(startDate)+"'' + (n || '' month'')::interval, ''mon'') As short_mname " +  
										   /*" FROM generate_series("+calStartDate.get(Calendar.MONTH)+","+calEndDate.get(Calendar.MONTH)+") n' " +*/
										   " FROM generate_series("+startDate.getMonth()+","+calEndDate.get(Calendar.MONTH)+") n' " +
										   ") AS mthreport ( " +
										   "row_name TEXT [], " + sb.toString().substring(0, sb.toString().lastIndexOf(",")) + ")"+ 
										   /*"jan INTEGER[], " +
										   "feb INTEGER[], " +
										   "mar INTEGER[], " +
										   "apr INTEGER[], " +
										   "may INTEGER[], " +
										   "jun INTEGER[], " +
										   "jul INTEGER[], " +
										   "aug INTEGER[], " +
										   "sep INTEGER[], " +
										   "oct INTEGER[], " +
										   "nov INTEGER[], " +
										   "dec INTEGER[]) " +*/
										   "ORDER BY product;"); 	
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map<String, Object>> sales= (List<Map<String, Object>>)query.list();
			return sales;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Transactional
	@Override
	public Map<String, Object> getAllSaleMonthlyReportsTotal(Date startDate, Date endDate) {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			SQLQuery query = 
					session.createSQLQuery("SELECT " +
										   "	SUM(COALESCE(jan[1],0)) AS TOTAL_JAN_QTY, " +
										   "	SUM(COALESCE(jan[2],0)) AS TOTAL_JAN_AMOUNT, " +
										   "	SUM(COALESCE(feb[1],0)) AS TOTAL_FEB_QTY, " + 
										   "	SUM(COALESCE(feb[2],0)) AS TOTAL_FEB_AMOUNT, " +
										   "	SUM(COALESCE(mar[1],0)) AS TOTAL_MAR_QTY, " +
										   "	SUM(COALESCE(mar[2],0)) AS TOTAL_MAR_AMOUNT, " +
										   "	SUM(COALESCE(apr[1],0)) AS TOTAL_APR_QTY, " +
										   "	SUM(COALESCE(apr[2],0)) AS TOTAL_APR_AMOUNT, " +
										   "	SUM(COALESCE(may[1],0)) AS TOTAL_MAY_QTY, " +
										   "	SUM(COALESCE(may[2],0)) AS TOTAL_MAY_AMOUNT, " +
										   "	SUM(COALESCE(jun[1],0)) AS TOTAL_JUN_QTY, "+
										   "	SUM(COALESCE(jun[2],0)) AS TOTAL_JUN_AMOUNT, " +
										   "	SUM(COALESCE(jul[1],0)) AS TOTAL_JUL_QTY, " +
										   "	SUM(COALESCE(jul[2],0)) AS TOTAL_JUL_AMOUNT, " +
										   "	SUM(COALESCE(aug[1],0)) AS TOTAL_AUG_QTY, " +
										   "	SUM(COALESCE(aug[2],0)) AS TOTAL_AUG_AMOUNT, " +
										   "	SUM(COALESCE(sep[1],0)) AS TOTAL_SEP_QTY, " +
										   "	SUM(COALESCE(sep[2],0)) AS TOTAL_SEP_AMOUNT, " +
										   "	SUM(COALESCE(oct[1],0)) AS TOTAL_OCT_QTY, " +
										   "	SUM(COALESCE(oct[2],0)) AS TOTAL_OCT_AMOUNT, " +
										   "	SUM(COALESCE(nov[1],0)) AS TOTAL_NOV_QTY, " +
										   "	SUM(COALESCE(nov[2],0)) AS TOTAL_NOV_AMOUNT, " +
										   "	SUM(COALESCE(DEC[1],0)) AS TOTAL_DEC_QTY, " +
										   "	SUM(COALESCE(DEC[2],0)) AS TOTAL_DEC_AMOUNT " +
										   "FROM " +
										   "	crosstab ( " +
										   "	'SELECT ARRAY[users.lastname::text, product.pro_name::text] As row_name " +
										   "		   ,to_char(import.imp_date, ''mon'')::text As imp_date " +
										   "		   ,ARRAY[SUM(import_detail.pro_qty), SUM(import_detail.unit_price*import_detail.pro_qty)] AS row " +
										   "	 FROM product " +
										   "	 LEFT JOIN import_detail ON product.pro_id = import_detail.pro_id " +
										   "	 LEFT JOIN import ON import.imp_id = import_detail.imp_id AND date_trunc(''year'',import.imp_date) = date_trunc(''year'',''2015-01-01''::TIMESTAMP) " +   
										   "	 LEFT JOIN users ON import.user_id = users.id " +
										   "	 GROUP BY 1,2 " +
										   "	 ORDER BY 2', " +
										   "'SELECT to_char(date ''2015-01-01'' + (n || '' month'')::interval, ''mon'') As short_mname " +  
										   " FROM generate_series(0,11) n' " +
										   ") AS mthreport ( " +
										   "row_name TEXT [], " +
										   "jan INTEGER[], " +
										   "feb INTEGER[], " +
										   "mar INTEGER[], " +
										   "apr INTEGER[], " +
										   "may INTEGER[], " +
										   "jun INTEGER[], " +
										   "jul INTEGER[], " +
										   "aug INTEGER[], " +
										   "sep INTEGER[], " +
										   "oct INTEGER[], " +
										   "nov INTEGER[], " +
										   "dec INTEGER[]) "); 	
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			Map<String, Object> sales= (Map<String, Object>)query.uniqueResult();
			return sales;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<Map<String, Object>> getAllSaleYearlyReports() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getAllSaleDailyReportsTotal(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getAllSaleWeeklyReportsTotal(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getAllSaleYearlyReportsTotal() {
		// TODO Auto-generated method stub
		return null;
	}
		
	
	
}
