package demo.api;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import demo.modelApi.ReviewRating;
import demo.models.Author;
import demo.models.Book;
import demo.models.CartItem;
import demo.models.Category;
import demo.models.OrderDetail;
import demo.models.Review;
import demo.models.User;
import demo.services.AuthorService;
import demo.services.BookService;
import demo.services.OrderDetailService;
import demo.services.ReviewService;
import demo.services.StorageService;
import demo.services.UserService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/review")
public class ReviewApi {
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
	@GetMapping("/{id}")
	public List<Review> listReviewByBook(@PathVariable("id") Integer id){
		return this.reviewService.getAll();
	}
	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file,
			HttpSession session) {
		
		String fileName = file.getOriginalFilename();
		boolean isEmpty = fileName == null || fileName.trim().length() == 0;
		if (!isEmpty) {
			this.storageService.store(file);
			
			return new ResponseEntity<>("Thêm thành công", HttpStatus.OK);
		}
		return new ResponseEntity<>("Thêm thất bại", HttpStatus.BAD_REQUEST);
	}
	@PostMapping("/add-review")
	public ResponseEntity<String> addReview(@RequestBody ReviewRating reviewRating){
		
		Book book=this.bookService.findById(reviewRating.getBookId());
		Double sum=0.0;
		Integer count=0;
		for (Review a : book.getReviews()) {
			sum+=a.getStar();
			count++;
		}
		if(count >0) {
			book.setStar(sum/count);
			this.bookService.create(book);
		}
		User user=this.userService.findById(reviewRating.getUserId());
		OrderDetail detail=this.orderDetailService.findById(reviewRating.getOrderDetailId());
		String img=reviewRating.getImg();
		Integer star=reviewRating.getStar();
		String rating=reviewRating.getRating();
		Review review=new Review();
		review.setBook(book);
		review.setUser(user);
		
		review.setStar(star);
		review.setRating(rating);
		review.setReviewDate(new Date());
		review.setImg(img);
		if(this.reviewService.create(review)) {
			
			this.orderDetailService.create(detail);
			return new ResponseEntity<>("Thêm đối tượng thành công", HttpStatus.OK);
		}
		return new ResponseEntity<>("Thêm đối tượng thất bại", HttpStatus.BAD_REQUEST);
	}
	
}
