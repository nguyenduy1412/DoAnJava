package demo.controllers.fe;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.models.Book;
import demo.models.Recent_Products;
import demo.models.Review;
import demo.models.User;
import demo.services.BookService;
import demo.services.RecentProductService;
import demo.services.ReviewService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BookControllerCus {
	@Autowired
	private BookService bookService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private RecentProductService recentProductService;
	@RequestMapping("/book-detail/{id}")
	public String detail(Model model,HttpSession session, @PathVariable("id") Integer id) {
		
		Book book=this.bookService.findById(id);
		User user = (User) session.getAttribute("user");
		List<Recent_Products> listRecent=new ArrayList<Recent_Products>();
		if (user != null) {
			listRecent=this.recentProductService.getByUser(user.getId());
			System.out.println(" user"+user.getId()+"book"+book.getId()+" KQ+ "+this.recentProductService.checkProduct(user.getId(), id));
			// nếu bản ghi không trùng thì thêm
			if(this.recentProductService.checkProduct(user.getId(), id)== false)
			{
				
				Recent_Products recent=new Recent_Products();
				recent.setBook(book);
				recent.setUser(user);
				if(this.recentProductService.create(recent)) {
					System.out.println("Alo");
				}
				else {
					System.out.println("Loi");
				}
				
			}
			
		}
		model.addAttribute("listRecent", listRecent);
		model.addAttribute("book", book);
		List<Review> listReview=this.reviewService.findByBookIdOrderByIdDesc(id);
		model.addAttribute("listReview", listReview);
		
//		model.addAttribute("count", listReview.size());
		return "bookdetail";
	}
}
