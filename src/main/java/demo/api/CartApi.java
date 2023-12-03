package demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.models.Book;
import demo.models.Cart;
import demo.models.CartItem;
import demo.services.BookService;
import demo.services.CartItemService;
import demo.services.CartService;
import demo.services.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartApi {
	@Autowired
	private CartService cartService;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private UserService userService;
	@GetMapping("/{id}")
	public Cart getCart(@PathVariable("id") long id) {
		Cart cart=this.cartService.findByUserId(id);
		cart.setTotal(cart.totalPrice());
		this.cartService.create(cart);
		return cart;
	}
	@GetMapping("/listItem/{id}")
	public List<CartItem> getListItem(@PathVariable("id") long id) {
		Cart cart=this.cartService.findByUserId(id);
		return this.cartItemService.findByCartId(cart.getId());
	}
	@GetMapping("/soluong/{id}")
	public Integer soluong(@PathVariable("id") long id){
		Cart cart=this.cartService.findByUserId(id);
		Integer soluong=this.cartItemService.sumQuantityByCartId(cart.getId());
		if(soluong==null)
			return 0;
		return soluong;
	}
}
