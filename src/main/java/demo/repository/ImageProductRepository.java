package demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import demo.models.ImageProduct;
import jakarta.transaction.Transactional;

public interface ImageProductRepository extends JpaRepository<ImageProduct, Integer>{
	@Transactional
	@Modifying
	@Query("delete from ImageProduct i where i.book.id =?1 ")
	void deleteByBookId(Integer id);
	
}
