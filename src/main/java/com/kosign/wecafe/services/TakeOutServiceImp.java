/*package com.kosign.wecafe.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosign.wecafe.entities.ImportDetail;
import com.kosign.wecafe.entities.ImportProduct;
import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.entities.Supplier;
import com.kosign.wecafe.entities.User;
import com.kosign.wecafe.forms.ImportForm;
import com.kosign.wecafe.util.HibernateUtil;

@Service
public class TakeOutServiceImp implements TakeOutService {

	@Inject TakeOutService takeOutService;

	@Override
	public List<ImportProduct> listAllTakeOut() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> listAllProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean saveTakeOut(List<ImportForm> importForm) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction().begin();
//			
//			for(int i=0; i < importform.size();i++){
//				
//			
//			}
//			
//			session.save(object);
			session.beginTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.beginTransaction().rollback();
		}finally {
			session.close();
		}
		
		return null;
	}

	@Override
	public Boolean updateTakeOut(List<ImportForm> importform, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteTakeOut(List<ImportForm> importform, Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	 
}
*/