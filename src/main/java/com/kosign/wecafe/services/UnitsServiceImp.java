package com.kosign.wecafe.services;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.entities.Unit;

@Service
public class UnitsServiceImp implements UnitService {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public List<Unit> getAllUnits() {
		Session session = sessionFactory.getCurrentSession();
		try {
			
			Query query = session.createQuery("FROM Unit");
			List<Unit> units = query.list();
			return units;
			
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage());
			
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Unit> getAllUnits(Pagination pagination) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Unit.class);  
			criteria.addOrder( Order.desc("unitId") );
			criteria.setFirstResult(pagination.offset());
			criteria.setMaxResults(pagination.getPerPage());			
			List<Unit>	unitlist = (List<Unit>)criteria.list();	
			return unitlist;
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;   
	}
	
	@Override
	@Transactional
	public Boolean addNewUnit(Unit unit) {
		try{
			sessionFactory.getCurrentSession().save(unit);
			return true;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public Unit findUnit(Long unitid) {
		try{
			return sessionFactory.getCurrentSession().get(Unit.class, unitid);
		}catch(Exception ex){
			System.out.println("Error at FIND UNIT");
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public Unit findUnitByID(Long unitid) {
		try{
			return sessionFactory.getCurrentSession().get(Unit.class, unitid);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public Boolean updateUnit(Unit unit) {
		try{			 
			sessionFactory.getCurrentSession().update(unit);
			return true;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public Long count() { 
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			return (Long) session.createCriteria(Unit.class).setProjection(Projections.rowCount()).uniqueResult();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0L;
	}

	@Override
	@Transactional
	public List<Unit> searchUnits(String str) {
		Session session = sessionFactory.getCurrentSession();
		try { 
			Query query = session.createQuery("FROM Unit where lower(unitName) like lower('" + str + "%')");
			List<Unit> units = query.list();
			return units;
			
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage()); 
		}
		return null;
	}

}
