package com.kosign.wecafe.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.forms.Cart;
import com.kosign.wecafe.services.OrderProductService;
import com.kosign.wecafe.services.ProductService;
import com.kosign.wecafe.services.SlideService;

@Controller
@RequestMapping(value="/order")
public class OrderController {

	@Inject 
	OrderProductService orderProductService ;
	
	@Inject
	ProductService productService;
	
	@Autowired
	SlideService slideService;
	
	@RequestMapping(value={"/"})
	public String index(Map<String ,Object> model){
		List<Product> products = orderProductService.getProduct();
		model.put("products", products);
		model.put("slides", slideService.getAllSlides());
		
		for(Product product : products){
			System.out.println(product.getProductName());
		}
		return "order/order";
		
	}
	
	@RequestMapping(value="/addtocart", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody List<Cart> addProductToCart(HttpSession session, @RequestBody Cart cart){
		List<Cart> carts = new ArrayList<Cart>();
		if(session.getAttribute("CARTS")!=null){
			carts = (ArrayList<Cart>)session.getAttribute("CARTS");
			for(int i=0; i <carts.size();i++){ 
				if(carts.get(i).getProductId().equals(cart.getProductId())){
					carts.get(i).setQuantity(cart.getQuantity());
					carts.get(i).setComment(cart.getComment());
					carts.get(i).setTime(cart.getTime());
					carts.get(i).setTotalAmount(cart.getPrice().multiply(new BigDecimal(carts.get(i).getQuantity())));
					session.setAttribute("CARTS", carts);
					return carts; 
				}
			} 
		}
		carts.add(cart); 
		
		session.setAttribute("CARTS", carts);
		System.out.println("size after add: " + carts.size());
		return carts;
	}
	
	@RequestMapping(value="/removetocart/{id}", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody List<Cart> removeProductFromCart(HttpSession session, @PathVariable("id") Long productId){
		
		System.out.println("PRODUCT ID="+ productId);
		List<Cart> carts = new ArrayList<Cart>();
		if(session.getAttribute("CARTS")!=null){
			carts = (ArrayList<Cart>)session.getAttribute("CARTS");
		}
		
		for(Cart cart : carts){
			if(cart.getProductId().equals(productId)){
				carts.remove(cart);
				System.out.println("TRUE");
				break;
			}
			System.out.println("FALSE");
		}
		
		session.setAttribute("CARTS", carts);
		
		return carts;
	}
	
	@RequestMapping(value="/removeAllFromCart", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody Boolean removeAllFromCart(HttpSession session){
		
		if(session.getAttribute("CARTS")!=null){
			session.setAttribute("CARTS", null);
			return true;
		}
		return false;	
	}
	
	@RequestMapping(value="/listcart", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody List<Cart> listAllProductsInCart(HttpSession session/*, @RequestBody Cart cart*/){
		List<Cart> carts = new ArrayList<Cart>();
		if(session.getAttribute("CARTS")!=null){
			carts = (ArrayList<Cart>)session.getAttribute("CARTS");
		}
		return carts;
	}
	
	@RequestMapping(value="/insertcartorder", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody List<Cart> insertProductFromCart(HttpSession session){ 
		List<Cart> carts = new ArrayList<Cart>();
		if(session.getAttribute("CARTS")!=null){
			carts = (ArrayList<Cart>)session.getAttribute("CARTS");
			orderProductService.addNewOrder(carts);
		} 	
		return carts;
	}
	
/*	@RequestMapping(value="/editproduct/{id}", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody Map<String, Object> findProductById(@PathVariable("id") Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		try{
			model.put("PRODUCT", productService.findProductById(id));
		}catch(Exception ex){
			model.put("ERRORS", ex.getMessage());
		}
		return model;
	}*/
	
	@RequestMapping(value="/editproduct/{id}", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody Product findProductById(@PathVariable("id") Long id){
		return productService.findProductById(id);
	}
 
}
