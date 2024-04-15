package demo.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.models.Author;
import demo.models.Receipt;
import demo.models.Review;
import demo.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public Boolean create(Review a) {
		try {
			this.reviewRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean delete(Integer id) {
		try {
			this.reviewRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return false;
	}

	@Override
	public Review findById(Integer id) {
		// TODO Auto-generated method stub
		return this.reviewRepository.findById(id).get();
	}

	@Override
	public List<Review> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Review> findByBookIdOrderByIdDesc(Integer id) {
		// TODO Auto-generated method stub
		return this.reviewRepository.findByBookIdOrderByIdDesc(id);
	}

	@Override
	public List<Review> findByStarOrderByIdDesc(Integer id) {
		// TODO Auto-generated method stub
		return this.reviewRepository.findByStarOrderByIdDesc(id);
	}

	@Override
	public List<Review> findAllByOrderByIdDesc() {
		// TODO Auto-generated method stub
		return this.reviewRepository.findAllByOrderByIdDesc();
	}

	@Override
	public List<Review> findByReviewDateOrderByIdDesc(Date date) {
		// TODO Auto-generated method stub
		return this.reviewRepository.findByReviewDateOrderByIdDesc(date);
	}

}
