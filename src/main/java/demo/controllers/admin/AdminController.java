package demo.controllers.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import demo.models.Banner;
import demo.models.Category;
import demo.models.CustomUserDetails;
import demo.models.User;
import demo.services.CategoryService;
import demo.services.CustomUserDetailService;
import demo.services.StorageService;
import demo.services.UserService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private StorageService storageService;
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;
	@GetMapping
	public String index(HttpSession session) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		System.out.println("Time: "+currentDateTime.toString());
		try {
			User user=(User) session.getAttribute("user");
			if(user ==null)
			{
				CustomUserDetails userCus =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				user=userCus.getUser();
				session.setAttribute("user", user);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			User user=new User();
			session.setAttribute("user", user);
		}
		
		return "redirect:/admin/";
	}
	@RequestMapping("/")
	public String admin(Model model,@Param("keyword") String keyword,
    		@RequestParam(name="page",defaultValue = "1") Integer page) {
	
		Page<Category> list=this.categoryService.getAll(page);
		if(keyword !=null) {
			list=this.categoryService.searchCategory(keyword,page);
			model.addAttribute("keyword",keyword);
		}
		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage",page);
		model.addAttribute("list",list);
		return "admin/index1";
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
