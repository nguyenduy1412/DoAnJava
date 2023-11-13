package demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import demo.models.Book;



public interface BookRepository extends JpaRepository<Book, Integer>{
	List<Book> findByBookNameContainingIgnoreCaseOrderByBookNameAsc(String keyword);
	List<Book> findAllByOrderByBookNameAsc();
	List<Book> findByCategoryIdOrderByIdDesc(Integer id);
	@Query("SELECT c FROM Book c WHERE c.sale > 0")
	List<Book> findBookSale();
	@Query("SELECT c FROM Book c ORDER BY c.id DESC")
	// @Query("SELECT c FROM Book c WHERE MONTH(c.dateAdded) = MONTH(CURRENT_DATE) AND YEAR(c.dateAdded) = YEAR(CURRENT_DATE) ORDER BY c.id DESC")
    List<Book> findBookNew();
}
