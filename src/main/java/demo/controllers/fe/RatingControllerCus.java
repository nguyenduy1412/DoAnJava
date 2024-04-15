package demo.controllers.fe;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import demo.method.FileName;
import demo.models.Book;
import demo.models.OrderDetail;
import demo.models.Review;
import demo.models.User;
import demo.services.BookService;
import demo.services.OrderDetailService;
import demo.services.ReviewService;
import demo.services.StorageService;
import demo.services.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class RatingControllerCus {
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private StorageService storageService;
	@Autowired
	private BookService bookService;
	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private UserService userService;
	@GetMapping("/add-rating/{id}")
	public String showCart(HttpSession session, Model model,@PathVariable("id") Integer id) {
		OrderDetail detail=this.orderDetailService.findById(id);
		
		Review review=detail.getReview();
		System.out.println("Review "+review);
		review.setBook(detail.getBook());
		review.setStar(1);
		System.out.println(review);
		model.addAttribute("review", review);
		return "add-rating";
	}
	@PostMapping("/add-rating")
	public String addRating(@ModelAttribute("review") Review review,@RequestParam("fileImage") MultipartFile file,HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		String fileName=FileName.getFileNameToDateNow();
		this.storageService.store(file,fileName);
		review.setImg(fileName);
		review.setReviewDate(new Date());
		review.setStatus(true);
		review.setUser(user);
		
		System.out.println(review);
		if(this.reviewService.create(review)) {
			return "redirect:/myacount";
		}
		return "add-rating";
	}
}
