package demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.models.Book;
import demo.models.Category;
import demo.models.Orders;
import demo.services.BookService;
import demo.services.CategoryService;
import demo.services.OrderService;

@RestController
@RequestMapping("/api/category")
public class CategoryApi {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BookService bookService;
	@GetMapping("/")
	public List<Category> list(){
		return this.categoryService.findAllByOrderByIdAsc();
	}
	@GetMapping("/{id}")
	public List<Book> listBook(@PathVariable("id") Integer id ){
		return this.bookService.findByCategoryId(id);
	}
	@GetMapping
	public Page<Book> listCategoryId(@RequestParam("id") Integer id, @RequestParam("page") Integer page){
		return this.bookService.findByCateId(id,page);
	}
}
