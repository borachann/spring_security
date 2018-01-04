package com.kosign.wecafe.services;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Slide;
import com.kosign.wecafe.entities.User;

@Service
public class SlideServiceImpl implements SlideService{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserService userService;
	
	@Override
	@Transactional
	public List<Slide> getAllSlidesPagination(Pagination pagination, boolean ispagination) {
		 
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Slide.class ); 
			criteria.add(Restrictions.eq("status", true));
			if(ispagination){
			criteria.setFirstResult(pagination.offset());
			criteria.setMaxResults(pagination.getPerPage());}			
			List<Slide>	slidelist = (List<Slide>)criteria.list();	
			return slidelist;
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;
	}
	@Override
	@Transactional
	public List<Slide> getAllSlides() { 
		try{
			return sessionFactory.getCurrentSession().createCriteria(Slide.class)
													 .add(Restrictions.eq("status", true))
													 .list(); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	@Override
	@Transactional
	public Boolean addNewSlide(Slide slide) {
		try{
			sessionFactory.getCurrentSession().save(slide);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public Boolean updateSlide(Slide slide) {
		try{
			Slide updateSlide = sessionFactory.getCurrentSession().get(Slide.class, slide.getId());
			updateSlide.setName(slide.getName());
			updateSlide.setImage(slide.getImage());
			updateSlide.setLastUpdatedBy(userService.findUserByUsername(getPrincipal()));
			updateSlide.setLastUpdatedDate(new Date());
			sessionFactory.getCurrentSession().update(updateSlide);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public Boolean deleteSlide(Integer id) {
		try{
			Slide slide = sessionFactory.getCurrentSession().get(Slide.class, id);
			slide.setLastUpdatedBy(userService.findUserByUsername(getPrincipal()));
			slide.setLastUpdatedDate(new Date());
			slide.setStatus(false);
			sessionFactory.getCurrentSession().update(slide);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public Slide findSlideById(Integer id) {
		try{
			return sessionFactory.getCurrentSession().get(Slide.class, id);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
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

/*	@Override
	@Transactional
	public Long count() { 
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Slide.class ); 
			criteria.add(Restrictions.eq("status", true)); 			
			Long	slidelist = (Long) criteria.uniqueResult();	
			return slidelist;
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;
	}*/
	

}
