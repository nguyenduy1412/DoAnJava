package demo.controllers.fe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.context.Context;

import demo.VNPayConfig;
import demo.modelApi.TransactionStatusDTO;
import demo.models.Cart;
import demo.models.CartItem;
import demo.models.DiscountCode;
import demo.models.MethodPay;
import demo.models.Notification;
import demo.models.OrderDetail;
import demo.models.Orders;
import demo.models.RequiredCancel;
import demo.models.Review;
import demo.models.User;
import demo.services.CartItemService;
import demo.services.CartService;
import demo.services.DiscountCodeService;
import demo.services.EmailService;
import demo.services.MethodPayService;
import demo.services.NotificationService;
import demo.services.OrderDetailService;
import demo.services.OrderService;
import demo.services.RequiredCancelService;
import demo.services.ReviewService;
import demo.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
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
	@Autowired
	private DiscountCodeService discountCodeService;
	@Autowired
	private EmailService emailService;
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
		List<DiscountCode> listDiscount=this.discountCodeService.getByQuantityGreaterThanEqual(1);
		model.addAttribute("listDiscount", listDiscount);
		System.out.println(order);
		return "checkout";
	}

	@RequestMapping("/postCheckout")
	public String postCheckout(HttpSession session, @ModelAttribute("order") Orders orders,
			@RequestParam("discount_code") String nameCode,Model model) {
		User user = (User) session.getAttribute("user");
		
		if (user == null) {
			return "redirect:/login";
		}
		System.out.println("---------------------->");
		System.out.println("Discoutn "+nameCode);
		DiscountCode discount=null;
		if(!nameCode.isBlank()) {
			discount=this.discountCodeService.findByNameCode(nameCode);
			if(discount!=null) {
				orders.setDiscountCode(discount);
			}
			System.out.println(discount);
		}
		user.setFullName(orders.getUser().getFullName());
		System.out.println("FullName "+orders.getUser().getFullName());
		this.userService.update(user);
		session.setAttribute("user", user);
		try {
			String regex="^(032|033|034|035|036|037|038|039|096|097|098|086|083|084|085|081|082|088|091|094|070|079|077|076|078|090|093|089|056|058|092|059|099)[0-9]{7}$";
			
			boolean isValid = Pattern.matches(regex, orders.getPhone());
			if(isValid)
			{
				
			}
			else
			{
				 model.addAttribute("errorMessage", "Số điện thoại không hợp lệ. Vui lòng nhập đúng định dạng.");
				return "redirect:/checkout";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		Cart cart = cartService.findByUserId(user.getId());
		orders.setUser(user);
		orders.setDateOrder(new Date());
		orders.setStatus(0);
		orders.setStatusPay(false);
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
			// trừ đi mã giảm giá
			if(discount!=null) {
				discount.setQuantity(discount.getQuantity()-1);
				this.discountCodeService.create(discount);
				System.out.println("Trư ma giam gia");
			}
			Context context = new Context();
			context.setVariable("name", orders.getUser().getFullName());
			context.setVariable("id",orders.getId() );
			context.setVariable("ngaydat",orders.getDateOrder() );
			context.setVariable("tongtien",orders.getSumMoney() );
			
			context.setVariable("status","Chờ xác nhận" );
			// gửi mail
			try {
				emailService.sendMail(orders.getUser().getEmail(), "Thông báo đơn hàng", "mailOrder", context);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// thông báo đơn hàng
			String message="Bạn đã đặt đơn hàng có mã: "+orders.getId();
			Notification notification=new Notification();
			notification.setDate(new Date());
			notification.setMessage(message);
			
			notification.setUser(orders.getUser());
			this.notificationService.create(notification);
		}
		if(orders.getMethodPay().getId()==1) {
			return "redirect:/";
		}
		else {
			session.setAttribute("orderId", orders.getId());
			return "redirect:/create_payment?amount="+orders.getSumMoney();
		}
//		return "checkout";
		
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
			if(order.getStatus()==0)
			{
				
				// khôi phục mã giảm giá sau khi hủy
				if(order.getDiscountCode()!=null) {
					DiscountCode disc=order.getDiscountCode();
					disc.setQuantity(disc.getQuantity()+1);
					if(this.discountCodeService.create(disc)) {
						System.out.println("Thành công");
					}
				}
				order.setStatus(5);
			}
			else
			{
				message="Bạn đã yêu cầu hủy đơn hàng có mã: "+id;
				order.setStatus(4);
			}
			Notification notification=new Notification();
			notification.setDate(new Date());
			notification.setMessage(message);
			
			notification.setUser(order.getUser());
			this.notificationService.create(notification);
	
			order.setRequiredCancel(required);
			if( this.orderService.create(order))
			{
				
				return "redirect:/myacount";
			}
		}
		
		return "redirect:/myacount";
	}
	@GetMapping("/vnp-return")
    public String handleVNPReturn(HttpServletRequest request,HttpSession session) {
        // Nhận dữ liệu trả về
        String vnp_Amount = request.getParameter("vnp_Amount");
        String vnp_BankCode = request.getParameter("vnp_BankCode");
        String vnp_OrderInfo = request.getParameter("vnp_OrderInfo");
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        
        TransactionStatusDTO transactionStatusDTO=new TransactionStatusDTO();
        
       if(vnp_ResponseCode.equals("00")) {
    	   transactionStatusDTO.setStatus("OK");
    	   transactionStatusDTO.setMessage("Success");
    	   transactionStatusDTO.setData("");
    	   Integer id = (Integer) session.getAttribute("orderId");
    	   Orders order=this.orderService.findById(id);
    	   order.setStatusPay(true);
    	   this.orderService.create(order);
    	   return "redirect:/myacount";
    	   
       }else {
    	   transactionStatusDTO.setStatus("No");
    	   transactionStatusDTO.setMessage("false");
    	   transactionStatusDTO.setData("");
       }
        return "fail";
    }
	@GetMapping("/create_payment")
	public RedirectView createPayment(HttpServletRequest req){
        long amount = Integer.parseInt(req.getParameter("amount"))*100L;
//        String bankCode = req.getParameter("bankCode");
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VNPayConfig.getIpAddress(req);

        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", VNPayConfig.getRandomNumber(8));
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang"+VNPayConfig.getRandomNumber(8));
        vnp_Params.put("vnp_OrderType", VNPayConfig.vnp_OrderType);
        //vnp_Params.put("vnp_BankCode",bankCode);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl",VNPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        
        String queryUrl = VNPayConfig.getPaymentURL(vnp_Params, true);
        String hashData= VNPayConfig.getPaymentURL(vnp_Params, false);
        String vnpSecureHash=VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData);
        
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
        System.out.println("Link"+paymentUrl);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", paymentUrl);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(paymentUrl);
        return redirectView;
	}
}
