package demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.models.Book;
import demo.services.BookService;
import demo.services.OrderService;

@RestController
@RequestMapping("/api/book")
public class BookApi {
	@Autowired
	private BookService bookService;
	@GetMapping("/{id}")
	public String reloadItem(@PathVariable("id") Integer id ) {
		return "layout/reloadCartItem";
	}
	@GetMapping
	public List<Book> getBook() {
		return this.bookService.getAll();
	}
}
