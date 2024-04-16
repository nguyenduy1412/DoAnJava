package demo.controllers.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import demo.models.Category;
import demo.models.User;
import demo.services.CategoryService;
import demo.services.StorageService;
import demo.services.UserService;

@Controller
@RequestMapping("/admin")
public class AccountController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/account")
    public String index(Model model,@Param("keyword") String keyword,
    		@RequestParam(name="page",defaultValue = "1") Integer page) {
		System.out.println(keyword);
		Page<User> list=this.userService.getAll(page,10);
		if(keyword !=null) {
			list=this.userService.searchFullName(keyword,page);
			model.addAttribute("keyword",keyword);
		}
		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage",page);
		model.addAttribute("list",list);		
        return "admin/account/index";
    }
    
    
}
