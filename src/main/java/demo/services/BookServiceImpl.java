package demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import demo.models.Book;
import demo.models.Category;
import demo.repository.BookRepository;
import demo.repository.CategoryRepository;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookRepository bookRepository;

	@Override
	public List<Book> getAll() {
		// TODO Auto-generated method stub
		return this.bookRepository.findAll();
	}

	@Override
	public Boolean create(Book a) {
		// TODO Auto-generated method stub
		try {
			this.bookRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return false;
	}

	@Override
	public Boolean update(Book a) {
		try {
			this.bookRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return false;
	}

	@Override
	public Boolean delete(Integer id) {
		try {
			this.bookRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return false;
	}

	@Override
	public Book findById(Integer id) {
		// TODO Auto-generated method stub
		return this.bookRepository.findById(id).get();
	}

	@Override
	public List<Book> findBookNew() {
		// TODO Auto-generated method stub
		return this.bookRepository.findBookNew();
	}

	@Override
	public List<Book> findBookSale() {
		// TODO Auto-generated method stub
		return this.bookRepository.findBookSale();
	}

	@Override
	public List<Book> searchBook(String keyword) {
		// TODO Auto-generated method stub
		return this.bookRepository.findByBookNameContainingIgnoreCaseOrderByBookNameAsc(keyword);
	}

	@Override
	public Page<Book> getAll(Integer page) {
		Pageable pageable = PageRequest.of(page - 1, 4);
		return this.bookRepository.findAll(pageable);
	}

	@Override
	public Page<Book> listBookNew(Integer page,Integer limit) {
		List<Book> list = this.findBookNew();
		Pageable pageable = PageRequest.of(page - 1, limit);
		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), list.size());

		List<Book> sublist = list.subList(start, end);

		return new PageImpl<>(sublist, pageable, list.size());
	}

	@Override
	public Page<Book> searchBook(String keyword, Integer page,Integer limit) {
		List list = this.searchBook(keyword);
		Pageable pageable = PageRequest.of(page - 1, limit);
		Integer start = (int) pageable.getOffset();
		Integer end = (int) (pageable.getOffset() + pageable.getPageSize() > list.size() ? list.size()
				: pageable.getOffset() + pageable.getPageSize());

		list = list.subList(start, end);
		return new PageImpl<Book>(list, pageable, this.searchBook(keyword).size());
	}

	@Override
	public List<Book> findByCategoryId(Integer id) {
		// TODO Auto-generated method stub
		return this.bookRepository.findByCategoryIdOrderByIdDesc(id);
	}

	@Override
	public Page<Book> findByCateId(Integer id,Integer page) {
		List<Book> list=this.findByCategoryId(id);
		Pageable pageable = PageRequest.of(page - 1, 4);
		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), list.size());

		List<Book> sublist = list.subList(start, end);

		return new PageImpl<>(sublist, pageable, list.size());
	}

	@Override
	public List<Book> findAllByOrderByBookNameAsc() {
		// TODO Auto-generated method stub
		return this.bookRepository.findAllByOrderByBookNameAsc();
	}

	@Override
	public Page<Book> listBookSale(Integer page) {
		List<Book> list = this.findBookSale();
		Pageable pageable = PageRequest.of(page - 1, 4);
		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), list.size());

		List<Book> sublist = list.subList(start, end);

		return new PageImpl<>(sublist, pageable, list.size());
	}

	 
	 

}
