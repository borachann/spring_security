package com.kosign.wecafe.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kosign.wecafe.entities.ImportProduct;
import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.entities.Supplier;
import com.kosign.wecafe.forms.ImportForm;

public interface ImportService {

	public List<ImportProduct> listAllImportProduct(Pagination pagination, Date startDate, Date endDate, boolean ispagination );
	 
	//public List<Map> listAllImportProduct();
	public Boolean saveImportPro(List<ImportForm> importForm);
	public List<Product> listAllProduct();
	public List<Product> searchByName(String str);
	public List<Supplier> listAllSupplier();
	public Boolean updateImportPro(List<ImportForm> importform, Long id);
	 
	public List<Map> findById(Long id);
	public List<Map> listAllImportDetail(Long id);
	Boolean deleteImportPro(List<ImportForm> importform, Long id);
	public Long count(Date startDate, Date endDate);

	public Object getTotalAmount(Date startDate, Date endDate);
}