package com.kosign.wecafe.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.kosign.wecafe.entities.Category;
import com.kosign.wecafe.entities.ImportDetail;
import com.kosign.wecafe.entities.ImportProduct;
import com.kosign.wecafe.entities.Order;
import com.kosign.wecafe.entities.OrderDetail;
import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.entities.Sale;
import com.kosign.wecafe.entities.Supplier;
import com.kosign.wecafe.entities.Unit;
import com.kosign.wecafe.entities.User;
import com.kosign.wecafe.entities.UserRole;
import com.kosign.wecafe.entities.importDetailPK;

public class HibernateUtil {
	
	private static final SessionFactory sessionFactory = buildSessionFactory();
	
	// hibernate.cfg.xml
/*	private static SessionFactory buildSessionFactory(){
		try{
			Configuration configuration = new Configuration();
			return configuration
					.buildSessionFactory(new StandardServiceRegistryBuilder()
							.applySettings(configuration.getProperties())
							.build());
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException("There was an error building the factory.");
		}
	}*/
	
	// hibernate.properties
	private static SessionFactory buildSessionFactory(){
		try{
			Configuration configuration = new Configuration();
			configuration.addAnnotatedClass(Product.class);
			configuration.addAnnotatedClass(Category.class);
			configuration.addAnnotatedClass(Supplier.class);
			configuration.addAnnotatedClass(Order.class);
			configuration.addAnnotatedClass(OrderDetail.class);
			configuration.addAnnotatedClass(Sale.class);
			configuration.addAnnotatedClass(User.class);
			configuration.addAnnotatedClass(UserRole.class);
			configuration.addAnnotatedClass(ImportDetail.class);
			configuration.addAnnotatedClass(ImportProduct.class);
			configuration.addAnnotatedClass(importDetailPK.class);
			configuration.addAnnotatedClass(Unit.class);
					
			
			//configuration.addPackage("com.kosign.wecafe.entities");
			return configuration.buildSessionFactory(new StandardServiceRegistryBuilder().build());
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException("There was an error building the factory.");
		}
	}
	
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}
