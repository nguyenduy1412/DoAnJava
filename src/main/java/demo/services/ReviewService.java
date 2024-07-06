package demo.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import demo.models.Author;
import demo.models.Banner;
import demo.models.Review;

public interface ReviewService {
	List<Review> getAll();
	Boolean create(Review a);
	Boolean delete(Integer id);
	Review findById(Integer id);
	List<Review> findByBookIdOrderByIdDesc(Integer id);
	List<Review> findByBookIdAndStatusOrderByIdDesc(Integer id,Boolean status);
	List<Review> findByStarOrderByIdDesc(Integer id);
	List<Review> findAllByOrderByIdDesc();
	List<Review> findByReviewDateOrderByIdDesc(Date date);
	List<Review> findByDayMonthAndYear(int day,int month,int year);
	Page<Review> findByDayMonthAndYear(int day,int month,int year,Integer page,Integer limit);
	Page<Review> findAllByOrderByIdDesc(Integer page,Integer limit);
}
