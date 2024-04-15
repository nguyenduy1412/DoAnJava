package demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.modelApi.UserBookId;
import demo.models.Book;
import demo.models.Cart;
import demo.models.CartItem;
import demo.models.Favourite;
import demo.models.FavouriteItem;
import demo.models.User;
import demo.services.BookService;
import demo.services.CartItemService;
import demo.services.CartService;
import demo.services.FavouriteItemService;
import demo.services.FavouriteService;
import demo.services.UserService;

@RestController
@RequestMapping("/api/favourite")
public class FavouriteApi {
	@Autowired
	private CartService cartService;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private FavouriteService favouriteService;
	@Autowired
	private FavouriteItemService favouriteItemService;
	@Autowired
	private BookService bookService;
	@Autowired
	private UserService userService;
	@GetMapping("/{id}")
	public List<FavouriteItem> getListFavourite(@PathVariable("id") long userId
		) {
		Favourite favourite=this.favouriteService.findByUserId(userId);
		
		return this.favouriteItemService.findByFavouriteIdOrderByIdDesc(favourite.getId());
	}
	@GetMapping("/checkFavourite")
	public FavouriteItem checkFavourite(@RequestParam("userId") long userId,@RequestParam("bookId") Integer bookId) {
		Book book=this.bookService.findById(bookId);
		Favourite favourite=this.favouriteService.findByUserId(userId);
		FavouriteItem favouriteItem=this.favouriteItemService.findByFavouriteIdAndBookId(favourite.getId(), book.getId());
		System.out.println(favouriteItem);
		return favouriteItem;
	}
	@PostMapping("/add")
	public ResponseEntity<String> addCartItem(@RequestBody UserBookId request) {
		User user=this.userService.findById(request.getUserId());
		Book book=this.bookService.findById(request.getBookId());
	    Favourite favourite = favouriteService.findByUserId(user.getId());
		if (favourite == null) {
			favourite = new Favourite();
			favourite.setUser(user);
			this.favouriteService.create(favourite);
		}
		FavouriteItem favouriteItem=new FavouriteItem();
		favouriteItem.setBook(book);
		favouriteItem.setFavourite(favourite);
		if(this.favouriteItemService.createOrUpdate(favouriteItem)) {
			 return new ResponseEntity<>("Thêm đối tượng thành công", HttpStatus.OK);
		}
		return new ResponseEntity<>("Thêm thất bại", HttpStatus.BAD_REQUEST);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteFavouriteItem(@PathVariable("id") Integer id) {
		this.favouriteItemService.delete(id);
	    return new ResponseEntity<>("Xóa đối tượng thành công", HttpStatus.OK);
	}
}
