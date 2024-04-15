package demo.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.models.Banner;
import demo.models.Book;
import demo.models.Cart;
import demo.models.CartItem;
import demo.models.Category;
import demo.models.CustomUserDetails;
import demo.models.User;
import demo.services.BannerService;
import demo.services.BookService;
import demo.services.CartItemService;
import demo.services.CartService;
import demo.services.CategoryService;
import demo.services.OrderDetailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
@Controller
public class HomeController {
	@Autowired
	BannerService bannerService;
	@Autowired
	private BookService bookService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CartService cartService;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private OrderDetailService orderDetailService;
	@RequestMapping("/")
	public String index(Model model,HttpServletRequest request,Principal principal,HttpSession session) {
		List<Banner> listBanners = this.bannerService.getAll();
		System.out.println("Alo1");
		model.addAttribute("listBanners", listBanners);
		List<Book> bookSale=this.bookService.findBookSale();
		System.out.println("Alo1");
		List<List<Book>> listBig = new ArrayList();
		for (int i = 1; i <6 ; i++) {
			//có 2 sản phẩm
			Page<Book> bookNew = this.bookService.listBookNew(i,2);
			System.out.println("Uaaa");
			List<Book> bookList = bookNew.getContent();
			listBig.add(bookList);
			System.out.println("Uaaa111");
		}
		System.out.println("Alo");
		for (List<Book> list : listBig) {
			System.out.println(list);
		}
		List<Category> listCate=this.categoryService.getAll();
		model.addAttribute("listBig", listBig);
		model.addAttribute("bookSale", bookSale);
		model.addAttribute("listCate", listCate);
		List<Book> listBook=this.orderDetailService.findBookTrend();
		
		//tạo 2 list con
		List<Book> listTrend1=new ArrayList<Book>();
		List<Book> listTrend2=new ArrayList<Book>();
		for(int i=0;i<listBook.size();i++) {
			if(i<3) {
				listTrend1.add(listBook.get(i));
			}
			else {
				listTrend2.add(listBook.get(i));
			}
		}
		model.addAttribute("listTrend1", listTrend1);
		model.addAttribute("listTrend2", listTrend2);
		Long total=null;
		Integer soluong=null;
		
		try {
			User user=(User) session.getAttribute("user");
			if(user==null) {
				CustomUserDetails userCus = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				user=userCus.getUser();
			}
			session.setAttribute("user", user);
			Cart cart=cartService.findByUserId(user.getId());
			// nếu có cart thì lấy số lượng và total + listItem bắn sang index
			if(cart!=null) {
				soluong= this.cartItemService.sumQuantityByCartId(cart.getId()) ;
				total=cart.totalPrice();
				List<CartItem> listItem=this.cartItemService.findByCartIdOrderByIdDesc(cart.getId());	
				session.setAttribute("listItem", listItem);
				session.setAttribute("cartId", cart.getId());
			}
		} catch (Exception e) {
			
		}
		soluong= (soluong==null)? 0 : soluong;
		session.setAttribute("soluong", soluong);
		session.setAttribute("total", total);
		
		return "index";
	}
}
