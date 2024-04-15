package demo.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.models.Book;
import demo.services.BookService;
import demo.services.OrderDetailService;
import demo.services.OrderService;

@RestController
@RequestMapping("/api/book")
public class BookApi {
	@Autowired
	private BookService bookService;
	@Autowired
	private OrderDetailService orderDetailService;
	@GetMapping("/{id}")
	public String reloadItem(@PathVariable("id") Integer id ) {
		return "layout/reloadCartItem";
	}
	@GetMapping("/bookId/{id}")
	public List<Book> book(@PathVariable("id") Integer id ) {
		List<Book> list=new ArrayList<Book>();
		list.add(this.bookService.findById(id));
		return list;
	}
	@GetMapping
	public List<Book> getBook() {
		return this.bookService.getAll();
	}
	@GetMapping("/sale")
	public Page<Book> getBookSale(@RequestParam("page") Integer page) {
		return this.bookService.listBookSale(page);
	}
	@GetMapping("/new")
	public Page<Book> getBookNew(@RequestParam("page") Integer page) {
		return this.bookService.listBookNew(page,4);
	}
	@GetMapping("/trend")
	public Page<Book> getBookTrend(@RequestParam("page") Integer page) {
		return this.orderDetailService.findBookTrend(page);
	}
	@GetMapping("/list")
	public Page<Book> listBook(@RequestParam(name="page",defaultValue = "1") Integer page,
			@RequestParam("keyword") String keyword) {
		Page<Book> list=this.bookService.getAll(page);
		if(!keyword.isBlank()) {
			
			list=this.bookService.searchBook(keyword,page,6);
			
		}
		return list;
	}
	
}
