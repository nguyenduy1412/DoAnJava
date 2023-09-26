package demo.controllers.fe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo.models.Book;
import demo.models.Category;
import demo.services.BookService;
import demo.services.CategoryService;

@Controller
public class CategoryControllerCus {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BookService bookService;
	
	@RequestMapping("/find-category/{id}")
	public String detail(Model model, @PathVariable("id") Integer id,@RequestParam(name="page",defaultValue = "1") Integer page) {
		List<Category> listCate=this.categoryService.getAll();
		Page<Book> listBookByCate=this.bookService.findByCateId(id,page);
		model.addAttribute("listBook", listBookByCate);
		model.addAttribute("listCate", listCate);
		model.addAttribute("totalPage", listBookByCate.getTotalPages());
		model.addAttribute("currentPage",page);
		model.addAttribute("id",id);
		return "category";
	}
	@RequestMapping("/find-book")
	public String index(Model model,@Param("keyword") String keyword,
    		@RequestParam(name="page",defaultValue = "1") Integer page) {
		Page<Book> list=this.bookService.getAll(page);
		System.out.println("key" + keyword);
		if(keyword !=null) {
			list=this.bookService.searchBook(keyword,page,12);
			for (Book book : list) {
				System.out.println("list"+book.getBookName());
			}
			model.addAttribute("keyword",keyword);
		}
		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage",page);
		model.addAttribute("list",list);
		return "find-book";
	}
}
