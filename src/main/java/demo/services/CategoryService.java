package demo.services;

import java.util.List;

import org.springframework.data.domain.Page;

import demo.models.Category;

public interface CategoryService {
	List<Category> getAll();
	List<Category> findAllByOrderByIdAsc();
	Boolean create(Category a);
	Boolean update(Category a);
	Boolean delete(Integer id);
	Category findById(Integer id);
	List<Category> searchCategory(String keyword);
	Page<Category> getAll(Integer page);
	Page<Category> searchCategory(String keyword,Integer page);

}
