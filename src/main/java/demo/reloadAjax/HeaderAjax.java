package demo.reloadAjax;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.models.Book;
import demo.models.Cart;
import demo.models.CartItem;
import demo.models.CustomUserDetails;
import demo.services.BookService;
import demo.services.CartItemService;
import demo.services.CartService;
import jakarta.servlet.http.HttpSession;

@Controller
public class HeaderAjax {
	@Autowired
	private CartService cartService;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private BookService bookService;

	@RequestMapping("/reloadCart")
	public String reload(Principal principal, HttpSession session) {
		Double total = null;
		Integer soluong = null;
		try {
			CustomUserDetails userCus = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Cart cart = cartService.findByUserId(userCus.getUser().getId());
			// nếu có cart thì lấy số lượng và total + listItem bắn sang index
			if (cart != null) {
				soluong = this.cartItemService.sumQuantityByCartId(cart.getId());
				total = cart.totalPrice();
				List<CartItem> listItem = this.cartItemService.findByCartIdOrderByIdDesc(cart.getId());
				session.setAttribute("listItem", listItem);
				session.setAttribute("cartId", cart.getId());
			}
		} catch (Exception e) {

		}
		soluong = (soluong == null) ? 0 : soluong;
		total = (total == null) ? 0.0 : total;
		session.setAttribute("soluong", soluong);
		session.setAttribute("total", total);
		return "layout/reloadCart";
	}

	@RequestMapping("/reloadTotal")
	public String reloadTotal() {
		return "layout/reloadTotal";
	}

	@RequestMapping("/reloadCartItem")
	public String reloadItem() {
		return "layout/reloadCartItem";
	}
	@RequestMapping("/api/book/view/{id}")
	public String showBook(@PathVariable("id") Integer id,Model model) {
		Book book=this.bookService.findById(id);
		model.addAttribute("book", book);
		return "layout/xemSanPham";
	}
}
