package com.kosign.wecafe.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosign.wecafe.entities.ImportProduct;
import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.entities.Unit;
import com.kosign.wecafe.entities.User;
import com.kosign.wecafe.entities.UserRole;
import com.kosign.wecafe.enums.Status;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public User findUserById(Long id) {
		try{
			return sessionFactory.getCurrentSession().get(User.class, id);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public User findUserByEmail(String email) {
		try{
			return  (User) sessionFactory.getCurrentSession().createCriteria(User.class)
								.add(Restrictions.eq("email", email))
								.uniqueResult();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	@Transactional
	public User findUserByUsername(String username) {
		try{
			return  (User) sessionFactory.getCurrentSession().createCriteria(User.class)
								.add(Restrictions.eq("username", username))
								.uniqueResult();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	@Transactional
	public Boolean saveUser(User user) {
		try{
			//sessionFactory.getCurrentSession().merge(user.getUserRoles());
			//user.setCreatedBy(this.findUserByUsername(getPrincipal())); 
			//System.out.println("create by = " + this.findUserByUsername(getPrincipal()));
			//user.setCreatedDate(new Date());
			//user.setLastUpdatedBy(this.findUserByUsername(getPrincipal()));
			//user.setLastUpdatedDate(new Date());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			//UserRole roles = sessionFactory.getCurrentSession().get(UserRole.class, user.getUserRoles().iterator().next().getId());
			//UserRole rolesNew = (UserRole) sessionFactory.getCurrentSession().merge(roles);
			sessionFactory.getCurrentSession().saveOrUpdate(user);
			return true;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return false;
	}
	
	@Override
	@Transactional
	public Boolean updateUser(User userUpdate) {
		Session session=null;
		try{
			session=sessionFactory.getCurrentSession();
			User user = (User)session.get(User.class, userUpdate.getId()); 
			Long id = user.getUserRoles().iterator().next().getId();
			//System.out.println("userRole id= " + id);
			//UserRole userRole = (UserRole)session.get(UserRole.class, id);
			//HashSet<UserRole> userRoles = new HashSet<UserRole>();
			//userRoles.add(userRole);
			//user.setLastUpdatedBy(this.findUserByUsername(getPrincipal()));
			//System.out.println("update by = " + this.findUserByUsername(getPrincipal()));
			user.setLastUpdatedDate(new Date());
			user.setEmail(userUpdate.getEmail());
			user.setFirstName(userUpdate.getFirstName());
			user.setLastName(userUpdate.getLastName());
			user.setStatus(userUpdate.getStatus());
			user.setUsername(userUpdate.getUsername());
			user.setGender(userUpdate.getGender());  
			if(id != userUpdate.getUserRoles().iterator().next().getId())
				user.setUserRoles(userUpdate.getUserRoles());
			/*sessionFactory.getCurrentSession().flush();
			sessionFactory.getCurrentSession().clear();*/
			//user.setUserRoles(userUpdate.getUserRoles()); 
			session.saveOrUpdate(user);
			return true;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return false;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<User> getAllUser(Pagination pagination){
		
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(User.class ); 
			criteria.addOrder( Order.asc("username") );
			criteria.setFirstResult(pagination.offset());
			criteria.setMaxResults(pagination.getPerPage());			
			List<User>	userlist = (List<User>)criteria.list();	
			return userlist;
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<User> getAllUsers(){
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			return (List<User>)session.createCriteria(User.class).list();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	@Transactional
	public Long count(){
		 Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			return (Long) session.createCriteria(User.class).setProjection(Projections.rowCount()).uniqueResult();
		}catch(Exception ex){
			ex.printStackTrace();
		} 
		return 0L; 
	}
	
	@Override
	@Transactional
	public List<UserRole> getAllUserRoles(){
		try{
			return (List<UserRole>)sessionFactory.getCurrentSession().createCriteria(UserRole.class).list();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	@Transactional
	public Boolean updateUserStatus(Long id){
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			User user = session.load(User.class, id);
			if(user.getStatus().equals(Status.ACTIVE)){
				user.setStatus(Status.INACTIVE);
			}else if(user.getStatus().equals(Status.INACTIVE)){
				user.setStatus(Status.ACTIVE);
			}else {
				user.setStatus(Status.ACTIVE);
			}
			user.setLastUpdatedDate(new Date());
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	@Override
	@Transactional
	public Boolean deleteUser(Long id){
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			User user = session.load(User.class, id);
			user.setStatus(Status.DELETED);
			user.setLastUpdatedDate(new Date());
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
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
	public List<User> searchUser(String str) {
		Session session = sessionFactory.getCurrentSession();
		try { 
			Query query = session.createQuery("FROM User where lower(username) like lower('" + str + "%')");
			List<User> users = query.list();
			return users;
			
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println(e.getMessage()); 
		}
		return null;
	}
}
