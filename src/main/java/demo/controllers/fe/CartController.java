package demo.controllers.fe;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo.models.Cart;
import demo.models.CartItem;
import demo.models.CustomUserDetails;
import demo.models.User;
import demo.repository.CartItemRepository;
import demo.repository.CartRepository;
import demo.services.BookService;
import demo.services.CartItemService;
import demo.services.CartService;
import jakarta.servlet.http.HttpSession;

@Controller

public class CartController {
	@Autowired
	private CartService cartService;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private BookService bookService;

	@GetMapping("/cart")
	public String showCart(HttpSession session, Model model) {
		Integer soluong = null;
		double total = 0;
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		Integer cartId = (Integer) session.getAttribute("cartId");
		System.out.println("CartId trong cart "+cartId);
		if (cartId != null) {
			List<CartItem> listItem = this.cartItemService.findByCartIdOrderByIdDesc(cartId);
			session.setAttribute("listItem", listItem);
			soluong = this.cartItemService.sumQuantityByCartId(cartId);
			total = this.cartService.findById(cartId).totalPrice();
		}
		soluong = (soluong == null) ? 0 : soluong;
		session.setAttribute("total", total);
		session.setAttribute("soluong", soluong);

		return "cart";
	}

	@PostMapping("/cart")
	public String addCart(@RequestParam("id") Integer idProduct, @RequestParam("quantity") Integer quantity,
			Principal principal, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		
		Cart cart = cartService.findByUserId(user.getId());
		if (cart == null) {
			cart = new Cart();
			cart.setUser(user);
			this.cartService.create(cart);
			
		}
		session.setAttribute("cartId", cart.getId());
		System.out.println("CartId post "+cart.getId());
		CartItem cartItem = this.cartItemService.findByCartIdAndBookId(cart.getId(), idProduct);
		if (cartItem != null) {
			Integer soluong = cartItem.getQuantity() + quantity;
			cartItem.setQuantity(soluong);
		} else {
			cartItem = new CartItem();
			cartItem.setCart(cart);
			cartItem.setBook(this.bookService.findById(idProduct));
			cartItem.setQuantity(quantity);
		}
		this.cartItemService.createOrUpdate(cartItem);
		Integer soluong = this.cartItemService.sumQuantityByCartId(cart.getId());
		session.setAttribute("soluong", soluong);
		return "redirect:/cart";
	}

	@GetMapping("delete-cart-item/{id}")
	public String delete(Model model, @PathVariable("id") Integer id, HttpSession session) {
		if (this.cartItemService.delete(id)) {
			return "redirect:/cart";
		}
		return "redirect:/cart";
	}
}
