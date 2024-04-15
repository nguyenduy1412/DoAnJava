package demo.controllers.admin;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo.models.Category;
import demo.models.Notification;
import demo.models.Orders;
import demo.models.RequiredCancel;
import demo.models.User;
import demo.services.BookService;
import demo.services.CartItemService;
import demo.services.CartService;
import demo.services.NotificationService;
import demo.services.OrderDetailService;
import demo.services.OrderService;
import demo.services.RequiredCancelService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class OrderAdminController {
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
	private RequiredCancelService requiredCancelService;
	@Autowired
	private NotificationService notificationService;
	@GetMapping("/order")
	public String order(Model model) {
		List<Orders> list = this.orderService.getAllOrderByIdDesc();
		model.addAttribute("listOrder", list);
		return "admin/order/index";
	}

	@GetMapping("edit-order/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Orders order = this.orderService.findById(id);
		model.addAttribute("order", order);

		return "admin/order/detailorder";
	}
	@PostMapping("/accept-cancel")
	public String accept(@RequestParam("id") Integer id,HttpSession session) {
		try {
			Orders order=this.orderService.findById(id);
			RequiredCancel requiredCancel= order.getRequiredCancel();
			requiredCancel.setRequired(true);
			if(this.requiredCancelService.create(requiredCancel)) {
				order.setStatus(5);
				this.orderService.create(order);
				String message="Hủy đơn hàng "+order.getId()+" thành công";
				Notification notification=new Notification();
				notification.setDate(new Date());
				notification.setMessage(message);
				
				notification.setUser(order.getUser());
				this.notificationService.create(notification);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return "redirect:/admin/order";
	}
	@PostMapping("/denial-cancel")
	public String denial(@RequestParam("id") Integer id,HttpSession session) {
		try {
			Orders order=this.orderService.findById(id);
			RequiredCancel requiredCancel= order.getRequiredCancel();
			requiredCancel.setRequired(true);
			if(this.requiredCancelService.create(requiredCancel)) {
				order.setStatus(1);
				this.orderService.create(order);
				String message="Người bán hàng đã từ chối yêu cầu hủy đơn hàng "+order.getId();
				Notification notification=new Notification();
				notification.setDate(new Date());
				notification.setMessage(message);
				
				notification.setUser(order.getUser());
				this.notificationService.create(notification);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/admin/order";
	}
}
