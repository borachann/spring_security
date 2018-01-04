package com.kosign.wecafe.services;

import java.util.List;

import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.forms.ProductForm;

public interface ProductService {
	public List<Product> findAllProducts(Pagination pagination);
	public List<Product> searchProducts(Pagination pagination, String str, Boolean ispagination);
	public Product findProductById(Long id);
	public Long count();
	public boolean addNewProduct(Product product);
	public boolean updateProduct(ProductForm product);
	public boolean deleteProduct(Long id);
	public boolean updateProductStatus(Long id);
	
}
