package com.kosign.wecafe.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class importDetailPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Product product;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ImportProduct importProduct;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ImportProduct getImportProduct() {
		return importProduct;
	}

	public void setImportProduct(ImportProduct importProduct) {
		this.importProduct = importProduct;
	}


	

}
