package com.kosign.wecafe.controller.admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosign.wecafe.entities.Category;
import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.entities.Unit;
import com.kosign.wecafe.entities.User;
import com.kosign.wecafe.forms.ProductForm;
import com.kosign.wecafe.services.CategoryService;
import com.kosign.wecafe.services.ProductService;
import com.kosign.wecafe.services.UnitService;
import com.kosign.wecafe.services.UserService;

@Controller
public class ProductController {

	@Inject
	private ProductService productService;

	@Inject
	private CategoryService categoryService;
	
	@Inject
	private UnitService unitService;

	@Autowired
	private UserService userService;
	
	@Autowired
	ServletContext servletContext;
	
	
	@RequestMapping(value = "/admin/approve_products", method = RequestMethod.GET)
	public String approveProducts(Map<String, Object> model) {
		return "admin/approve_stock";
	}

	@RequestMapping(value = "/admin/products", method = RequestMethod.GET)
	public String listAllProducts(Map<String, Object> model) {
		model.put("categories", categoryService.getAllCategories());
		model.put("units", unitService.getAllUnits());
		return "admin/productlist";
	}

	@RequestMapping(value = "/admin/productadd", method = RequestMethod.GET)
	public String productadd(Map<String, Object> model) {
		model.put("categories", categoryService.getAllCategories());
		model.put("units", unitService.getAllUnits());
		return "admin/productadd";
	}

	@RequestMapping(value="/admin/product/add", method=RequestMethod.POST)
	public  @ResponseBody Boolean addNewProduct(ProductForm form , HttpServletRequest request){
		Product product = new Product();
		Category category = new Category();
		Unit unit = new Unit();
		category.setCatId(form.getCategoryId());
		unit.setUnitId(form.getUnitId());  
		User user = userService.findUserByUsername(getPrincipal());  
		product.setProductType("1"); 
		product.setStockType("1");
		product.setCan_sell_holiday("1");
		product.setProductName(form.getProductName());
		product.setQuantity(form.getQuantity());
		product.setCostPrice(new BigDecimal(0));
		product.setSalePrice(new BigDecimal(0));
		product.setUnitPrice(new BigDecimal(0));
		product.setCategory(category);
		product.setCreatedBy(user);
		product.setUnit(unit);
		product.setLastUpdatedBy(user);
		product.setImage(form.getImage());
		product.setCreatedDate(new Date());
		product.setLastUpdatedDate(new Date());
		product.setStatus(false);
		
		return productService.addNewProduct(product);  
	}

	@RequestMapping(value = "/admin/product/{id}", method = RequestMethod.GET)
	public String updateProduct(@PathVariable("id") Long id, Map<String, Object> model) {
		Product product = productService.findProductById(id);
		model.put("product", product);
		model.put("categories", categoryService.getAllCategories());
		model.put("units", unitService.getAllUnits());
		return "admin/productupdate";
	}
	
	

	@RequestMapping(value = "/admin/product/update", method = RequestMethod.POST)
	public @ResponseBody boolean updateProduct(ProductForm product, HttpServletRequest request) {
		return productService.updateProduct(product);
	}

	@RequestMapping(value = "/admin/product/delete/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean deleteProduct(@RequestBody @PathVariable("id") Long id) {
		System.out.println("DELETE ID=" + id);
		return productService.deleteProduct(id);
	}
	
	@RequestMapping(value = "/admin/product/status/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody boolean changeProductStatus(@PathVariable("id") Long id) {
		System.out.println("ID=" + id);
		return productService.updateProductStatus(id);
	}
	
	@RequestMapping(value = "/admin/product/invoice")
	public String productInvoice(){
		return "admin/invoice";
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

}

