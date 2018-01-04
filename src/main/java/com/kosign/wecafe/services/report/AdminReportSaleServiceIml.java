package com.kosign.wecafe.services.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Sale;
import com.kosign.wecafe.util.HibernateUtil;

@Service
public class AdminReportSaleServiceIml implements AdminReportSaleService {
	@Autowired
	private SessionFactory sessionFactory; 
	
	@Override
	@Transactional
	public List<Map> getListReportDetailSaleRest(Pagination pagination, int byyear) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			session.getTransaction().begin();
			SQLQuery query = session.createSQLQuery(
					"SELECT A.sale_id "
				 + "   , B.username "
				 + "	   , to_char(A.sale_datetime, 'YYYY-mm-dd') as sale_date "
				 + "	   , A.total_amount from sale A "
				 + "	    INNER JOIN users B on A.user_id = B.id "
				 + "	where EXTRACT(YEAR FROM A.sale_datetime) = " + byyear + " order by A.sale_id DESC");
			query.setFirstResult(pagination.offset());
			query.setMaxResults(pagination.getPerPage());
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map>	saleProducts = (List<Map>)query.list();	
			return saleProducts;
		}catch(Exception e){
			e.printStackTrace();
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
			return  (Long) session.createCriteria(Sale.class)
					.add(Restrictions.between("saleDatetime", startdate ,enddate ))
					.setProjection(Projections.rowCount()).uniqueResult(); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0L;
	}

	@Override
	@Transactional
	public Long count(){
		Session session = null;
		try{ 
			session = sessionFactory.getCurrentSession();
			return  (Long) session.createCriteria(Sale.class).setProjection(Projections.rowCount()).uniqueResult(); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0L;
	}
	
	@Override
	@Transactional
	public List<Map> getListReportDailySaleRest(Pagination pagination, Date startdate, boolean ispagination) {
		String startDate = new SimpleDateFormat("yyyy-MM-dd").format(startdate);
		System.out.println("startdate = "+startdate);
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			session.getTransaction().begin();
			SQLQuery query = session.createSQLQuery("SELECT B.pro_name, " 
							+ " to_char(C.sale_datetime,'YYYY-mm-dd')  as saleDate, "
							+ " SUM(A.pro_qty) as product_qty, "
							+ " A.pro_unit_price, "
							+ " sum(A.pro_qty*A.pro_unit_price) as total_amount, "
							+ " D.status "
							+ " from sale C INNER JOIN wecafe_order D on C.ord_id = D.order_id "
							+ " 		LEFT JOIN order_detail A ON C.ord_id = A.order_id "
							+ " 		LEFT JOIN product B on B.pro_id = A.pro_id "
							+ " WHERE D.status = 2 and to_char(C.sale_datetime,'YYYY-mm-dd') = '" + startDate
							+ "' GROUP BY 1,2,4,6");
			if(ispagination){
			query.setFirstResult(pagination.offset());
			query.setMaxResults(pagination.getPerPage());
			}
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map>	saleProducts = (List<Map>)query.list();	
			return saleProducts;
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			
		} 
		
		return null;
	}

	@Override
	@Transactional
	public Object getListReportWeeklySaleRest(Pagination pagination,Date startdate, Date enddate, boolean isPagination) {	
	Session session = null;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	System.out.println("beforFomat = " + startdate);
	Calendar calStartDate = Calendar.getInstance();
	calStartDate.setTime(startdate);
	
	Calendar calEndDate = Calendar.getInstance();
	calEndDate.setTime(enddate);
	
	Calendar calendar = calStartDate;
	
	System.out.println("afterFomat = " + sdf.format(startdate));
	
	String[] months = new String[]{"day1","day2", "day3", "day4", "day5", "day6", "day7"};
	StringBuilder sb = new StringBuilder();
	StringBuilder sbSelect = new StringBuilder();
	
	
	for(int i=0 ; i<7 ; i++){
		sbSelect.append("COALESCE("+months[i]+"[1],0) AS "+months[i].toUpperCase()+"_QTY ,");
		sbSelect.append("COALESCE("+months[i]+"[2],0) AS "+months[i].toUpperCase()+"_AMOUNT ,");
		sb.append(months[i] + " NUMERIC[],"); 
		
	}	
	try {
		session = sessionFactory.getCurrentSession();
		SQLQuery query = 
				session.createSQLQuery("SELECT"
        + " mthreport.row_name AS pro_name, "
        + " COALESCE(day1[1], 0) AS day1_QTY, "
        + " COALESCE(day1[2], 0) AS day1_AMOUNT, "
        + " COALESCE(day2[1], 0) AS day2_QTY, "
        + " COALESCE(day2[2], 0) AS day2_AMOUNT, "
        + " COALESCE(day3[1], 0) AS day3_QTY, "
        + " COALESCE(day3[2], 0) AS day3_AMOUNT, "
        + " COALESCE(day4[1], 0) AS day4_QTY, "
        + " COALESCE(day4[2], 0) AS day4_AMOUNT, "
        + " COALESCE(day5[1], 0) AS day5_QTY, "
        + " COALESCE(day5[2], 0) AS day5_AMOUNT, "
        + " COALESCE(day6[1], 0) AS day6_QTY, "
        + " COALESCE(day6[2], 0) AS day6_AMOUNT, "
        + " COALESCE(day7[1], 0) AS day7_QTY, "
        + " COALESCE(day7[2], 0) AS day7_AMOUNT " 
    + " FROM crosstab ('SELECT  P.pro_name::text As row_name " 		   
    + " ,to_char(ord.order_date, ''DD'')::text As sale_date 	 "	   
    + " ,ARRAY[SUM(od.pro_qty) "
    + " ,sum(od.pro_qty* od.pro_unit_price)] AS row " 	 
    + " from product P INNER JOIN order_detail od ON od.pro_id = P.pro_id "
		+ "INNER JOIN wecafe_order ord ON ord.order_id = od.order_id "
		+ "INNER JOIN sale sa ON sa.ord_id = ord.order_id  " 
+ "WHERE ord.status = 2 and to_char(sa.sale_datetime,''YYYY-mm-dd'') BETWEEN ''"+sdf.format(startdate)+"'' And ''"+sdf.format(enddate)+"''" 	 
+ "GROUP BY 1,2   "
+ "ORDER BY 1', "
+ "        'SELECT to_char(date ''"+sdf.format(startdate)+"'' + (n || '' day'')::interval, ''DD'') As short_mname  FROM generate_series(0,6) n;' ) AS mthreport " 
+ "( row_name TEXT , " + sb.toString().substring(0, sb.toString().lastIndexOf(",")) + ")"); 	
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
	@Transactional
	public Object getListReportMonthlySaleRest(Pagination pagination, Date startdate, Date enddate, boolean isPagination ) {

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
			sbSelect.append("COALESCE("+months[i]+"[1],0) AS "+months[i].toUpperCase()+"_QTY ,");
			sbSelect.append("COALESCE("+months[i]+"[2],0) AS "+months[i].toUpperCase()+"_AMOUNT ,");
			sb.append(months[i] + " NUMERIC[],"); 
			
		}
		System.out.println(sb.toString().substring(0, sb.toString().lastIndexOf(",")));
		
		System.out.println(calStartDate.getTime());
		System.out.println(calEndDate.getTime());
		  
		try {
			session = sessionFactory.getCurrentSession();
			SQLQuery query = 
					session.createSQLQuery("SELECT mthreport.row_name  AS pro_name, " 
							+ sbSelect.toString().substring(0, sbSelect.toString().lastIndexOf(",")) 
							+ " FROM crosstab ( " 
							+ " 'SELECT P.pro_name::text As row_name " 		   
							+ " ,to_char(ord.order_date, ''DD'')::text As sale_date 	 "	   
							+ " ,ARRAY[SUM(od.pro_qty) "
							+ " ,sum(od.pro_qty* od.pro_unit_price)] AS row " 	 
						    + " from product P  INNER JOIN order_detail od ON od.pro_id = P.pro_id "
							+ "INNER JOIN wecafe_order ord ON ord.order_id = od.order_id "
							+ "INNER JOIN sale sa ON sa.ord_id = ord.order_id " 
							+ "WHERE ord.status = 2 and to_char(sa.sale_datetime,''YYYY-mm-dd'') BETWEEN ''"+sdf.format(startdate)+"'' And ''"+sdf.format(enddate)+"''" 	 
							+ "GROUP BY 1,2   "
							+ "ORDER BY 1', " +
						   "'SELECT to_char(date ''"+sdf.format(startdate)+"'' + (n || '' day'')::interval, ''DD'') As short_mname " +									   
						   " FROM generate_series(0,"+ (calEndDate.get(Calendar.DAY_OF_MONTH) -1) +") n;' " +
						   ") AS mthreport ( " +
						   "row_name TEXT, " + sb.toString().substring(0, sb.toString().lastIndexOf(",")) + ")" 
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

	@Override
	@Transactional
	public Object getListReportYearlySale(Pagination pagination, Date startDate, Date endDate,boolean isPagination) {

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
			System.out.println(months[i]);
			sbSelect.append("COALESCE("+months[i]+"[1],0) AS "+months[i].toUpperCase()+"_QTY ,");
			sbSelect.append("COALESCE("+months[i]+"[2],0) AS "+months[i].toUpperCase()+"_AMOUNT ,");
			sb.append(months[i] + " NUMERIC[],");
			
		}
		System.out.println(sb.toString().substring(0, sb.toString().lastIndexOf(",")));
		
		System.out.println(calStartDate.getTime());
		System.out.println(calEndDate.getTime());
		try {
			session = sessionFactory.getCurrentSession();
			SQLQuery query = 
					session.createSQLQuery("SELECT " +										   
										   "mthreport.row_name AS pro_name, " + 
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
										   "	crosstab ( " 
										   	+ " 'SELECT P.pro_name::text As row_name " 		   
											+ " ,to_char(ord.order_date, ''mon'')::text As sale_date 	 "	   
											+ " ,ARRAY[SUM(od.pro_qty) "
											+ " ,sum(od.pro_qty* od.pro_unit_price)] AS row " 	 
										    + " from product P  INNER JOIN order_detail od ON od.pro_id = P.pro_id   "
											+ "INNER JOIN wecafe_order ord ON ord.order_id = od.order_id "
											+ "INNER JOIN sale sa ON sa.ord_id = ord.order_id " 
											+ "WHERE ord.status = 2 and to_char(sa.sale_datetime,''YYYY-mm-dd'') BETWEEN ''"+sdf.format(startDate)+"'' And ''"+sdf.format(endDate)+"''" 	 
											+ "GROUP BY 1,2   "
											+ "ORDER BY 1', " +
										   "'SELECT to_char(date ''"+sdf.format(startDate)+"'' + (n || '' month'')::interval, ''mon'') As short_mname " +										   
										   " FROM generate_series(0,11) n' " +
										   ") AS mthreport ( " +
										   "row_name TEXT , " + sb.toString().substring(0, sb.toString().lastIndexOf(",")) + ")" 
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
	@Transactional
	public Map<String, Object> getAllSaleMonthlyReportsTotal(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public List<Map> listAllsaleDetail(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.getTransaction().begin();
			Query query = session.createSQLQuery("SELECT A.total_amount, "
					+ "B.pro_qty, "
					+ "B.pro_unit_price, "
					+ "C.pro_name, B.pro_qty*B.pro_unit_price as amount "
					+ "FROM sale A INNER JOIN order_detail B on A.ord_id = B.order_id "
					+ "LEFT JOIN product C ON B.pro_id = C.pro_id "
					+ "WHERE A.sale_id = " + id);
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map> saleDetails = query.list();
			return saleDetails;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.getStackTrace();
			session.getTransaction().rollback();
		}finally {
			session.close();
		}
		return null;
	}

	@Override
	@Transactional
	public Long countDaily(Date dateTime) throws HibernateException, ParseException {  
	 	String startDate = new SimpleDateFormat("yyyy-MM-dd").format(dateTime); 
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			session.getTransaction().begin();
			SQLQuery query = session.createSQLQuery("SELECT count(DISTINCT A.pro_id) as count "  
							+ " from order_detail A INNER JOIN wecafe_order B on A.order_id = B.order_id "
							+ " 		LEFT JOIN sale C on C.ord_id = B.order_id "
							+ " 		WHERE to_char(C.sale_datetime,'yyyy-MM-dd') = '" + startDate + "'");  
			query.addScalar("count", LongType.INSTANCE);
			System.out.println("totalCount = " + query.uniqueResult());
			return (Long) query.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			
		}
		return null;
	}
/*	@Override
	@Transactional
	public Long countWeekly(Date startDate,Date endDate) {
		Session session = null; 
			session = sessionFactory.getCurrentSession();
			return  (Long) session.createCriteria(Sale.class)
					.add(Restrictions.between("saleDatetime", startDate, endDate))
					.setProjection(Projections.rowCount()).uniqueResult();   
	}*/

	@Override
	@Transactional
	public Long getAllTotalAmount(Date startdate, Date enddate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(startdate);
		String endDate = sdf.format(enddate);
		System.out.println("StartDate = " + startDate);
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			session.getTransaction().begin();
			SQLQuery query = session.createSQLQuery("SELECT "
							+ " sum(A.pro_qty*A.pro_unit_price) as total_amount " 
							+ " from sale C INNER JOIN wecafe_order D on C.ord_id = D.order_id "
							+ " 		LEFT JOIN order_detail A ON C.ord_id = A.order_id " 
							+ " WHERE D.status = 2 and to_char(C.sale_datetime,'YYYY-mm-dd') between '" + startDate +"' and '" +  endDate +"'");  
			query.addScalar("total_amount", LongType.INSTANCE); 	
			return  (Long) (query.uniqueResult());
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			
		}		
		return null;
	}

	 

}
