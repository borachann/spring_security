package com.kosign.wecafe.services;

import java.util.List;

import com.kosign.wecafe.entities.Pagination;
import com.kosign.wecafe.entities.Slide;

public interface SlideService {
	
	public List<Slide> getAllSlidesPagination(Pagination pagination, boolean ispagination);
	public List<Slide> getAllSlides();
	public Boolean addNewSlide(Slide slide);
	public Boolean updateSlide(Slide slide);
	public Boolean deleteSlide(Integer id);
	public Slide findSlideById(Integer id);
	//public Long count();
}
