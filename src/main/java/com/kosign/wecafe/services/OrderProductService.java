package com.kosign.wecafe.services;

import java.util.List;

import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.forms.Cart;

public interface OrderProductService {
	public boolean addNewOrder(List<Cart> carts);
	public List<Product> getProduct();
}
