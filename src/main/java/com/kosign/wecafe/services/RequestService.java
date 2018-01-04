package com.kosign.wecafe.services;

import java.util.List;
import java.util.Map;

import com.kosign.wecafe.entities.ImportDetail;
import com.kosign.wecafe.entities.ImportProduct;
import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Product;
import com.kosign.wecafe.entities.RequestStock;
import com.kosign.wecafe.entities.Supplier;
import com.kosign.wecafe.forms.ImportForm;
import com.kosign.wecafe.forms.RequestForm;

public interface RequestService {

	public Boolean saveRequestPro(List<RequestForm> requestForm);
	public Boolean updateReqQty(RequestForm requestForm);
	public List<Map>  listRequestDetail(String reqId,Pagination pagination,Boolean isTrue);
	List<Map> listRequestStock();
	Long count(String id);
	Boolean approveRequest(List<RequestForm> requestForm);
}