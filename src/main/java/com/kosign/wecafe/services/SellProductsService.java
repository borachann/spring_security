package com.kosign.wecafe.services;

import java.util.List;

import com.kosign.wecafe.entities.Order;
import com.kosign.wecafe.entities.OrderDetail;
import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.entities.RequestStock;
import com.kosign.wecafe.forms.Cart;

public interface SellProductsService {

	public List<Product> getAllProducts();
	public List<Order> getOrdered();
	public List<RequestStock> getRequestStock();
	public List<OrderDetail> getOrderedDetail(Long orderID);
	public Boolean addNewSaleProducts(List<Cart> carts);
	public Boolean cancelOrder(Long orderID);
	public Boolean addOrderToSale(Long orderID);
	public Boolean updateOrderProduct(Long orderId, Long productId, Long qty );
	public Boolean deleteOrderProduct(Long orderId, Long productId);
//	public Boolean updateOrderProduct(OrderDetail orderDetail);
}
