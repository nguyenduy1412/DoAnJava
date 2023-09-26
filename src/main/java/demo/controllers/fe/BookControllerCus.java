package demo.controllers.fe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.models.Book;
import demo.services.BookService;

@Controller
public class BookControllerCus {
	@Autowired
	private BookService bookService;
	@RequestMapping("/book-detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Book book=this.bookService.findById(id);
		model.addAttribute("book", book);
		return "bookdetail";
	}
}
