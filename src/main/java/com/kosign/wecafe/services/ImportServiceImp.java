package com.kosign.wecafe.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosign.wecafe.entities.ImportDetail;
import com.kosign.wecafe.entities.ImportProduct;
import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.entities.Supplier;
import com.kosign.wecafe.entities.User;
import com.kosign.wecafe.forms.ImportForm;

@Service
public class ImportServiceImp implements ImportService {

	@Inject UserService userService;
	
	@Autowired SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ImportProduct> listAllImportProduct(Pagination pagination, Date startDate, Date endDate, boolean ispagination) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(ImportProduct.class );
			criteria.add(Restrictions.between("impDate", startDate, endDate));
			criteria.addOrder( Order.desc("impId") );
			if(ispagination){ 
				criteria.setFirstResult(pagination.offset());
				criteria.setMaxResults(pagination.getPerPage());
			}			
			List<ImportProduct>	importProducts = (List<ImportProduct>)criteria.list();	
			return importProducts;
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;
	}
	
	@Override
	@Transactional
	public Boolean saveImportPro(List<ImportForm> importform) { 
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			//1. save import product 
			ImportProduct importProduct = new ImportProduct();
			importProduct.setImpDate(new Date());
			User user = userService.findUserByUsername(getPrincipal());
			importProduct.setUser(user);
		
			//session.save(importProduct);

			//2.save import detail
			
			for(int i=0; i < importform.size();i++){
				ImportDetail importdetail = new ImportDetail();
				Product product = new Product();
				product.setProductId(importform.get(i).getProId());
			
				importdetail.setProduct(product);
				importdetail.setImportProduct(importProduct);
				importdetail.setProQty(importform.get(i).getQuantity());
				
				importdetail.setProStatus(true);
				Supplier suppliers = new Supplier();
				suppliers.setSupId(importform.get(i).getSupplierId());
				importdetail.setSupplier(suppliers);
				
				importdetail.setSupplierid(importform.get(i).getSupplierId());
				importdetail.setUnitPrice(importform.get(i).getUnitPrice());
				
				importProduct.getImportDetail().add(importdetail);
				//session.save(importdetail);
				
				
				//3. update product (stock)
				Product products  = session.get(Product.class, importdetail.getProduct().getProductId());
				System.out.println("importdetail.getProduct().getProductId()" + products.getQuantity());
				products.setQuantity(products.getQuantity() + (importform.get(i).getQuantity()* products.getUnit().getQty() ));
				products.setUnitPrice(importform.get(i).getUnitPrice());
				/*products.setCostPrice(importform.get(i).getUnitPrice());
				products.setUnit(unit);
				products.setSalePrice(salePrice);*/
				session.update(products);
			}
			
			session.save(importProduct);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Product> listAllProduct() {		
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery("FROM Product where status='t'");
			 
			List<Product> products = query.list(); 
			 return products; 
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Supplier> listAllSupplier() {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			Query query = session.createQuery("FROM Supplier");
			 List<Supplier> suppliers= query.list();
			 return suppliers; 
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
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

	@Override
	@Transactional
	public Boolean updateImportPro(List<ImportForm> importForms, Long id) {
	    try {
	    	//select importprodut by id
			ImportProduct importProduct = sessionFactory.getCurrentSession().get(ImportProduct.class, id);
			Set<ImportDetail> importDetails = importProduct.getImportDetail();		
			System.out.println("IMPORT DETAILS SIZE=" + importDetails.size());		
				for(ImportDetail importDetail : importDetails){
				//select product by id
				Product product = sessionFactory.getCurrentSession().get(Product.class, importDetail.getProduct().getProductId());
				//update qty in product table 
				product.setQuantity(product.getQuantity() - importDetail.getProQty());
				product.setLastUpdatedDate(new Date());
				product.setLastUpdatedBy(userService.findUserByUsername(getPrincipal()));				
				sessionFactory.getCurrentSession().update(product);			
			}			
			importProduct.getImportDetail().clear();					
			
			sessionFactory.getCurrentSession().update(importProduct);
		
			for(ImportForm importForm : importForms){
				ImportDetail importDetail = new ImportDetail(); 
				Product product = sessionFactory.getCurrentSession().get(Product.class, importForm.getProId());
				
				importDetail.setImportProduct(importProduct);
				importDetail.setProduct(product);
				importDetail.setSupplier(sessionFactory.getCurrentSession().get(Supplier.class, importForm.getSupplierId()));
				importDetail.setUnitPrice(importForm.getUnitPrice());
				importDetail.setProQty(importForm.getQuantity());
				importDetail.setProStatus(true);				
				
				product.setUnitPrice(importForm.getUnitPrice());
				product.setQuantity(product.getQuantity()+importForm.getQuantity());
				
				sessionFactory.getCurrentSession().flush();
				sessionFactory.getCurrentSession().clear();
				sessionFactory.getCurrentSession().update(product);
			
				importProduct.getImportDetail().add(importDetail); 
				 
			}
			
			sessionFactory.getCurrentSession().update(importProduct);
			
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.getStackTrace();
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional
	public List<Map> findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		try {			
			Query query = session.createQuery("Select new Map("
					+ "p.productName as proname,"
					+ "impdetail.proQty as proqty,"
					+ "impdetail.unitPrice as prounitprice,"
					+ "impdetail.supplier.supplierName as supname)"
					+ " FROM ImportDetail impdetail" 
					+ " INNER JOIN impdetail.pk1.product  p"
					+ " WHERE impdetail.pk1.importProduct.impId= ? ");			
			query.setParameter(0, id);			
			List<Map> importProducts = (List<Map>)query.list();			
			return importProducts;			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public List<Map> listAllImportDetail(Long id){
		Session session = sessionFactory.getCurrentSession();
		try {			
			Query query = session.createQuery("Select new Map("
											+ "p.productName as proname,"
											+ "p.productId as proid,"
											+ "impdetail.proQty as proqty,"
											+ "impdetail.unitPrice as prounitprice,"
											+ "impdetail.supplier.supId as supId,"
											+ "impdetail.supplier.supplierName as supname)"
											+ " FROM ImportDetail impdetail" 
											+ " INNER JOIN impdetail.pk1.product  p"
											+ " WHERE impdetail.pk1.importProduct.impId= ? ");
			query.setParameter(0, id);
			List<Map> importDetails = query.list();			
			return importDetails;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.getStackTrace();			
		}
		return null;
	}
	@Override
	@Transactional
	public Boolean deleteImportPro(List<ImportForm> importform, Long id ) {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			session.getTransaction().begin();
			Query query = session.createQuery("FROM ImportDetail ID"
											+ " Where ID.pk1.importProduct.impId = ? "
											+ " and ID.pk1.product.productId = ? ");
				query.setParameter(0,  id);
				query.setParameter(1, 1);
			ImportDetail importdetail =   (ImportDetail)query.uniqueResult();
			session.delete(importdetail);			 			
			session.delete(importdetail);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	@Transactional
	public Long count(Date startDate, Date endDate) {
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			return (Long) session.createCriteria(ImportProduct.class)
								 .add(Restrictions.between("impDate", startDate, endDate))
								 .setProjection(Projections.rowCount()).uniqueResult();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0L;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Object getTotalAmount(Date startDate, Date endDate) {
		Session session = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String StartDate = sdf.format(startDate) + " 00:00:00";
		String EndDate = sdf.format(endDate) + " 23:59:59";
		
		try {
			startDate = sdf2.parse(StartDate);
			endDate =  sdf2.parse(EndDate);
			System.out.println("endDate = " + endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			session = sessionFactory.getCurrentSession();
			SQLQuery query = session.createSQLQuery("SELECT sum(B.pro_qty* unit_price) as total_amount"
					+ " FROM import A INNER JOIN import_detail B on A.imp_id = B.imp_id "
					+ " WHERE A.imp_date BETWEEN '" + startDate + "' and '" + endDate + "'");
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			List<Map<String, Object>> sales= (List<Map<String, Object>>)query.list();
			return sales;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0;
	}

	@Override
	@Transactional
	public List<Product> searchByName(String str) { 
			Session session = sessionFactory.getCurrentSession();
			try {
				Query query = session.createQuery("FROM Product where status='t' and lower(productName) like '" + str.toLowerCase() + "%'");
				 
				List<Product> products = query.list(); 
				 return products; 
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			return null; 
	} 
}
