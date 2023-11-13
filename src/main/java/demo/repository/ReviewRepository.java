package demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.models.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	List<Review> findByBookIdOrderByIdDesc(Integer id);
}
