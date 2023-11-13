package demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.modelApi.UserBookId;
import demo.models.Book;
import demo.models.Cart;
import demo.models.CartItem;
import demo.models.Orders;
import demo.models.User;
import demo.services.BookService;
import demo.services.CartItemService;
import demo.services.CartService;
import demo.services.OrderService;
import demo.services.UserService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/cartItem")
public class CartItemApi {
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private CartService cartService;
	@Autowired
	private BookService bookService;
	@Autowired
	private UserService userService;
	@GetMapping
	public List<CartItem> list(){
		return this.cartItemService.getAll();
	}
	@PutMapping("/{id}")
	CartItem update(@PathVariable("id") Integer id, @RequestBody CartItem cartItem) {
		
		CartItem carrItemOld=this.cartItemService.findById(id);
		carrItemOld.setQuantity(cartItem.getQuantity());
		return this.cartItemService.update(carrItemOld);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCartItem(@PathVariable("id") Integer id) {
	    cartItemService.delete(id);

	    return new ResponseEntity<>("Xóa đối tượng thành công", HttpStatus.OK);
	}
	@PostMapping("/add")
	public ResponseEntity<String> addCartItem(@RequestBody UserBookId request) {
		User user=this.userService.findById(request.getUserId());
	    Cart cart = cartService.findByUserId(user.getId());
		if (cart == null) {
			cart = new Cart();
			cart.setUser(user);
			this.cartService.create(cart);
		}
		CartItem cartItem = this.cartItemService.findByCartIdAndBookId(cart.getId(),request.getBookId());
		if (cartItem != null) {
			Integer soluong = cartItem.getQuantity() + 1;
			cartItem.setQuantity(soluong);
		} else {
			cartItem = new CartItem();
			cartItem.setCart(cart);
			cartItem.setBook(this.bookService.findById(request.getBookId()));
			cartItem.setQuantity(1);
		}
		this.cartItemService.createOrUpdate(cartItem);
	    return new ResponseEntity<>("Thêm đối tượng thành công", HttpStatus.OK);
	}
}
