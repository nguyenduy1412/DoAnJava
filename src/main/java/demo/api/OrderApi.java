package demo.api;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import demo.models.Book;
import demo.models.Cart;
import demo.models.CartItem;
import demo.models.Notification;
import demo.models.OrderDetail;
import demo.models.Orders;
import demo.models.RequiredCancel;
import demo.models.User;
import demo.models.WareHouse;
import demo.services.BookService;
import demo.services.CartItemService;
import demo.services.CartService;
import demo.services.EmailService;
import demo.services.NotificationService;
import demo.services.OrderDetailService;
import demo.services.OrderService;
import demo.services.RequiredCancelService;
import demo.services.UserService;
import demo.services.WareHouseService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.Delegate;

@RestController
@RequestMapping("/api/order")
public class OrderApi {
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private BookService bookService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private RequiredCancelService requiredCancelService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private WareHouseService wareHouseService;
	@GetMapping()
	public List<Orders> list() {
		return this.orderService.getAll();
	}

	@PutMapping("/{id}")
	Orders update(@PathVariable("id") Integer id, @RequestBody Orders order,HttpSession session) {
		
		Orders orderOld = this.orderService.findById(id);
		User user = (User) session.getAttribute("user");
		orderOld.setStatus(order.getStatus());
		
		String status;
		if(order.getStatus()==1) {
			status="Chờ lấy hàng";
		}
		else if(order.getStatus()==2) {
			status="Đang giao";
		}
		else if(order.getStatus()==0) {
			status="Chờ xác nhận";
		}
		else if(order.getStatus()==3) {
			// giao hàng thành công thì - số lượng trong kho
			status="Giao thành công";
			for (OrderDetail a : orderOld.getOrderDetails()) {
				WareHouse wareHouse=this.wareHouseService.findByBookId(a.getBook().getId());
				
				Integer soLuongKho=wareHouse.getQuantity()-a.getQuantity();
				if(soLuongKho>=0) {
					wareHouse.setQuantity(soLuongKho);
					wareHouse.setSold(wareHouse.getQuantityEnter()-soLuongKho);
					this.wareHouseService.create(wareHouse);
				}
				else {
					System.out.println("Loi");
				}
			}
		}
		else if(order.getStatus()==4) {
			status="Đang xử lý";
		}
		else {
			status="Đã hủy";
			try {
				RequiredCancel requiredCancel= orderOld.getRequiredCancel();
				if(requiredCancel==null) {
					requiredCancel=new RequiredCancel();
				}
				requiredCancel.setDateCancel(new Date());
				requiredCancel.setCanceller(user.getUserName());
				this.requiredCancelService.create(requiredCancel);
				orderOld.setRequiredCancel(requiredCancel);
				String message="Người bán hàng đã hủy đơn hàng "+orderOld.getId();
				Notification notification=new Notification();
				notification.setDate(new Date());
				notification.setMessage(message);
				
				notification.setUser(orderOld.getUser());
				this.notificationService.create(notification);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		Context context = new Context();
		context.setVariable("name", orderOld.getUser().getFullName());
		context.setVariable("id",id );
		context.setVariable("ngaydat",orderOld.getDateOrder() );
		context.setVariable("tongtien",orderOld.getSumMoney() );
		context.setVariable("status",status );
		// gửi mail
//		try {
//			emailService.sendMail(orderOld.getUser().getEmail(), "Thông báo đơn hàng", "mailOrder", context);
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println("trang thai "+orderOld.getStatus());
		return this.orderService.update(orderOld);
	}

	@GetMapping("/{id}")
	Orders find(@PathVariable("id") Integer id) {
		return this.orderService.findById(id);
	}
	@GetMapping("/status")
	Page<Orders> getOrderByStatus(@RequestParam("userId") long userId,@RequestParam("status") Integer status,
			@RequestParam("page") Integer page) {
		if(status==4){
			return this.orderService.getByUserId(userId, page, 4);
		}
		return this.orderService.getByStatusAndUserIdOrderByIdDesc(status, userId,page,4);
	}


	/*
	 * @GetMapping("/userId/{id}") List<Orders> getOrderByUserId(@PathVariable("id")
	 * long id) {
	 * 
	 * User user = this.userService.findById(id); return
	 * this.orderService.getByUserOrderByIdDesc(user); }
	 */

	@PostMapping("/checkout/{id}")
	public ResponseEntity<String> addOrder(@PathVariable("id") long id, @RequestBody Orders order) {
		User user = this.userService.findById(id);
		Cart cart = this.cartService.findByUserId(id);
		order.setUser(user);
		order.setDateOrder(new Date());
		order.setStatus(0);
		order.setSumMoney((cart.totalPrice().longValue()));
		if (this.orderService.create(order)) {
			// thêm tất cả các sản phẩm trong giỏ hàng vào order detail
			for (CartItem a : cart.getCartItems()) {
				OrderDetail orderDetail = new OrderDetail();
				
				orderDetail.setOrders(order);
				orderDetail.setPrice(a.getBook().getPriceSale());
				orderDetail.setQuantity(a.getQuantity());
				orderDetail.setBook(a.getBook());
				this.orderDetailService.create(orderDetail);
			}
			// xóa tất cả sản phẩm trong giỏ hàng
			this.cartItemService.deleteByCartId(cart.getId());
		}
		return new ResponseEntity<>("Xóa đối tượng thành công", HttpStatus.OK);
	}

	@PostMapping("/checkout")
	public ResponseEntity<String> addOrderBookId(@RequestParam("userId") long userId,
			@RequestParam("bookId") Integer bookId, @RequestBody Orders order) {
		User user = this.userService.findById(userId);
		Book book =this.bookService.findById(bookId);
		order.setUser(user);
		order.setDateOrder(new Date());
		order.setStatus(0);
		order.setSumMoney(book.getPriceSale());
		if(this.orderService.create(order)) {
			OrderDetail orderDetail = new OrderDetail();
			
			orderDetail.setOrders(order);
			orderDetail.setPrice(book.getPriceSale());
			orderDetail.setQuantity(1);
			orderDetail.setBook(book);
			if(this.orderDetailService.create(orderDetail)) {
				return new ResponseEntity<>("thành công", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Thêm thất bại", HttpStatus.BAD_REQUEST);
	}
	@DeleteMapping("/cancel/{id}")
	public ResponseEntity<String> cancelOrder(@PathVariable("id") Integer id) {
		if(  this.orderDetailService.deleteByOrdersId(id) && this.orderService.delete(id) )
		{
			return new ResponseEntity<>("thành công", HttpStatus.OK);
		}
		return new ResponseEntity<>("Thêm thất bại", HttpStatus.BAD_REQUEST);
	}
	
}
