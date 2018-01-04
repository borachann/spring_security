package com.kosign.wecafe.controller;


import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosign.wecafe.entities.ImportDetail;
import com.kosign.wecafe.entities.Order;
import com.kosign.wecafe.entities.OrderDetail;
import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.entities.RequestStockDetail;
import com.kosign.wecafe.forms.Cart;
import com.kosign.wecafe.forms.ImportForm;
import com.kosign.wecafe.forms.RequestForm;
import com.kosign.wecafe.services.ImportService;
import com.kosign.wecafe.services.RequestService;
import com.kosign.wecafe.services.SellProductServiceImpl;
import com.kosign.wecafe.services.SellProductsService;
import com.kosign.wecafe.services.SellService;
import com.kosign.wecafe.services.UserService;

@Controller
public class SellController {
	
	@Inject SellProductsService sellProductService;
	
	@Inject SellService sellService;
	
	@Inject ImportService importService;
	@Inject RequestService requestService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/seller/request_product", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Boolean saveRequestProduct(@RequestBody List<RequestForm> requestForm)	{
		RequestStockDetail requestDetail = new RequestStockDetail();
		System.out.println("request size================="+requestForm.size());
		try {			
			return requestService.saveRequestPro(requestForm);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	@RequestMapping(value="/seller/searchproduct", method=RequestMethod.POST)
	public @ResponseBody List<Product> searchProductName(){
		return importService.listAllProduct();
	}
	
	@SuppressWarnings({ "unchecked" })
	private ResponseEntity<Map<String, Object>> listData(String q, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Cart> carts = new ArrayList<Cart>();
		if(session.getAttribute("CARTS")!=null){
			carts = (ArrayList<Cart>)session.getAttribute("CARTS");
		}
		map.put("carts",carts);
		map.put("searchpro", importService.searchByName(q));
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/seller/searchbyname/{proName}", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> searchByName(@PathVariable ("proName") String proname, HttpSession session){
		return listData(proname, session);
	}
	
	@RequestMapping(value="/seller/searchbyname/", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> searchByName(HttpSession session){
		return listData("", session);
	}
	
	@RequestMapping(value = "/seller/request_products", method = RequestMethod.GET)
	public String requestProducts(Map<String, Object> model) {
		return "seller/request_stock";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/seller")
	public String listAllProducts(HttpSession session, Map<String, Object> model, Principal principal){
		List<Cart> carts = new ArrayList<Cart>();
		if(session.getAttribute("CARTS")!=null){
			carts = (List<Cart>)session.getAttribute("CARTS");
		}
		model.put("carts", carts);
		model.put("products", sellProductService.getAllProducts());
		model.put("user", userService.findUserByUsername(principal.getName()));
		return "seller/seller";
	}
	
	@RequestMapping(value="/seller/getordered", method=RequestMethod.POST)
	public  @ResponseBody List<Order> listAllOrders(){
		try{
			return sellProductService.getOrdered();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/seller/addtocart", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody List<Cart> addProductToCart(HttpSession session, @RequestBody Cart cart){
		List<Cart> carts = new ArrayList<Cart>();
		if(session.getAttribute("CARTS")!=null){
			carts = (ArrayList<Cart>)session.getAttribute("CARTS"); 
			for(int i=0; i <carts.size();i++){ 
				System.out.println("cart.getProductName" + cart.getProductName());
				System.out.println("carts.get(i).getProductName()" + carts.get(i).getProductName());
				if(carts.get(i).getProductId().equals(cart.getProductId())){
					carts.get(i).setQuantity(carts.get(i).getQuantity()+ cart.getQuantity());
					carts.get(i).setTotalAmount(cart.getPrice().multiply(new BigDecimal(carts.get(i).getQuantity())));
					session.setAttribute("CARTS", carts);
					return carts;
				}
			}
		}
		carts.add(cart);  
		session.setAttribute("CARTS", carts); 
		return carts;
	}
	
	@RequestMapping(value="/seller/removeallfromcart", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Boolean removeProductInCart(HttpSession session){
		if(session.getAttribute("CARTS") != null){
			session.setAttribute("CARTS", null);
			return true;
		}
		return false;
		
		
	}
	@RequestMapping(value="/seller/listtocart", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody List<Cart> listAllProductsInCart(HttpSession session/*, @RequestBody Cart cart*/){
		List<Cart> carts = new ArrayList<Cart>();
		if(session.getAttribute("CARTS")!=null){
			carts = (ArrayList<Cart>)session.getAttribute("CARTS");
		}
		return carts;
	}

	@RequestMapping(value="/seller/removetocart/{id}", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cart> removeproductfromcart(HttpSession session, @PathVariable("id") Long productId){
		System.out.println("Product ID = " + productId);
		List<Cart> carts = new ArrayList<Cart>();
		if(session.getAttribute("CARTS") != null){
			carts = (ArrayList<Cart>)session.getAttribute("CARTS");
		}
			
		for(Cart cart: carts){
			if(cart.getProductId().equals(productId)){
				long bb = cart.getQuantity();
				long aa = 1L;
				if( bb > aa){
					cart.setQuantity(cart.getQuantity() - 1);
					System.out.println("cart.getQuantity()" + cart.getQuantity());
				}
				else{
					System.out.println("cart.getQuantity()" + cart.getQuantity());
					carts.remove(cart);
					System.out.println("TRUE");
					break;
				} 
			}
			System.err.println("FALSE");
		}
		session.setAttribute("CARTS", carts);
		return carts;
	}
	@RequestMapping(value="/seller/removefromcart/{id}", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody List<Cart> removeProductFromCart(HttpSession session, @PathVariable("id") Long productId){
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
	@RequestMapping(value="/seller/insertcartsell", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody Boolean insertProductFromCart(HttpSession session){
		
		List<Cart> carts = new ArrayList<Cart>();
		if(session.getAttribute("CARTS")!=null){
			carts = (ArrayList<Cart>)session.getAttribute("CARTS");
			System.out.println("CARTS SIZE:"+ carts.size());
			return sellProductService.addNewSaleProducts(carts);
		}
		return false;
	}
	
	@RequestMapping(value="/seller/orders", method=RequestMethod.GET)
	public  @ResponseBody List<Order> getAllOrders(){
		return sellService.getAllOrders();		
	}
	
	@RequestMapping(value="/seller/getOrderedDetail/{orderId}", method=RequestMethod.GET)
	public @ResponseBody List<OrderDetail> getOrderedDetail(@PathVariable("orderId") Long orderID){
		List<OrderDetail> orderdetail = new ArrayList<OrderDetail>();
		orderdetail = sellProductService.getOrderedDetail(orderID);
		return orderdetail;
	}
	@RequestMapping(value="/seller/cancelOrder/{orderId}", method=RequestMethod.GET)
	public @ResponseBody Boolean cancelOrder(@PathVariable("orderId") Long orderID){ 
			sellProductService.cancelOrder(orderID); 
		return false;
	}
	@RequestMapping(value="/seller/confirmOrder/{orderId}", method=RequestMethod.GET)
	public @ResponseBody Boolean confirmOrder(@PathVariable("orderId") Long orderID){ 
			sellProductService.addOrderToSale(orderID); 
		return false;
	}
	@RequestMapping(value="/seller/updateOrderProduct/{orderId}/{productId}/{quatity}", method=RequestMethod.GET)
	public @ResponseBody Boolean updateOrderProduct(@PathVariable("orderId") Long orderID, @PathVariable("productId") Long productID, @PathVariable("quatity") Long qty){
		sellProductService.updateOrderProduct(orderID, productID, qty);
		return false;
	}
	
	@RequestMapping(value="/seller/deletedOrderProduct/{orderId}/{productId}", method=RequestMethod.GET)
	public @ResponseBody Boolean deletedOrderProduct(@PathVariable("orderId") Long orderID,@PathVariable("productId") Long productID){
		sellProductService.deleteOrderProduct(orderID, productID);
		
		System.out.println("orderIIID" + orderID);
		System.out.println("oPPerIIID" + productID);
		
		return false;
	}
	
//	@RequestMapping(value="/seller/updateOrderProduct", method=RequestMethod.GET)
//	public @ResponseBody Boolean updateOrderProduct(@RequestBody OrderDetail orderDetailForm){
//		OrderDetail orderDetail = new OrderDetail();
//		Order orders = new Order();
//		orders.setOrderId(orderDetailForm.get);;
//		orderDetail.setOrder(orderDetailForm.getOrder().getOrderId());
//		orderDetail.setProQty(orderDetailForm.getProQty());
//		sellProductService.updateOrderProduct(orderDetail);
//		return false;
//	}
}
