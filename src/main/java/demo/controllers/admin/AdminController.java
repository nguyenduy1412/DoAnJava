package demo.controllers.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import demo.modelApi.RevenueDto;
import demo.models.Banner;
import demo.models.Category;
import demo.models.CustomUserDetails;
import demo.models.OrderDetail;
import demo.models.Orders;
import demo.models.User;
import demo.services.CategoryService;
import demo.services.CustomUserDetailService;
import demo.services.OrderService;
import demo.services.StorageService;
import demo.services.UserService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.Year;
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private StorageService storageService;
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private OrderService orderService;
	
	@GetMapping
	public String index(HttpSession session) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		System.out.println("Time: "+currentDateTime.toString());
		try {
			User user=(User) session.getAttribute("user");
			if(user ==null)
			{
//				user=new User();
//				session.setAttribute("user", user);
//				return "redirect:/login";
//				CustomUserDetails userCus =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//				user=userCus.getUser();
//				session.setAttribute("user", user);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
//			User user=new User();
//			session.setAttribute("user", user);
		}
		
		return "redirect:/admin/";
	}
	@RequestMapping("/")
	public String admin(Model model) {
	
		int currentYear = Year.now().getValue();
		List<RevenueDto> listReve=new ArrayList<RevenueDto>();
		String dataRevenue="";
		for(int i=1;i<=12;i++) {
			List<Orders> listOrder=this.orderService.findByMonthAndYear(i, currentYear);
			long totalMoneyOrder=0;
			Integer quantity=0;
			
			for (Orders orders : listOrder) {
				for (OrderDetail a : orders.getOrderDetails()) {
					quantity+=a.getQuantity();
				}
				if(orders.getStatus()==3)
				{
					totalMoneyOrder+=orders.getSumMoney();
				}
			}
			
			RevenueDto a=new RevenueDto();
			a.setMoney(totalMoneyOrder);
			a.setQuatity(quantity);
			a.setMonth(i);
			a.setYear(currentYear);
			if(i<12)
			{
				dataRevenue+=totalMoneyOrder+",";
			}
			else {
				dataRevenue+=totalMoneyOrder;
			}
			listReve.add(a);
		}
		int day = 0, month = 0, year = 0;
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH trả về giá trị từ 0-11, nên cần +1
		year = cal.get(Calendar.YEAR);
		List<Orders> list=this.orderService.findByDayMonthAndYear(day, month, year);
		int sumOrder=list.size();
		System.out.println("Data"+dataRevenue);
		model.addAttribute("sumOrder", sumOrder);
		model.addAttribute("dataRevenue", dataRevenue);
		return "admin/statistical/index";
	}
	@RequestMapping("/profile")
	public String profile(Model model,HttpSession session) {
		
		User user=(User) session.getAttribute("user");
		Date birthday=user.getBirthday();
		model.addAttribute("birthday",birthday);
		model.addAttribute("user", user);
		System.out.println(user);
		return "admin/account/profile";
	}
	@PostMapping("/profile-edit")
	public String update(Model model, @ModelAttribute("user") User user,
		@RequestParam("fileImage") MultipartFile file,@RequestParam("date1") String date1,HttpSession session) {
		String fileName = file.getOriginalFilename();
		boolean isEmpty = fileName == null || fileName.trim().length() == 0;
		System.out.println("bớt đây+"+date1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
			Date parsedDate = sdf.parse(date1);
			user.setBirthday(parsedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!isEmpty) {
			this.storageService.store(file);
			user.setImg(fileName);
		}
		if (this.userService.update(user)!=null) {
			session.setAttribute("user", user);
			return "redirect:/admin";
		}
		return "admin/profile";

	}
}
