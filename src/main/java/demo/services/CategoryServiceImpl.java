package demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import demo.models.Category;
import demo.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getAll() {
		// TODO Auto-generated method stub
		return this.categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "categoryName"));
	}
	@Override
	public Boolean create(Category a) {
		// TODO Auto-generated method stub
		try {
			this.categoryRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}

	@Override
	public Boolean update(Category a) {
		try {
			this.categoryRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}

	@Override
	public Boolean delete(Integer id) {
		try {
			this.categoryRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}

	@Override
	public Category findById(Integer id) {
		// TODO Auto-generated method stub
		return this.categoryRepository.findById(id).get();
	}
	@Override
	public List<Category> searchCategory(String keyword) {
		
		return this.categoryRepository.findByCategoryNameContainingIgnoreCaseOrderByCategoryNameAsc(keyword);
	}
	@Override
	public Page<Category> getAll(Integer page) {
		Pageable pageable=PageRequest.of(page-1, 5);
		return this.categoryRepository.findAll(pageable);
	}
	@Override
	public Page<Category> searchCategory(String keyword, Integer page) {
		List list=this.searchCategory(keyword);
		Pageable pageable=PageRequest.of(page-1,5);
		Integer start=(int) pageable.getOffset();
		Integer end=  (int) ( pageable.getOffset() + pageable.getPageSize()  > list.size() ? list.size() : pageable.getOffset()+pageable.getPageSize());
		list=list.subList(start, end);
		return new PageImpl<Category>(list, pageable, this.searchCategory(keyword).size());
	}
	@Override
	public List<Category> findAllByOrderByIdAsc() {
		return this.categoryRepository.findAllByOrderByIdAsc();
	}
	
}
