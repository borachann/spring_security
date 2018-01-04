package com.kosign.wecafe.services.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosign.wecafe.entities.Expense;
import com.kosign.wecafe.entities.ImportProduct;
import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Sale;
import com.kosign.wecafe.forms.DateForm;
import com.kosign.wecafe.util.HibernateUtil;

@Service
public class AdminReportServiceImp implements AdminReportService {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Map> getReportListAllSellProduct() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.getTransaction().begin();
			Query query = session.createQuery(
					"SELECT new Map(O.pk.product.productId As prouductID " 
									+ ", O.pk.product.productName as productName "
									+ ", SUM(O.proQty) As proQty "
									+ ", O.proUnitPrice As UnitPrice "
									+ ", SUM(O.proQty * O.proUnitPrice) as Total)"
						+ " FROM OrderDetail O " 
						+ " INNER JOIN O.pk.order " 
						+ " INNER JOIN O.pk.order.sale "
						+ " Where O.pk.order.sale.saleDatetime BETWEEN :startDate AND :endDate "
						+ " and O.pk.order.status = 2 "
						+ " GROUP BY  O.pk.product.productId, O.pk.product.productName, O.proUnitPrice");

			// query.setParameter("orderId", new Long(94));
			SimpleDateFormat dateformat3 = new SimpleDateFormat("dd/MM/yyyy");
			Date date1 = dateformat3.parse("26/10/2015");
			Date date2 = dateformat3.parse("29/11/2015");
			query.setParameter("startDate", date1);
			query.setParameter("endDate", date2);
			List<Map> products = (ArrayList<Map>) query.list();
			System.out.println("products.size()" + products.size());
 
			return products;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			session.getTransaction().rollback();
		} finally {
			session.close();
		}

		return null;
	}

	@Override
	public List<Map> getReportListAllOrderProduct() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.getTransaction().begin();
			/*Query query = session.createQuery(
					"SELECT new Map(O.pk.product.productId As prouductID " 
									+ ", O.pk.product.productName as productName "
									+ ", SUM(O.proQty) As proQty "
									+ ", O.proUnitPrice As UnitPrice "
									+ ", SUM(O.proQty * O.proUnitPrice) as Total)"
						+ " FROM OrderDetail O " 
						+ " INNER JOIN O.pk.order " 
						+ " INNER JOIN O.pk.order.sale "
						+ " Where O.pk.order.sale.saleDatetime BETWEEN :startDate AND :endDate "
						+ " and O.pk.order.status = 1 "
						+ " GROUP BY  O.pk.product.productId, O.pk.product.productName, O.proUnitPrice");

			*/
		
			Query query = session.createQuery("Select new Map("
					+ "p.productName as proname,"
					+ "SUM(impdetail.proQty) as proqty,"
					+ "impdetail.unitPrice as prounitprice,"
				//	+ "impdetail.supplier.supplierName as supname, "
					+ " SUM(impdetail.proQty * impdetail.unitPrice) as Total)"
					+ " FROM ImportDetail impdetail" 
					+ " INNER JOIN impdetail.pk1.product p"
					+ " GROUP BY p.productName, impdetail.unitPrice ");
			// query.setParameter("orderId", new Long(94));
//			SimpleDateFormat dateformat3 = new SimpleDateFormat("dd/MM/yyyy");
//			 Date date1 = dateformat3.parse("26/10/2015");
//			 Date date2 = dateformat3.parse("29/11/2015");
//			query.setParameter("startDate", date1);
//			query.setParameter("endDate", date2);
			List<Map> products = (ArrayList<Map>) query.list();
			System.out.println("products.size()" + products.size());
//			for (Map product : products) {
//				System.out.println("productName" + product.get("productName"));
//				System.out.println("prouductID " + product.get("prouductID"));
//				System.out.println("proQty " + product.get("proQty"));
//				System.out.println("UnitPrice " + product.get("UnitPrice"));
//				System.out.println("Total" + product.get("Total"));
//			}
			return products;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public List<Map> getSearchSellbyDate(DateForm dateForm) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.getTransaction().begin();
			Query query = session.createQuery(
					"SELECT new Map(O.pk.product.productId As prouductID " 
									+ ", O.pk.product.productName as productName "
									+ ", SUM(O.proQty) As proQty "
									+ ", O.proUnitPrice As UnitPrice "
									+ ", SUM(O.proQty * O.proUnitPrice) as Total)"
						+ " FROM OrderDetail O " 
						+ " INNER JOIN O.pk.order " 
						+ " INNER JOIN O.pk.order.sale "
						+ " Where O.pk.order.sale.saleDatetime BETWEEN :startDate AND :endDate "
						+ " and O.pk.order.status = 2 "
						+ " GROUP BY  O.pk.product.productId, O.pk.product.productName, O.proUnitPrice");

			// query.setParameter("orderId", new Long(94));
			SimpleDateFormat dateformat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startdate = dateformat3.parse(dateForm.getStartdate());
			Date enddate = dateformat3.parse(dateForm.getEnddate());
			query.setParameter("startDate", startdate);
			query.setParameter("endDate", enddate);
			List<Map> products = (ArrayList<Map>) query.list();
			System.out.println("products.size()" + products.size());
//			for (Map product : products) {
//				System.out.println("productName" + product.get("productName"));
//				System.out.println("prouductID " + product.get("prouductID"));
//				System.out.println("proQty " + product.get("proQty"));
//				System.out.println("UnitPrice " + product.get("UnitPrice"));
//				System.out.println("Total" + product.get("Total"));
//			}
			
			System.out.println("=======================================" + query );
			return products;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			session.getTransaction().rollback();
		} finally {
			session.close();
		}

		return null;
	}

	@Override
	public List<Map> getSearchOrderbyDate(DateForm dateForm) { 
		

		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.getTransaction().begin();
			/*Query query = session.createQuery(
					"SELECT new Map(O.pk.product.productId As prouductID " 
									+ ", O.pk.product.productName as productName "
									+ ", SUM(O.proQty) As proQty "
									+ ", O.proUnitPrice As UnitPrice "
									+ ", SUM(O.proQty * O.proUnitPrice) as Total)"
						+ " FROM OrderDetail O " 
						+ " INNER JOIN O.pk.order " 
						+ " INNER JOIN O.pk.order.sale "
						+ " Where O.pk.order.sale.saleDatetime BETWEEN :startDate AND :endDate "
						+ " and O.pk.order.status = 1 "
						+ " GROUP BY  O.pk.product.productId, O.pk.product.productName, O.proUnitPrice");						


			*/
//			SELECT pd.pro_id, pd.imp_id, pd.pro_qty, pd.pro_status, pd.sup_id, pd.unit_price from import_detail pd INNER JOIN import p on pd.imp_id = p.imp_id where p.imp_date = '2015-11-13'
			
			Query query = session.createQuery("Select new Map("
					+ "p.productName as proname,"
					+ "SUM(impdetail.proQty) as proqty,"
					+ "impdetail.unitPrice as prounitprice,"
				//	+ "impdetail.supplier.supplierName as supname, "
					+ " SUM(impdetail.proQty * impdetail.unitPrice) as Total)"
					+ " FROM ImportDetail impdetail" 
					+ " Inner join impdetail.pk1.importProduct imppro"
					+ " INNER JOIN impdetail.pk1.product p"
					+ " Where imppro.impDate BETWEEN :startDate AND :endDate "
					+ " GROUP BY p.productName, impdetail.unitPrice ");
			 // query.setParameter("orderId", new Long(94));
			
			SimpleDateFormat dateformat3 = new SimpleDateFormat("yyyy-MM-dd");
			Date startdate = dateformat3.parse(dateForm.getStartdate());
			Date enddate = dateformat3.parse(dateForm.getEnddate());
			query.setParameter("startDate", startdate);
			query.setParameter("endDate", enddate); 
			List<Map> products = (ArrayList<Map>) query.list();
			System.out.println("products.size()" + products.size());
//			for (Map product : products) {
//				System.out.println("productName" + product.get("productName"));
//				System.out.println("prouductID " + product.get("prouductID"));
//				System.out.println("proQty " + product.get("proQty"));
//				System.out.println("UnitPrice " + product.get("UnitPrice"));
//				System.out.println("Total" + product.get("Total"));
//			}
			return products;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	@Transactional
	public List<Map> getListReportDetailPurchaseRest(Pagination pagination, int byyear) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			session.getTransaction().begin();
			SQLQuery query = session.createSQLQuery(
					"SELECT A.imp_id AS purchase_id "
				 + "   , A.imp_date AS purchase_date "
				 + "	   , CONCAT(C.lastname, ' ', C.firstname) AS purchase_by "
				 + "	   , SUM(pro_qty * unit_price) AS purchase_total_amount "
				 + "	   , '0' AS purchase_type "
				 + "	FROM import A "
				 + "	INNER JOIN import_detail B ON A.imp_id = B.imp_id "
				 + "	LEFT JOIN users C ON C.id = A.user_id "
				 + "	WHERE EXTRACT(YEAR FROM A.imp_date) = " + byyear
				 + "	GROUP BY 1,2,3 " 
				 + "	UNION ALL "
				 + "	SELECT A.expense_id AS purchase_id "
					+ "   , A.expense_date AS purchase_date "
				+ "	   , CONCAT(C.lastname, ' ', C.firstname) AS purchase_by "
				+ "	   , SUM(B.expense_qty * B.expense_unitprice) AS purhcase_total_amount "
				+ "	   , '1' AS purchase_type "
				+ "	FROM tbl_expense A "
				+ "	INNER JOIN tbl_expense_detail B ON A.expense_id = B.expense_id "
				+ "	LEFT JOIN users C ON A.exp_user_id = C.id "
							+ "	WHERE EXTRACT(YEAR FROM A.expense_date) = " + byyear
				+ "	GROUP BY 1,2,3 "
				+ "	ORDER BY 2 DESC, 1 DESC");
			query.setFirstResult(pagination.offset());
			query.setMaxResults(pagination.getPerPage());
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map>	importProducts = (List<Map>)query.list();	
			return importProducts;
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			
		}		
		return null;
	}
	
	@Override
	@Transactional
	public Long countDetail(int year) throws ParseException{
		String startDate = year +"-01-01";
		String endDate = year + "-12-31";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startdate = simpleDateFormat.parse(startDate);
		Date enddate = simpleDateFormat.parse(endDate);
		Session session = null;
		try{ 
			session = sessionFactory.getCurrentSession();
			  Long impCount = (Long) session.createCriteria(ImportProduct.class)
					.add(Restrictions.between("impDate", startdate ,enddate ))
					.setProjection(Projections.rowCount()).uniqueResult(); 
			  Long expCount = (Long) session.createCriteria(Expense.class)
					.add(Restrictions.between("exp_date", startdate ,enddate ))
					.setProjection(Projections.rowCount()).uniqueResult();
			return (impCount + expCount);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0L;
	}
	
	@Override
	@Transactional
	public Long countDaily(Date dateTime) {
		Session session = null; 
			session = sessionFactory.getCurrentSession();
			return  (Long) session.createCriteria(ImportProduct.class)
					.add(Restrictions.between("impDate", dateTime, dateTime ))
					.setProjection(Projections.rowCount()).uniqueResult();   
	}
	@Override
	@Transactional
	public Long count() {
		Session session = null; 
			session = sessionFactory.getCurrentSession();
			return  (Long) session.createCriteria(ImportProduct.class).setProjection(Projections.rowCount()).uniqueResult();   
	}
	@Override
	
	
	public List<Map> getReportListAllBeverageStock(Date dateForm, Pagination pagination, boolean ispagination) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.getTransaction().begin();
			String sql = "SELECT  "
	+ " A.pro_id as pro_id, "
	+ " A.pro_name as pro_name, "
	+ " A.unit_id as unit_id, "
	+ " A.qty as balance,  "
	+ " (COALESCE((SELECT sum(impde.pro_qty*un.qty) as pro_qty "
	+ " 			FROM import_detail impde  "
	+ " 			INNER JOIN import imp ON impde.imp_id = imp.imp_id  " 
	+ " 			INNER JOIN unit un on un.unit_id = A.unit_id "
	+ " 			WHERE to_char(imp.imp_date,'yyyy-MM-dd')= '" + simpleDateFormat.format(dateForm) + "' AND impde.pro_id = A.pro_id "
	+ " 			),'0')) as pro_qty, "
+ " (COALESCE((select "
    + "             sum(od.pro_qty) as pro_qty  "
    + "        from "
    + "            order_detail od  "
    + "         Inner join "
    + "             wecafe_order wo  "
    + "                 on od.order_id = wo.order_id  "
    + "         where "
    + "             wo.status =2 and A.pro_id=od.pro_id and  to_char(wo.order_date,'yyyy-MM-dd') = '" + simpleDateFormat.format(dateForm) 
	+ "' 				),'0')) as sale, " 
+ " (A.qty + (COALESCE((select "
    + "             sum(od.pro_qty) as pro_qty "
    + "         from "
    + "            order_detail od  "
    + "         Inner join "
    + "             wecafe_order wo  "
    + "                 on od.order_id = wo.order_id  "
    + "         where "
    + "             wo.status =2 and A.pro_id=od.pro_id and  to_char(wo.order_date,'yyyy-MM-dd') = '" + simpleDateFormat.format(dateForm) 
	+ "' 				),'0'))- COALESCE((SELECT sum(impde.pro_qty*un.qty) as pro_qty "
	+ " 			FROM import_detail impde  "
	+ " 			INNER JOIN import imp ON impde.imp_id = imp.imp_id   "
	+ " 			INNER JOIN unit un on un.unit_id = A.unit_id "
	+ " 			WHERE to_char(imp.imp_date,'yyyy-MM-dd') = '" + simpleDateFormat.format(dateForm) +  "' AND impde.pro_id = A.pro_id "
	+ " 			),'0')) as carried_over "
+ " from product A order by A.pro_name";
			
			Query query = session.createSQLQuery(sql);
			
			if(ispagination){ 
				query.setFirstResult(pagination.offset());
				query.setMaxResults(pagination.getPerPage());
			}
			//query.setString(0, dateForm.getStartdate());
			//query.setString(1, dateForm.getEnddate());
		System.out.println("getStartdate = " + simpleDateFormat.format(dateForm));
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		
			List<Map> result = (ArrayList<Map>)query.list();
			for (Object object : result) {
				System.out.println(object);
			}
			
			return result;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			session.getTransaction().rollback();
		}finally {
			session.close();
		}
		return null;
	}

	@Override
	public List<Object[]> getReportListAllCupStockbyDate(DateForm dateForm) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.getTransaction().begin();
			String sql = "select c.cat_name,p.pro_id,aa.imp_date,p.pro_name, p.qty as carried_over, id.pro_qty as purhase,ta.qty as takeut,(p.qty  - ta.qty) as InStock from product p"
					+ " left join import_detail id on p.pro_id = id.pro_id"
					+ " left join import aa on id.imp_id = aa.imp_id"
					+ " left join  takeout ta on p.pro_id = ta.pro_id"
					+ " inner join category c on p.cat_id = c.cat_id"
					+ " where aa.imp_date BETWEEN '2015-11-30' and '2015-12-03 23:59'"
					+ " GROUP BY p.pro_name, p.qty, id.pro_qty ,   p.pro_id, aa.imp_date, ta.qty,c.cat_name";
			Query query = session.createSQLQuery(sql);
			List<Object[]> result = query.list();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			session.close();
		}
		return null;
	} 
	@Transactional
	@Override
	public List<Map<String, Object>> getListReportYearlyPurcase(Pagination pagination,Date startDate, Date endDate,boolean isPagination) {
		// TODO Auto-generated method stub
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
			sbSelect.append("COALESCE("+months[i]+"[1],0) AS "+months[i].toUpperCase()+"_QTY ,");
			sbSelect.append("COALESCE("+months[i]+"[2],0) AS "+months[i].toUpperCase()+"_AMOUNT ,");
			sb.append(months[i] + " INTEGER[],");
			
		} 
		try {
			session = sessionFactory.getCurrentSession();
			SQLQuery query = 
					session.createSQLQuery("SELECT " +
										   "mthreport.row_name [ 1 ] AS customer, " +
										   "mthreport.row_name [ 2 ] AS pro_name, " + 
										   "COALESCE(jan[1],0) AS JAN_QTY, " +
										   "COALESCE(jan[2],0) AS JAN_AMOUNT, " +
										   "COALESCE(feb[1],0) AS FEB_QTY, " +
										   "COALESCE(feb[2],0) AS FEB_AMOUNT, " +
										   "COALESCE(mar[1],0) AS MAR_QTY, " +
										   "COALESCE(mar[2],0) AS MAR_AMOUNT, " +
										   "COALESCE(apr[1],0) AS APR_QTY, " +
										   "COALESCE(apr[2],0) AS APR_AMOUNT, " +
										   "COALESCE(may[1],0) AS MAY_QTY," +
										   "COALESCE(may[2],0) AS MAY_AMOUNT," +
										   "COALESCE(jun[1],0) AS JUN_QTY," +
										   "COALESCE(jun[2],0) AS JUN_AMOUNT," +
										   "COALESCE(jul[1],0) AS JUL_QTY," +
										   "COALESCE(jul[2],0) AS JUL_AMOUNT," +
										   "COALESCE(aug[1],0) AS AUG_QTY," +
										   "COALESCE(aug[2],0) AS AUG_AMOUNT, " +
										   "COALESCE(sep[1],0) AS SEP_QTY, " +
										   "COALESCE(sep[2],0) AS SEP_AMOUNT, " +
										   "COALESCE(oct[1],0) AS OCT_QTY, " +
										   "COALESCE(oct[2],0) AS OCT_AMOUNT, " +
										   "COALESCE(nov[1],0) AS NOV_QTY, " +
										   "COALESCE(nov[2],0) AS NOV_AMOUNT, " +
										   "COALESCE(DEC[1],0) AS DEC_QTY, " +
										   "COALESCE(DEC[2],0) AS DEC_AMOUNT " +  
										   "FROM " +
										   "	crosstab ( " +
										   "	'SELECT ARRAY[sup.sup_name::text, P.pro_name::text] As row_name " +
										   "		   ,to_char(imp.imp_date, ''mon'')::text As imp_date 	 " +
										   "		   ,ARRAY[SUM(impde.pro_qty), SUM(impde.unit_price*impde.pro_qty)] AS row  " +
										   "	 FROM product P INNER JOIN import_detail impde on impde.pro_id = P.pro_id " +
										   "	 INNER JOIN import imp on imp.imp_id = impde.imp_id" +
										   "	 INNER JOIN supplier sup on sup.sup_id = impde.sup_id "
										   + "	 WHERE to_char(imp.imp_date ,''yyyy-MM-dd'') BETWEEN ''"+sdf.format(startDate)+"'' AND ''"+sdf.format(endDate)+"'' " +
										   "	 GROUP BY 1,2 " +
										   " UNION ALL "+
										   " SELECT ARRAY[B.customer::text, B.expense_description::text] As row_name "+
										   " ,to_char(A.expense_date, ''mon'')::text As imp_date "+
										   " ,ARRAY[SUM(B.expense_qty), SUM(B.expense_unitprice*B.expense_qty)] AS row "+
										   " FROM tbl_expense A INNER JOIN tbl_expense_detail B ON A.expense_id = B.expense_id "+
										   " LEFT JOIN users C ON A.exp_user_id = C.id "+
										   " WHERE to_char(A.expense_date ,''yyyy-MM-dd'') BETWEEN ''"+sdf.format(startDate)+"'' AND ''"+sdf.format(endDate)+"'' "+
										   " GROUP BY 1,2 "+
										   "	 ORDER BY 1', " +
										   "'SELECT to_char(date ''"+sdf.format(startDate)+"'' + (n || '' month'')::interval, ''mon'') As short_mname " +   
										   " FROM generate_series(0,11) n' " +
										   ") AS mthreport ( " +
										   "row_name TEXT [], " + sb.toString().substring(0, sb.toString().lastIndexOf(",")) + ")" 
										   ); 	
			if(isPagination){
				query.setFirstResult(pagination.offset());
				query.setMaxResults(pagination.getPerPage());
			}
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
	public Map<String, Object> getAllPurchaseMonthlyReportsTotal(Date startDate, Date endDate) {
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
	public List<Map> getListReportDetailPurchase() {
		// TODO Auto-generated method stub
		return null;
	} 

	@Override
	@Transactional
	public List<Map> getListReportDailyPurchaseRest(Pagination pagination,Date startdate, boolean isPagination) {
		String startDate = new SimpleDateFormat("yyyy-MM-dd").format(startdate);
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			session.getTransaction().begin();
			SQLQuery query = session.createSQLQuery(
					"SELECT A.imp_id AS purchase_id," 
					+ " A.imp_date AS purchase_date , " 
					+ " CONCAT(C.lastname, ' ',C.firstname) AS purchase_by ," 
					+ " D.sup_name As supplier_name,"
					+ " E.pro_name As product_name,"
					+ " B.pro_qty As product_qty,"
					+ " B.unit_price As pro_unit_price,"
					+ " SUM(B.pro_qty * B.unit_price) AS purchase_total_amount, '0' AS purchase_type "
					+ " FROM import A INNER JOIN import_detail B ON A.imp_id = B.imp_id LEFT JOIN users C ON C.id = A.user_id LEFT JOIN supplier D ON D.sup_id = B.sup_id "
					+ " 						LEFT JOIN product E on E.pro_id = B.pro_id"
					+ " WHERE to_char(A.imp_date,'YYYY-mm-dd') = '" + startDate
					+ "' GROUP BY 1,2,3,4,5,6,7 "
					+ " UNION ALL  	"
					+ " SELECT 	A.expense_id AS purchase_id  , "
					+ " A.expense_date AS purchase_date  ,"
					+ " CONCAT(C.lastname, ' ', C.firstname) AS purchase_by,"
					+ " B.customer As supplier_name,"
					+ " B.expense_description As product_name,"
					+ " B.expense_qty As product_qty,"
					+ " B.expense_unitprice as pro_unit_price,"
					+ " SUM(B.expense_qty * B.expense_unitprice) AS purhcase_total_amount, "
					+ " '1' AS purchase_type"
					+ " FROM tbl_expense A "
					+ " 	INNER JOIN tbl_expense_detail B ON A.expense_id = B.expense_id LEFT JOIN users C ON A.exp_user_id = C.id "
					+ " 				WHERE to_char(A.expense_date,'YYYY-mm-dd') = '" + startDate
					+ "' GROUP BY 1,2,3,4,5,6,7 "
					+ " ORDER BY 9");
			if(isPagination){
				query.setFirstResult(pagination.offset());
				query.setMaxResults(pagination.getPerPage());
			}
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map>	importProducts = (List<Map>)query.list();	
			System.out.println("Records Count = "   + importProducts.size());
			return importProducts;
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			
		}
		
		return null;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getListReportWeeklyPurchaseRest(Pagination pagination,Date startdate, Date enddate, boolean isPagination) {		
		Session session = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar calStartDate = Calendar.getInstance();
		calStartDate.setTime(startdate); 
		Calendar calEndDate = Calendar.getInstance();
		calEndDate.setTime(enddate); 
		Calendar calendar = calStartDate;
		String[] months = new String[]{"day1","day2", "day3", "day4", "day5", "day6", "day7"};
		StringBuilder sb = new StringBuilder();
		StringBuilder sbSelect = new StringBuilder();  
	for(int i=0 ; i<7 ; i++){ 
		sbSelect.append("COALESCE("+months[i]+"[1],0) AS "+months[i].toUpperCase()+"_QTY ,");
		sbSelect.append("COALESCE("+months[i]+"[2],0) AS "+months[i].toUpperCase()+"_AMOUNT ,");
		sb.append(months[i] + " INTEGER[],");  
	} 
	try {
		session = sessionFactory.getCurrentSession();
		SQLQuery query = 
				session.createSQLQuery("SELECT " +
									   "mthreport.row_name [ 1 ] AS customer, " +
									   "mthreport.row_name [ 2 ] AS pro_name, " + 
									   "COALESCE(day1[1],0) AS day1_QTY, " +
									   "COALESCE(day1[2],0) AS day1_AMOUNT, " +
									   "COALESCE(day2[1],0) AS day2_QTY, " +
									   "COALESCE(day2[2],0) AS day2_AMOUNT, " +
									   "COALESCE(day3[1],0) AS day3_QTY, " +
									   "COALESCE(day3[2],0) AS day3_AMOUNT, " +
									   "COALESCE(day4[1],0) AS day4_QTY, " +
									   "COALESCE(day4[2],0) AS day4_AMOUNT, " +
									   "COALESCE(day5[1],0) AS day5_QTY," +
									   "COALESCE(day5[2],0) AS day5_AMOUNT," +
									   "COALESCE(day6[1],0) AS day6_QTY," +
									   "COALESCE(day6[2],0) AS day6_AMOUNT," +
									   "COALESCE(day7[1],0) AS day7_QTY," +
									   "COALESCE(day7[2],0) AS day7_AMOUNT" +									    
									   " FROM " +
									   "	crosstab ( " +
									   "	'SELECT ARRAY[sup.sup_name::text, P.pro_name::text] As row_name " +
									   "		   ,to_char(imp.imp_date, ''DD'')::text As imp_date " +
									   "		   ,ARRAY[SUM(impde.pro_qty), SUM(impde.unit_price*impde.pro_qty)] AS row " +
									   "	 FROM product P INNER JOIN import_detail impde on impde.pro_id = P.pro_id " +
									   "	 INNER JOIN import imp on imp.imp_id = impde.imp_id " +
									   "	 INNER JOIN supplier sup on sup.sup_id = impde.sup_id " +   
									   "	 WHERE to_char(imp.imp_date ,''yyyy-MM-dd'') BETWEEN ''"+sdf.format(startdate)+"'' And ''"+sdf.format(enddate)+"''" +
									   "	 GROUP BY 1,2 " +
									   " UNION ALL " +
									   " SELECT ARRAY[B.customer::text, B.expense_description::text] As row_name " +
									   " ,to_char(A.expense_date, ''DD'')::text As imp_date " +
									   " ,ARRAY[SUM(B.expense_qty), SUM(B.expense_unitprice*B.expense_qty)] AS row " +
									   " FROM tbl_expense A INNER JOIN tbl_expense_detail B ON A.expense_id = B.expense_id " +
									   " LEFT JOIN users C ON A.exp_user_id = C.id " +
									   " WHERE to_char(A.expense_date,''yyyy-MM-dd'') BETWEEN ''"+sdf.format(startdate)+"'' And ''"+sdf.format(enddate)+"''" +
									   " GROUP BY 1,2 " +
									   "	 ORDER BY 1', " +
									   "'SELECT to_char(date ''"+sdf.format(startdate)+"'' + (n || '' day'')::interval, ''DD'') As short_mname " +									   
									   " FROM generate_series(0,6) n;' " +
									   ") AS mthreport ( " +
									   "row_name TEXT [], " + sb.toString().substring(0, sb.toString().lastIndexOf(",")) + ")" 
									   ); 	
		if(isPagination){
			query.setFirstResult(pagination.offset());
			query.setMaxResults(pagination.getPerPage());
		}
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		List<Map<String, Object>> sales= (List<Map<String, Object>>)query.list();
		return sales;
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println(e.getMessage());
	}
	return null;
	}

	@Override
	public List<Map> getListReportYearlyPurchaseRest(int byYear) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Object getListReportMonthlyPurchaseRest(Pagination pagination,Date startdate, Date enddate, boolean isPagination) {
		Session session = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar calStartDate = Calendar.getInstance();
		calStartDate.setTime(startdate);
		
		Calendar calEndDate = Calendar.getInstance();
		calEndDate.setTime(enddate);
		System.out.println("Enddate = " + calEndDate.get(Calendar.DAY_OF_MONTH));
		System.out.println("start Date = " + calStartDate.get(Calendar.DAY_OF_MONTH));
		
		String[] months = new String[calEndDate.get(Calendar.DAY_OF_MONTH)];
		for(int i=0;i<calEndDate.get(Calendar.DAY_OF_MONTH);i++)
		{
			months[i] = "day" + (i+1);
		}
		StringBuilder sb = new StringBuilder();
		StringBuilder sbSelect = new StringBuilder();
		
		
		for(int i=0 ; i<calEndDate.get(Calendar.DAY_OF_MONTH) ; i++){
			//calendar.add(Calendar.DATE, i);
			 
			//System.out.println("Month ==== " + months[i]);
			 
			sbSelect.append("COALESCE("+months[i]+"[1],0) AS "+months[i].toUpperCase()+"_QTY ,");
			sbSelect.append("COALESCE("+months[i]+"[2],0) AS "+months[i].toUpperCase()+"_AMOUNT ,");
			sb.append(months[i] + " INTEGER[],"); 
			
		}
		System.out.println(sb.toString().substring(0, sb.toString().lastIndexOf(",")));
		
		System.out.println(calStartDate.getTime());
		System.out.println(calEndDate.getTime());
		  
		try {
			session = sessionFactory.getCurrentSession();
			SQLQuery query = 
					session.createSQLQuery("SELECT " +
										   "mthreport.row_name [ 1 ] AS customer, " +
										   "mthreport.row_name [ 2 ] AS pro_name, " + sbSelect.toString().substring(0, sbSelect.toString().lastIndexOf(",")) +									    
										   " FROM " +
										   "	crosstab ( " +
										   "	'SELECT ARRAY[sup.sup_name::text, P.pro_name::text] As row_name " +
										   "		   ,to_char(imp.imp_date, ''DD'')::text As imp_date " +
										   "		   ,ARRAY[SUM(impde.pro_qty), SUM(impde.unit_price*impde.pro_qty)] AS row " +
										   "	 FROM product P INNER JOIN import_detail impde on impde.pro_id = P.pro_id " +
										   "	 INNER JOIN import imp on imp.imp_id = impde.imp_id " +
										   "	 INNER JOIN supplier sup on sup.sup_id = impde.sup_id " +   
										   "	 WHERE to_char(imp.imp_date ,''yyyy-MM-dd'') BETWEEN ''"+sdf.format(startdate)+"'' And ''"+sdf.format(enddate)+"''" +
										   "	 GROUP BY 1,2 " +
										   " UNION ALL " +
										   " SELECT ARRAY[B.customer::text, B.expense_description::text] As row_name " +
										   " ,to_char(A.expense_date, ''DD'')::text As imp_date " +
										   " ,ARRAY[SUM(B.expense_qty), SUM(B.expense_unitprice*B.expense_qty)] AS row " +
										   " FROM tbl_expense A INNER JOIN tbl_expense_detail B ON A.expense_id = B.expense_id " +
										   " LEFT JOIN users C ON A.exp_user_id = C.id " +
										   " WHERE to_char(A.expense_date,''yyyy-MM-dd'') BETWEEN ''"+sdf.format(startdate)+"'' And ''"+sdf.format(enddate)+"''" +
										   " GROUP BY 1,2 " +
										   "	 ORDER BY 1', " +
										   "'SELECT to_char(date ''"+sdf.format(startdate)+"'' + (n || '' day'')::interval, ''DD'') As short_mname " +									   
										   " FROM generate_series(0,"+ (calEndDate.get(Calendar.DAY_OF_MONTH) -1) +") n;' " +
										   ") AS mthreport ( " +
										   "row_name TEXT [], " + sb.toString().substring(0, sb.toString().lastIndexOf(",")) + ")" 
										   ); 	
			if(isPagination){
				query.setFirstResult(pagination.offset());
				query.setMaxResults(pagination.getPerPage());
			}
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map<String, Object>> sales = (List<Map<String, Object>>)query.list(); 
			return sales;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

 
	}
