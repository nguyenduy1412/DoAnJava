package demo.controllers.fe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import demo.models.CustomUserDetails;
import demo.models.Notification;
import demo.models.Orders;
import demo.models.User;
import demo.services.NotificationService;
import demo.services.OrderService;
import demo.services.StorageService;
import demo.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class CustomController {
	@Autowired
	private UserService userService;
	@Autowired
	private StorageService storageService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private NotificationService notificationService;
	@RequestMapping("/login")
	public String signin() {
		return "login";
	}
	@RequestMapping("/register")
	public String register(Model model) {
		User user=new User();
		model.addAttribute("user", user);
		return "register";
	}
	@PostMapping("/register")
	public String doRegister(@Valid @ModelAttribute("user") User user,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
            System.out.println("ERRORR");
            return "register";
        }
		System.out.println("giá trị "+ bindingResult.hasErrors());
		String pass=new BCryptPasswordEncoder().encode(user.getPassWord());
		user.setPassWord(pass);
		user.setEnabled(true);
		if(this.userService.update(user)!=null) {
			return "login";
		}
		return "register";
	}
	@GetMapping("/myacount")
	public String detailAccount(Model model,HttpSession session){
		
		User user=(User) session.getAttribute("user");
		if(user==null)
		{
			return "login";
		}
		Date birthday;
		try {
			birthday=user.getBirthday();
		} catch (Exception e) {
			birthday=null;
		}
		List<Orders> listOrder=this.orderService.getByUserIdOrderByIdDesc(user.getId());
		List<Notification> listNofi=this.notificationService.findByUserIdAndOrderByIdDesc(user.getId());
		model.addAttribute("listOrder",listOrder);
		model.addAttribute("birthday",birthday);
		model.addAttribute("user", user);
		model.addAttribute("listNofi", listNofi);
		return "account-detail";
	}
	@PostMapping("/myacount")
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
			return "redirect:/myacount";
		}
		return "redirect:/myacount";
	}
	@PostMapping("/updatePass")
	public String updatePass(HttpSession session,@RequestParam("oldPass") String oldPass,
			@RequestParam("newPass") String newPass,RedirectAttributes redirectAttrs) {
		User user=(User) session.getAttribute("user");
		boolean check=passwordEncoder.matches(oldPass, user.getPassWord());
		String messSuccess="Đổi mật khẩu thành công";
		String messError="Đổi mật khẩu thất bại";
		if(check) {
			user.setPassWord(passwordEncoder.encode(newPass));
			if(this.userService.update(user)!=null) {
				redirectAttrs.addFlashAttribute("messege", messSuccess);
				return "redirect:/myacount";
			}
			else {
				redirectAttrs.addFlashAttribute("messege",messError);
				return "redirect:/myacount";
			}
		}
		else {
			System.out.println("Sai mật khẩu");
		}
		return "redirect:/myacount";
	}
}
