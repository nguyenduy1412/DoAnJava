package demo.controllers.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo.models.Category;
import demo.models.DiscountCode;
import demo.models.Notification;
import demo.models.Orders;
import demo.models.RequiredCancel;
import demo.models.User;
import demo.services.BookService;
import demo.services.CartItemService;
import demo.services.CartService;
import demo.services.DiscountCodeService;
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
	@Autowired
	private DiscountCodeService discountCodeService;

	@GetMapping("/order")
	public String order(Model model, @Param("keyword") String keyword,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "date", defaultValue = "0") String date,
			@RequestParam(name = "allday", defaultValue = "1") Integer allday,
			@RequestParam(name = "status", defaultValue = "6") Integer status) {
//		List<Orders> list = this.orderService.getAllOrderByIdDesc();

		int day = 0, month = 0, year = 0;
		Date now = new Date();
		// lấy tất cả đơn hàng theo ngày tháng năm nếu không lựa chọn ngày thì giá trị
		// mặc định là 0
		// và gắn là ngày hiện tại
		if (date.equals("0")) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			day = cal.get(Calendar.DAY_OF_MONTH);
			month = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH trả về giá trị từ 0-11, nên cần +1
			year = cal.get(Calendar.YEAR);

		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				now = sdf.parse(date);
				Calendar cal = Calendar.getInstance();
				cal.setTime(now);
				day = cal.get(Calendar.DAY_OF_MONTH);
				month = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH trả về giá trị từ 0-11, nên cần +1
				year = cal.get(Calendar.YEAR);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		Page<Orders> list;
		// nếu chọn theo ngày thì lấy list order theo ngày ngược lại thì lấy tất cả danh
		// sách order
		if (allday == 1) {
			list = this.orderService.findByDayMonthYearAndStatus(day, month, year, page, 10, status);
		} else {
			list = this.orderService.findByStatusOrderByIdDesc(status, page, 10);
		}
		model.addAttribute("listOrder", list);

		model.addAttribute("date", now);
		model.addAttribute("dates", date);
		model.addAttribute("status", status);
		model.addAttribute("allday", allday);
		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage",page);
		return "admin/order/index";
	}

	@GetMapping("edit-order/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Orders order = this.orderService.findById(id);
		model.addAttribute("order", order);

		return "admin/order/detailorder";
	}

	@PostMapping("/accept-cancel")
	public String accept(@RequestParam("id") Integer id, HttpSession session) {
		try {
			Orders order = this.orderService.findById(id);
			RequiredCancel requiredCancel = order.getRequiredCancel();
			requiredCancel.setRequired(true);
			if (this.requiredCancelService.create(requiredCancel)) {
				order.setStatus(5);
				this.orderService.create(order);
				String message = "Hủy đơn hàng " + order.getId() + " thành công";
				Notification notification = new Notification();
				notification.setDate(new Date());
				notification.setMessage(message);
				// khôi phục mã giảm giá

				if (order.getDiscountCode() != null) {
					DiscountCode disc = order.getDiscountCode();
					disc.setQuantity(disc.getQuantity() + 1);
					if (this.discountCodeService.create(disc)) {
						System.out.println("Thành công");
					}
				}
				notification.setUser(order.getUser());
				this.notificationService.create(notification);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "redirect:/admin/order";
	}

	@PostMapping("/denial-cancel")
	public String denial(@RequestParam("id") Integer id, HttpSession session) {
		try {
			Orders order = this.orderService.findById(id);
			RequiredCancel requiredCancel = order.getRequiredCancel();
			requiredCancel.setRequired(true);
			if (this.requiredCancelService.create(requiredCancel)) {
				order.setStatus(1);
				this.orderService.create(order);
				String message = "Người bán hàng đã từ chối yêu cầu hủy đơn hàng " + order.getId();
				Notification notification = new Notification();
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
