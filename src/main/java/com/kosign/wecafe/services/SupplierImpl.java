package com.kosign.wecafe.services;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Supplier;
import com.kosign.wecafe.entities.User;
import com.kosign.wecafe.util.HibernateUtil;

@Service
public class SupplierImpl implements SupplierService{
	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public Boolean saveSupplier(Supplier supplier) {
		Session session = null;
		try {
			session =  HibernateUtil.getSessionFactory().openSession();
			session.getTransaction().begin();
			
			session.save(supplier);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
			//HibernateUtil.getSessionFactory().close();
		}
		
		return false;
	}

	@Override
	public List<Supplier> getAllSuppliers(Pagination pagination) { 
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.getTransaction().begin();
			Query query = session.createQuery("FROM Supplier");
			query.setFirstResult(pagination.offset());
			query.setMaxResults(pagination.getPerPage());
			List<Supplier> supplier = (List<Supplier>)query.list(); 
			return supplier; 
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			
		}finally {
			session.close();
		}
		return null; 
	}

	@Override
	public List<Supplier> getAllSupplier() { 
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.getTransaction().begin();
			Query query = session.createQuery("FROM Supplier");
			List<Supplier> supplier = (List<Supplier>)query.list(); 
			return supplier; 
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			
		}finally {
			session.close();
		}
		return null; 
	}
	
	@Override
	public Boolean updateSupplier(Supplier supplier) { 
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.getTransaction().begin();
//			Supplier suppliers = session.get(Supplier.class, supplier.getSupId());
			session.update(supplier);

			session.beginTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			session.close();
		}
		
		return false;
	}

	@Override
	public Supplier findSupplierById(long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.getTransaction().begin();
			Supplier supplier = session.get(Supplier.class, id);
			System.out.println(supplier.getSupplierName());
			session.getTransaction().commit();
			return supplier;
			
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			session.getTransaction().rollback();
		}finally {
			session.close();
		}
		return null;
	}

	@Override
	public Boolean deleteSupplier(Supplier supplier) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.getTransaction().begin();
			Supplier suppliers = session.get(Supplier.class, supplier.getSupId());
			//add field status
			session.delete(suppliers);
			session.beginTransaction().commit();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		return true;
	}

	@Override
	@Transactional
	public Long count() {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			return (Long) session.createCriteria(Supplier.class).setProjection(Projections.rowCount()).uniqueResult();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0L; 
	}

	@Override
	@Transactional
	public List<Supplier> searchSupplier(String str) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.getTransaction().begin();
			Query query = session.createQuery("FROM Supplier where lower(supplierName) like lower('" + str + "%')"); 
			List<Supplier> supplier = (List<Supplier>)query.list(); 
			return supplier; 
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			
		}finally {
			session.close();
		}
		return null; 
	}
}
