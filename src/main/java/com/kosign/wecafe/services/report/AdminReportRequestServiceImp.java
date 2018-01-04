package com.kosign.wecafe.services.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.Query;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.entities.RequestStock;
import com.kosign.wecafe.entities.RequestStockDetail;
import com.kosign.wecafe.forms.DateForm;

@Service
public class AdminReportRequestServiceImp implements AdminReportRequestService  {
	@Autowired
	private SessionFactory sessionFactory;
	
	/*---------------------------------List record------------------------------/
	
	/**
	 * Get all detail request 
	 */
	
	@Override
	@Transactional
	public List<Map> getListReportDetailRequest(Pagination pagination,DateForm date) {
		Session session = null;
		String sql="SELECT "
						+"DISTINCT "
						+"rs.req_id, "
						+ "app_date, "
						+"to_char(rs.req_date, 'DD/MM/YYYY HH24:MI:SS') req_date, "
						+"(SELECT firstname ||' '|| lastname As request_by FROM users WHERE id=rs.use_id ), "
						+"SUM(rsd.pro_qty) as total_request_qty "
				   +"FROM "       
						+"request_stock rs,  "
						+"request_stock_detail  rsd, "
						+"users use, "
						+"product pro "      
				   +"WHERE 1=1 " 
						+"and rs.req_id=rsd.req_id "      
						+"and rs.use_id=use.id "     
						+"and rsd.pro_id=pro.pro_id "  
						+"and rs.status='f' "
						+"and  EXTRACT(YEAR FROM rs.app_date)=? "				
						+"GROUP BY "
						+"rs.req_id "
				   + "ORDER BY app_date DESC "	;					
						
		try{
			session = sessionFactory.getCurrentSession();		
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			query.setParameter(0, Integer.parseInt(date.getYear()));
			query.setFirstResult(pagination.offset());
			query.setMaxResults(pagination.getPerPage());
			List<Map>	requestReport = (List<Map>)query.list();	
			return requestReport;
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;
	}
	
	/**
	 * Get all daily request 
	 */
	
	
	@Override
	@Transactional
	public List<Map> getListReportDailyRequest(Pagination pagination, DateForm date) {
		Session session = null;	
						
		try{
			session = sessionFactory.getCurrentSession();		
			SQLQuery query = session.createSQLQuery(getQueryDailyRequest(date));
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			query.setParameter(0, date.getDay());
			query.setFirstResult(pagination.offset());
			query.setMaxResults(pagination.getPerPage());
			List<Map>	requestReport = (List<Map>)query.list();	
			return requestReport;
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;
	}
	
	/**
	 * Get all weekly request 
	 */

	@Override
	@Transactional
	public List<Map> getListReportWeeklyRequest(Pagination pagination,DateForm date,boolean ispagination) {
		Session session = null;
		
		String[] days = new String[]{"day1","day2", "day3", "day4", "day5", "day6", "day7"};
		StringBuilder sb = new StringBuilder();
		StringBuilder sbSelect = new StringBuilder();		
		
		for(int i=0 ; i<7 ; i++){
			sbSelect.append("COALESCE("+days[i]+") AS "+days[i].toUpperCase()+"_QTY ,");
			sb.append(days[i] + " NUMERIC,"); 
			
		}	
		
		String startDate=date.getStartdate();
	
		try {
			session = sessionFactory.getCurrentSession();
			SQLQuery query = 
					session.createSQLQuery("SELECT "
									+"ct.product_name,	"							 
									+"COALESCE(day1) AS day1_QTY, " 
									 +"COALESCE(day2) AS day2_QTY, "
									 +"COALESCE(day3) AS day3_QTY, " 
									 +"COALESCE(day4) AS day4_QTY, " 
									 +"COALESCE(day5) AS day5_QTY, " 
									 +"COALESCE(day6) AS day6_QTY, "
									 +"COALESCE(day7) AS day7_QTY "
								+ "FROM crosstab( "
								+"'SELECT " 
									+"pro.pro_name::text , "
									+"to_char(rs.app_date,''DD'')::TEXT app_date ," 
									+"SUM(rsd.pro_qty) "
								+"FROM request_stock rs," 
									+"request_stock_detail rsd,product pro " 
								+"WHERE " 
									+"rs.req_id=rsd.req_id " 
									+"and rsd.pro_id=pro.pro_id " 
									+"and rs.status=''f'' "
									+"and to_char(rs.app_date,''YYYY-mm-dd'') " 
									+"BETWEEN ''"+startDate+"'' And ''"+date.getEnddate()+"'' "
									+"GROUP BY 1,2 ORDER BY 1 ', "
								+"'SELECT to_char(date ''"+startDate+"'' + (n || '' day'')::interval, ''DD'') As short_mname "  
								+"FROM "
									+"generate_series(0,6) n;' "
									+") as ct(product_name text ," + sb.toString().substring(0, sb.toString().lastIndexOf(",")) + ") "); 
			if(ispagination){
			query.setFirstResult(pagination.offset());
			query.setMaxResults(pagination.getPerPage());
			}
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			
			
			List<Map> sales= (List<Map>)query.list();
			return sales;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Get all monthly request 
	 */
	
	
	@Override
	@Transactional
	public List<Map> getListReportMonthlyRequest(Date startdate, Date enddate,Pagination pagination, boolean ispagination) {
	
		Session session = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar calStartDate = Calendar.getInstance();
		calStartDate.setTime(startdate);
		
		Calendar calEndDate = Calendar.getInstance();
		calEndDate.setTime(enddate);
		
		String[] months = new String[calEndDate.get(Calendar.DAY_OF_MONTH)];
		for(int i=0;i<calEndDate.get(Calendar.DAY_OF_MONTH);i++)
		{
			months[i] = "day" + (i+1);
		}
		StringBuilder sb = new StringBuilder();
		StringBuilder sbSelect = new StringBuilder();
		
		
		for(int i=0 ; i<calEndDate.get(Calendar.DAY_OF_MONTH) ; i++){
			 
			sbSelect.append("COALESCE("+months[i]+") AS "+months[i].toUpperCase()+"_QTY ,");
			sb.append(months[i] + " INTEGER,"); 
			
		}
		  
		try {
			session = sessionFactory.getCurrentSession();
			SQLQuery query = 
					session.createSQLQuery("SELECT " 
											   +"mthreport.row_name AS pro_name, " 
											   +""+sbSelect.toString().substring(0, sbSelect.toString().lastIndexOf(","))+" "								    
											   +"FROM " 
											   +"crosstab ( " 
										   +"'SELECT "
												+"pro.pro_name::text ,"
												+" to_char(rs.app_date,''DD'')::TEXT app_date ,"
												+"SUM(rsd.pro_qty) "
												+"FROM request_stock rs,request_stock_detail rsd,product pro "
											+"WHERE  rs.req_id=rsd.req_id and rsd.pro_id=pro.pro_id and rs.status=''f'' "
												+"and to_char(rs.app_date,''YYYY-mm-dd'') BETWEEN ''"+sdf.format(startdate)+"'' And ''"+sdf.format(enddate)+"'' " 
												+"GROUP BY 1,2 ORDER BY 1 ', "
										    +"'SELECT "
										    	+ "to_char(date ''"+sdf.format(startdate)+"'' + (n || '' day'')::interval, ''DD'') As short_mname " 								   
										   +"FROM "
										   		+ "generate_series(0,"+ (calEndDate.get(Calendar.DAY_OF_MONTH) -1) +") n;' " 
										   +") AS mthreport ( "
										   		+"row_name TEXT, " + sb.toString().substring(0, sb.toString().lastIndexOf(",")) + ")" 
										   ); 	
			if(ispagination){
			query.setFirstResult(pagination.offset());
			query.setMaxResults(pagination.getPerPage());}
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
					
			List<Map> sales= (List<Map>)query.list();
			return sales;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Get all yearly request 
	 */

	@Transactional
	@Override
	public List<Map<String, Object>> getListReportYearlyRequest(Pagination pagination,Date startDate, Date endDate, boolean ispagination ) {
		// TODO Auto-generated method stub
		Session session = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
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
			sbSelect.append("COALESCE("+months[i]+") AS "+months[i].toUpperCase()+"_QTY ,");
			sb.append(months[i] + " INTEGER,");
			
		} 
		try {
			session = sessionFactory.getCurrentSession();
			SQLQuery query = 
					session.createSQLQuery("SELECT " +					
										   "mthreport.row_name AS pro_name, " + 
										   "COALESCE(jan) AS JAN_QTY, " +								
										   "COALESCE(feb) AS FEB_QTY, " +										  
										   "COALESCE(mar) AS MAR_QTY, " +										   
										   "COALESCE(apr) AS APR_QTY, " +										  
										   "COALESCE(may) AS MAY_QTY," +										  
										   "COALESCE(jun) AS JUN_QTY," +										 
										   "COALESCE(jul) AS JUL_QTY," +										 
										   "COALESCE(aug) AS AUG_QTY," +										  
										   "COALESCE(sep) AS SEP_QTY, " +										 
										   "COALESCE(oct) AS OCT_QTY, " +										  
										   "COALESCE(nov) AS NOV_QTY, " +										
										   "COALESCE(DEC) AS DEC_QTY " +										 
										   "FROM " +
										   "	crosstab ( " 
												   +"'SELECT "
														+"pro.pro_name::text ,"
														+"to_char(rs.app_date,''mon'')::TEXT app_date ,"
														+"SUM(rsd.pro_qty) "
													+"FROM request_stock rs,request_stock_detail rsd,product pro "
													+"WHERE  rs.req_id=rsd.req_id and rsd.pro_id=pro.pro_id and rs.status=''f'' "
													+"and to_char(rs.app_date,''YYYY-mm-dd'') BETWEEN ''"+sdf.format(startDate)+"'' And ''"+sdf.format(endDate)+"'' " 
													+"GROUP BY 1,2 ORDER BY 1 ', "
													
												   +"'SELECT to_char(date ''"+sdf.format(startDate)+"'' + (n || '' month'')::interval, ''mon'') As short_mname "   
												   +" FROM generate_series(0,11) n' " 
												   +") AS mthreport ( " 
												   +"row_name TEXT, " + sb.toString().substring(0, sb.toString().lastIndexOf(",")) + ")" 
												); 	
			if(ispagination){
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
	
	
	
	/*----------------------------------------Get Total Qty Request------------------------------/
	
	/**
	 * Get total qty for detail  request 
	 */
	
	@Override
	@Transactional
	public Long getTotalQtyDetailRequest(DateForm date){
		
		int year =0;
		year=Integer.parseInt(date.getYear());
		Session session = sessionFactory.getCurrentSession();
		try {

			org.hibernate.Query query= session.createQuery("Select Sum(rsd.proQty) "
															+ "From "
																+ "RequestStockDetail rsd "
															+ "Where "
																+ "rsd.pk1.requestStock.status=? "
																+ "And EXTRACT(YEAR FROM rsd.pk1.requestStock.appDate)=? " 
															);
			query.setParameter(0, false);
			query.setParameter(1, year);
			return (Long) query.uniqueResult() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}
	
	/**
	 * Get total qty for daily  request 
	 */
	
	@Override
	@Transactional
	public List<Map> getTotalProQty(DateForm date) {
		Session session = null;	
						
		try{
			String sql="SELECT "
								+"rsd.pro_id,"
							    +"sum(pro_qty) total_pro_qty,"
							    +"count(rsd.pro_id) total_row "
						    +"FROM "
						        +"request_stock rs, "
						        +"request_stock_detail  rsd,"
						        +"users use,"
						        +"product pro " 
						    +"WHERE "
						        +"1=1 " 
						        +"and rs.req_id=rsd.req_id " 
						        +"and rs.use_id=use.id "
						        +"and rsd.pro_id=pro.pro_id " 
						        +"and rs.status='f' "
						        +"and to_char(rs.app_date,'yyyy-mm-dd')=? "  
								+"GROUP BY "
								+"rsd.pro_id,"
								+"pro_name  "
							+"ORDER BY "
								+"pro_name ASC"	;
			session = sessionFactory.getCurrentSession();		
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			query.setParameter(0, date.getDay());
			List<Map>	requestReport = (List<Map>)query.list();	
			return requestReport;
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;
	}
	
	
	
	/**
	 * Get total qty for weekly  request 
	 */
	@Override
	@Transactional
	public Long getTotalQtyWeeklyRequest(DateForm date){
		
		Session session = sessionFactory.getCurrentSession();
		try {

			org.hibernate.Query query= session.createQuery("Select Sum(rsd.proQty) "
															+ "From "
																+ "RequestStockDetail rsd "
															+ "Where "
																+ "rsd.pk1.requestStock.status=? "
																+ "And to_char(rsd.pk1.requestStock.appDate,'YYYY-mm-dd') " 
																+"BETWEEN ?  And ? ");
			query.setParameter(0, false);
			query.setParameter(1, date.getStartdate());
			query.setParameter(2, date.getEnddate());
			return (Long) query.uniqueResult() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}
	
	
	/**
	 * Get total qty for monthly  request 
	 */
	
	@Override
	@Transactional
	public Long getTotalQtyMonthlyRequest(Date startDate,Date endDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Session session =null;
		try {
			session=sessionFactory.getCurrentSession();
			org.hibernate.Query query= session.createQuery("Select Sum(rsd.proQty) "
															+ "From "
																+ "RequestStockDetail rsd "
															+ "Where "
																+ "rsd.pk1.requestStock.status=? "
																+ "And to_char(rsd.pk1.requestStock.appDate,'YYYY-mm-dd') " 
																+"BETWEEN '"+sdf.format(startDate)+"'  And '"+sdf.format(endDate)+"' ");
			query.setParameter(0, false);
			//query.setParameter(1, sdf.format(startDate));
			//query.setParameter(2, sdf.format(endDate));
			return (Long) query.uniqueResult() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}
	
	
	/**
	 * Get total qty for yearly  request 
	 */
	
	@Override
	@Transactional
	public Long getTotalQtyYearlyRequest(Date startDate,Date endDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Session session =null;
		try {
			session=sessionFactory.getCurrentSession();
			org.hibernate.Query query= session.createQuery("Select Sum(rsd.proQty) "
															+ "From "
																+ "RequestStockDetail rsd "
															+ "Where "
																+ "rsd.pk1.requestStock.status=? "
																+ "And to_char(rsd.pk1.requestStock.appDate,'YYYY-mm-dd') " 
																+"BETWEEN '"+sdf.format(startDate)+"'  And '"+sdf.format(endDate)+"' ");
			query.setParameter(0, false);
			//query.setParameter(1, sdf.format(startDate));
			//query.setParameter(2, sdf.format(endDate));
			return (Long) query.uniqueResult() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}
	
	/*----------------------------------------Get Total record------------------------------/
	
		/**
	 * Get total record for detail  request 
	 */

	@Override
	@Transactional
	public Long countDetail(DateForm date) throws ParseException {
		String year=date.getYear();
		String startDate = year +"-01-01";
		String endDate = year + "-12-31";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startdate = simpleDateFormat.parse(startDate);
		Date enddate = simpleDateFormat.parse(endDate);
		Session session = null;
		try{ 
			session = sessionFactory.getCurrentSession();
			return  (Long) session.createCriteria(RequestStock.class)
					.add(Restrictions.eq("status",false))
					.add(Restrictions.and(Restrictions.between("appDate", startdate ,enddate )))					
					.setProjection(Projections.rowCount()).uniqueResult(); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0L;
	}
	
	
	/**
	 * Get total record for daily  request 
	 */
	
	
	@Override
	@Transactional
	public Long getTotalDailyRequest(DateForm date) {
		// TODO Auto-generated method stub
		Session session = null;
		Long cnt = 0L;
		try {

			try {
				session=sessionFactory.getCurrentSession();
				String sql = "SELECT count(*) cnt FROM(" + getQueryDailyRequest(date) + ") cnt ";
				SQLQuery query = session.createSQLQuery(sql);
				query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
				query.setParameter(0,date.getDay() );
				HashMap<String, Object> result = (HashMap<String, Object>) query.uniqueResult();
				cnt = Long.parseLong(result.get("cnt").toString());
			} catch (HibernateException e) {
				System.out.println(" error total remord");				
				e.printStackTrace();
			}
			return cnt;
		} catch (HibernateException e) {
			throw new RuntimeException("Could not begin transaction");
		}
	}
	
	public String getQueryDailyRequest(DateForm date){
		String sql="";
		 sql="SELECT DISTINCT " 
				+"pro.pro_name req_pro, "
				+"rsd.pro_qty req_qty,"
				+"(SELECT firstname ||' '|| lastname As approve_by FROM users WHERE id=rs.app_id ),"
				+"(SELECT firstname ||' '|| lastname As request_by FROM users WHERE id=rs.use_id ),"
				+"to_char(rs.req_date, 'DD/MM/YYYY') req_date,"
		        +"to_char(rs.app_date, 'DD/MM/YYYY') app_date ,"
		        + "(SELECT SUM(rsd1.pro_qty) total_req_qty from request_stock_detail rsd1, request_stock rs1 WHERE rs1.req_id=rsd1.req_id and rs1.status='f' and to_char(rs1.app_date,'yyyy-mm-dd')='"+date.getDay()+"') "
		  +"FROM "
			    +"request_stock rs,"
			    +"request_stock_detail  rsd,"
			    +"users use,"
			    +"product pro  "
		  +"WHERE "
			    +"1=1  "
			    +"and rs.req_id=rsd.req_id  "
			    +"and rs.use_id=use.id  "
			    +"and rsd.pro_id=pro.pro_id  "
			    +"and rs.status='f' " 
			    +"and to_char(rs.app_date,'yyyy-mm-dd')=? "  
	      +"GROUP BY "
		        +"pro_name, "
				+"rsd.pro_qty, "
				+"rs.app_id,"
				+"rs.use_id,"
				+"rs.req_date,"
				+"rs.app_date "
		   +"ORDER BY "
		        +"pro_name ASC";	
		 return sql;
	}
	
	
	/**
	 * Get total record for weekly  request 
	 */
	
	@Override
	@Transactional
	public Long countWeekly(DateForm date) throws ParseException {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startdate = simpleDateFormat.parse(date.getStartdate());
		Date enddate = simpleDateFormat.parse(date.getEnddate());
		Session session = null;
		try{ 
			session = sessionFactory.getCurrentSession();
			return  (Long) session.createCriteria(RequestStock.class)
					.add(Restrictions.eq("status",false))
					.add(Restrictions.and(Restrictions.between("appDate", startdate ,enddate)))					
					.setProjection(Projections.rowCount()).uniqueResult(); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0L;
	}
	
	
	/**
	 * Get total record for monthly  request 
	 */
	
	@Override
	@Transactional
	public Long countMonthly(Date startDate,Date endDate) throws ParseException {
		Session session = null;
		try{ 
			session = sessionFactory.getCurrentSession();
			return  (Long) session.createCriteria(RequestStock.class)
					.add(Restrictions.eq("status",false))
					.add(Restrictions.and(Restrictions.between("appDate", startDate ,endDate)))					
					.setProjection(Projections.rowCount()).uniqueResult(); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0L;
	}

   
	/**
	 * Get total record for yearly  request 
	 */
	
	@Override
	@Transactional
	public Long countYearly(Date startDate,Date endDate) throws HibernateException, ParseException {
		Session session = null;
		try{ 
			session = sessionFactory.getCurrentSession();
			return  (Long) session.createCriteria(RequestStock.class)
					.add(Restrictions.eq("status",false))
					.add(Restrictions.and(Restrictions.between("appDate", startDate ,endDate)))					
					.setProjection(Projections.rowCount()).uniqueResult(); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0L;
	}


	
}