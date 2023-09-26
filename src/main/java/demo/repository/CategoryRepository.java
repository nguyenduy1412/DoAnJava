package demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import demo.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

	List<Category> findByCategoryNameContainingIgnoreCaseOrderByCategoryNameAsc(String keyword);
	

}