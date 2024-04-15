package demo.services;

import java.util.Date;
import java.util.List;

import demo.models.Author;
import demo.models.Banner;
import demo.models.Review;

public interface ReviewService {
	List<Review> getAll();
	Boolean create(Review a);
	Boolean delete(Integer id);
	Review findById(Integer id);
	List<Review> findByBookIdOrderByIdDesc(Integer id);
	List<Review> findByStarOrderByIdDesc(Integer id);
	List<Review> findAllByOrderByIdDesc();
	List<Review> findByReviewDateOrderByIdDesc(Date date);
	
}
