package demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import demo.models.Orders;
import demo.models.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	List<Review> findByBookIdOrderByIdDesc(Integer id);
	List<Review> findByBookIdAndStatusOrderByIdDesc(Integer id,Boolean status);
	List<Review> findByStarOrderByIdDesc(Integer id);
	List<Review> findByReviewDateOrderByIdDesc(Date date);
	@Query("SELECT o FROM Review o WHERE FUNCTION('YEAR', o.reviewDate) = :year AND FUNCTION('MONTH', o.reviewDate) = :month AND FUNCTION('DAY', o.reviewDate) = :day ORDER BY o.id DESC")
    List<Review> findByDayMonthAndYear(@Param("day") int day, @Param("month") int month, @Param("year") int year);
	@Query("SELECT r FROM Review r WHERE r.star IS NOT NULL ORDER BY r.id DESC")
    List<Review> findAllByStarIsNotNullOrderByIdDesc();
}
