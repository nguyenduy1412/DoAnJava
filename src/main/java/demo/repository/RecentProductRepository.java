package demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.models.Author;
import demo.models.Recent_Products;

public interface RecentProductRepository extends JpaRepository<Recent_Products, Integer> {
	List<Recent_Products> findTop4ByUserIdOrderByIdDesc(Long id);
	
	List<Recent_Products> findByUserIdAndBookId(Long userId, Integer bookId);
}
