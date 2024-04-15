package demo.controllers.fe;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.models.Cart;
import demo.models.CartItem;

import demo.models.MethodPay;
import demo.models.Notification;
import demo.models.OrderDetail;
import demo.models.Orders;
import demo.models.RequiredCancel;
import demo.models.Review;
import demo.models.User;
import demo.services.CartItemService;
import demo.services.CartService;
import demo.services.MethodPayService;
import demo.services.NotificationService;
import demo.services.OrderDetailService;
import demo.services.OrderService;
import demo.services.RequiredCancelService;
import demo.services.ReviewService;
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
	private UserService userService;
	@Autowired
	private MethodPayService methodPayService;
	@Autowired
	private RequiredCancelService requiredCancelService; 
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private ReviewService reviewService;
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
		
		
		List<CartItem> listCartItem = this.cartItemService.findByCartId(cart.getId());
		model.addAttribute("listCartItem", listCartItem);
		MethodPay pay=new MethodPay();
		List<MethodPay> listMethodPays=this.methodPayService.getAll();
		pay=listMethodPays.get(0);
		order.setMethodPay(pay);
		model.addAttribute("listPay", listMethodPays);
		model.addAttribute("order", order);
		System.out.println(order);
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
			// thêm tất cả các sản phẩm trong giỏ hàng vào order detail và thêm các review vào orderdetail
			for (CartItem a : cart.getCartItems()) {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrders(orders);
				orderDetail.setPrice(a.getBook().getPriceSale());
				orderDetail.setQuantity(a.getQuantity());
				orderDetail.setBook(a.getBook());
				Review review=new Review();
				review.setStatus(false);
				this.reviewService.create(review);
				orderDetail.setReview(review);
				this.orderDetailService.create(orderDetail);
			}
			// xóa tất cả sản phẩm trong giỏ hàng
			this.cartItemService.deleteByCartId(cart.getId());
		}
		System.out.println(orders);
		return "redirect:/";
	}

	@RequestMapping("/order-detail/{id}")
	public String orderDetail(Model model, @PathVariable("id") Integer id) {
		Orders order = this.orderService.findById(id);
		model.addAttribute("order", order);
		return "order-detail";
	}
	@RequestMapping("/cancel/{id}")
	public String cancelOrder(@PathVariable("id") Integer id,HttpSession session) {
		Orders order=this.orderService.findById(id);
		RequiredCancel required=new RequiredCancel();
		required.setDateCancel(new Date());
		required.setRequired(true);
		User user = (User) session.getAttribute("user");
		required.setCanceller(user.getUserName());
		if(this.requiredCancelService.create(required))
		{
			String message="Bạn đã hủy đơn hàng có mã: "+id;
			Notification notification=new Notification();
			notification.setDate(new Date());
			notification.setMessage(message);
			
			notification.setUser(order.getUser());
			this.notificationService.create(notification);
			System.out.println("Álo");
			order.setRequiredCancel(required);
			if(order.getStatus()==0)
			{
				order.setStatus(5);
			}
			else
			{
				order.setStatus(4);
			}
			if( this.orderService.create(order))
			{
				return "redirect:/myacount";
			}
		}
		
		return "redirect:/myacount";
	}
	
}
