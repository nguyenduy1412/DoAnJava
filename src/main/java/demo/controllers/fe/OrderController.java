package demo.controllers.fe;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.models.Cart;
import demo.models.CartItem;
import demo.models.CustomUserDetails;
import demo.models.OrderDetail;
import demo.models.Orders;
import demo.models.User;
import demo.services.BookService;
import demo.services.CartItemService;
import demo.services.CartService;
import demo.services.OrderDetailService;
import demo.services.OrderService;
import demo.services.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private CartService cartService;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private BookService bookService;
	@Autowired
	private UserService userService;
	@RequestMapping("/checkout")
	public String checkout(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");

		if (user == null) {
			return "redirect:/login";
		}
		Cart cart = cartService.findByUserId(user.getId());
		model.addAttribute("totalPrice", cart.totalPrice());
		Orders order = new Orders();
		order.setUser(user);
		order.setDateOrder(new Date());
		order.setStatus(0);
		order.setSumMoney((cart.totalPrice().longValue()));
		model.addAttribute("order", order);
		List<CartItem> listCartItem = this.cartItemService.findByCartId(cart.getId());
		model.addAttribute("listCartItem", listCartItem);
		return "checkout";
	}

	@RequestMapping("/postCheckout")
	public String postCheckout(HttpSession session, @ModelAttribute("order") Orders orders) {
		User user = (User) session.getAttribute("user");
		
		if (user == null) {
			return "redirect:/login";
		}
		user.setFullName(orders.getUser().getFullName());
		System.out.println("FullName "+orders.getUser().getFullName());
		this.userService.update(user);
		session.setAttribute("user", user);
		Cart cart = cartService.findByUserId(user.getId());
		orders.setUser(user);
		orders.setDateOrder(new Date());
		orders.setStatus(0);
		orders.setSumMoney((cart.totalPrice().longValue()));
		if (this.orderService.create(orders)) {
			// thêm tất cả các sản phẩm trong giỏ hàng vào order detail
			for (CartItem a : cart.getCartItems()) {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrders(orders);
				orderDetail.setPrice(a.getBook().getPrice());
				orderDetail.setQuantity(a.getQuantity());
				orderDetail.setBook(a.getBook());
				this.orderDetailService.create(orderDetail);
			}
			// xóa tất cả sản phẩm trong giỏ hàng
			this.cartItemService.deleteByCartId(cart.getId());
		}
		return "redirect:/";
	}

	@RequestMapping("/order-detail/{id}")
	public String orderDetail(Model model, @PathVariable("id") Integer id) {
		Orders order = this.orderService.findByOrder(id);
		model.addAttribute("order", order);
		return "order-detail";
	}
	@RequestMapping("/cancel/{id}")
	public String cancelOrder(@PathVariable("id") Integer id) {
		if(  this.orderDetailService.deleteByOrdersId(id) && this.orderService.delete(id) )
		{
			return "redirect:/myacount";
		}
		return "order-detail";
	}
}
