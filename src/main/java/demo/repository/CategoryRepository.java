package demo.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;


import demo.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

	List<Category> findByCategoryNameContainingIgnoreCaseOrderByCategoryNameAsc(String keyword);
	List<Category> findAllByOrderByIdAsc();

}