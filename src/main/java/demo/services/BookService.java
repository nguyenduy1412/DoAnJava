package demo.services;

import java.util.List;

import org.springframework.data.domain.Page;

import demo.models.Book;
import demo.models.Category;



public interface BookService {
	List<Book> getAll();
	Boolean create(Book a);
	Boolean update(Book a);
	Boolean delete(Integer id);
	Book findById(Integer id);
	List<Book> findBookNew();
	List<Book> findBookSale();
	List<Book> searchBook(String keyword);
	Page<Book> getAll(Integer page);
	Page<Book> listBookNew(Integer page,Integer limit);
	Page<Book> listBookSale(Integer page);
	Page<Book> searchBook(String keyword,Integer page,Integer limit);
	List<Book> findByCategoryId(Integer id);
	Page<Book> findByCateId(Integer id,Integer page);
	// lấy danh sách sách theo thứ tự alphabeta
	List<Book> findAllByOrderByBookNameAsc();
}
