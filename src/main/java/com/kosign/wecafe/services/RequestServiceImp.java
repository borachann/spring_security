package com.kosign.wecafe.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosign.wecafe.entities.ImportProduct;
import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.entities.RequestStock;
import com.kosign.wecafe.entities.RequestStockDetail;
import com.kosign.wecafe.entities.Supplier;
import com.kosign.wecafe.entities.User;
import com.kosign.wecafe.forms.RequestForm;

@Service
public class RequestServiceImp implements RequestService {

	@Inject
	UserService userService;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Transactional
	public Boolean updateReqQty(RequestForm req) {
		Session session = sessionFactory.getCurrentSession();
		try {

			Query query = session
					.createQuery("Update RequestStockDetail rsd Set rsd.proQty =?,rsd.remainQty=? Where rsd.pk1.requestStock.reqId=? and rsd.pk1.product.productId=?");
			query.setParameter(0,req.getProQty());
			query.setParameter(1,req.getRemainQty());
			query.setParameter(2,req.getReqId());
			query.setParameter(3,req.getProId());
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public Boolean approveRequest(List<RequestForm> requestForm) {

		Session session = sessionFactory.getCurrentSession();
		try {

			for (int i = 0; i < requestForm.size(); i++) {
				// 1. save import product
				RequestStock requestStock = session.get(RequestStock.class, requestForm.get(i).getReqId());
				requestStock.setStatus(false);
				requestStock.setAppDate(new Date());
				User user = userService.findUserByUsername(getPrincipal());
				requestStock.setUserApprove(user);

				RequestStockDetail requestDetail = new RequestStockDetail();
				Product products = session.get(Product.class, requestForm.get(i).getProId());
				products.setQuantity(products.getQuantity() - requestForm.get(i).getProQty());

				session.update(products);
				session.update(requestStock);
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public Long count(String id) {
		Session session = null;
		long rowCount = 0;
		try {
			String sql = "SELECT count(rsd.req_id) as cnt FROM request_stock rs, request_stock_detail rsd "
					+ "WHERE rs.req_id=rsd.req_id and rs.status='t' " + "and CAST(rs.req_id as TEXT) LIKE ?";
			session = sessionFactory.getCurrentSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			query.setParameter(0, "%" + id + "%");
			HashMap<String, Object> result = (HashMap<String, Object>) query.uniqueResult();
			rowCount = Long.parseLong(result.get("cnt").toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rowCount;
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
	public Boolean saveRequestPro(List<RequestForm> requestForm) {

		Session session = sessionFactory.getCurrentSession();
		try {
			// 1. save import product
			RequestStock requestStock = new RequestStock();
			requestStock.setStatus(true);
			requestStock.setReqDate(new Date());
			User user = userService.findUserByUsername(getPrincipal());
			requestStock.setUserRequest(user);

			// 2.save import detail

			for (int i = 0; i < requestForm.size(); i++) {
				RequestStockDetail requestDetail = new RequestStockDetail();
				Product product = new Product();
				product.setProductId(requestForm.get(i).getProId());
				requestDetail.setProduct(product);
				requestDetail.setRequestStock(requestStock);
				requestDetail.setProQty(requestForm.get(i).getProQty());
				requestDetail.setRemainQty(requestForm.get(i).getRemainQty());
				requestStock.getRequestStockDetail().add(requestDetail);

			}
			session.save(requestStock);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public List<Map> listRequestDetail(String id, Pagination pagination,Boolean isTrue) {
		Session session = sessionFactory.getCurrentSession();
		try {
		String sql = "SELECT "
						+ "rs.req_id,"
						+ "pro.pro_id,"
						+ "pro.pro_name,"
						+ "rsd.pro_qty,"
						+ "rsd.remain_qty,"
						+ "firstname,"
						+ "lastname,"
						+ "pro.qty stock_qty ,"
						+ "to_char(rs.req_date, 'DD/MM/YYYY HH24:MI:SS') req_date,"
						+ "to_char(rs.app_date, 'DD/MM/YYYY HH24:MI:SS') app_date,"
						+ "(SELECT firstname ||' '|| lastname As req_by FROM users WHERE id=rs.use_id ),"
						+ "(SELECT firstname ||' '|| lastname As app_by FROM users WHERE id=rs.app_id )"
					+ "FROM " 
						+ "request_stock rs, request_stock_detail  rsd,users use,product pro "
						+ "WHERE 1=1 "
						+ "and rs.req_id=rsd.req_id "
						+ "and rs.use_id=use.id " 
						+ "and rsd.pro_id=pro.pro_id "
						+ ""+ checkStatus(isTrue)+""
						+ "and CAST(rs.req_id as TEXT) LIKE ? "				
					+ " Order By rs.req_id DESC";
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			query.setParameter(0, "%" + id + "%");
			query.setFirstResult(pagination.offset());
			query.setMaxResults(pagination.getPerPage());
			List<Map> requestDetails = query.list();
			return requestDetails;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	
	public String checkStatus(Boolean isTrue){
		String status="";
		return status=(isTrue==true)?" and rs.status='t' ":" and rs.status='f' ";
	
	}

	@Override
	@Transactional
	public List<Map> listRequestStock() {

		Session session = sessionFactory.getCurrentSession();
		try {
			String sql = "Select req_id From request_stock Where status='t' Order By req_id DESC";
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<Map> requestDetails = query.list();
			return requestDetails;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
