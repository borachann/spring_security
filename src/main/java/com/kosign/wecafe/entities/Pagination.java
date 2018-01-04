package com.kosign.wecafe.entities;

import java.io.Serializable;

public class Pagination implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int currentPage;
	private int perPage;
	private Long totalCount;
	private int totalPages;
	
	public Pagination(){
		this(1,15,0L,0);
	}	
	
	public Pagination(int currentPage, int perPage, Long totalCount, int totalPages){
		this.currentPage = currentPage;
		this.perPage = perPage;
		this.totalCount = totalCount;
		this.totalPages = totalPages;
	}
	public int getCurrentPage() {
		return currentPage;
	}

	public int totalPages(){
		totalPages = (int) Math.ceil((double)this.totalCount/perPage);
		return totalPages;
	}
	
	public int nextPage(){
		return this.currentPage+1;
	}
	
	public int previousPage(){
		return this.currentPage-1;
	}
	
	public boolean hasNextPage(){
		return this.nextPage() <=this.totalPages()? true :false;
	}
	
	public boolean hasPreviousPage(){
		return this.previousPage()>=1 ? true : false;
	}	
	
	public int offset(){
		return ((this.currentPage-1) * this.perPage);
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		if(perPage > 100 ){
			perPage = 10;
		}
		this.perPage = perPage;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		if(totalCount == 0)
			this.totalCount = 1L;
		else
			this.totalCount = totalCount;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
}
