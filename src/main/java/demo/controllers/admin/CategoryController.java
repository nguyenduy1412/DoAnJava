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
import demo.services.CategoryService;
import demo.services.StorageService;

@Controller
@RequestMapping("/admin")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private StorageService storageService;
	@GetMapping("/category")
    public String index(Model model,@Param("keyword") String keyword,
    		@RequestParam(name="page",defaultValue = "1") Integer page) {
		System.out.println(keyword);
		Page<Category> list=this.categoryService.getAll(page);
		if(keyword !=null) {
			list=this.categoryService.searchCategory(keyword,page);
			model.addAttribute("keyword",keyword);
		}
		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage",page);
		model.addAttribute("list",list);		
        return "admin/category/index";
    }
    @GetMapping("/add-category")
    public String add(Model model) {
    	Category category=new Category();
    	category.setCategoryStatus(true);
    	model.addAttribute("category", category);
    	return "admin/category/add";
    }
    @PostMapping("/add-category")
    public String save(Model model,@ModelAttribute("category") Category category,@RequestParam("fileImage") MultipartFile file) {
    	this.storageService.store(file);
		String fileName = file.getOriginalFilename();
		category.setImg(fileName);
    	if(this.categoryService.create(category)) {
    		return "redirect:/admin/category";
    	}
    	else {
    		return "admin/category/add";
    	}
    	
    }
    @GetMapping("edit-category/{id}")
    public String edit(Model model,@PathVariable("id") Integer id) {
    	Category category =this.categoryService.findById(id);
    	model.addAttribute("category",category);
    	return "admin/category/edit";
    }
    @PostMapping("edit-category")
    public String update(@ModelAttribute("category") Category category,@RequestParam("fileImage") MultipartFile file) {
    	String fileName = file.getOriginalFilename();
		boolean isEmpty = fileName == null || fileName.trim().length() == 0;
		if (!isEmpty) {
			this.storageService.store(file);
			category.setImg(fileName);
		}
    	if(this.categoryService.create(category)) {
    		return "redirect:/admin/category";
    	}
    	return "admin/category/edit";
    }
    @GetMapping("delete-category/{id}")
    public String delete(Model model,@PathVariable("id") Integer id) {
    	if(this.categoryService.delete(id)) {
    		return "redirect:/admin/category";
    	}
    	return "redirect:/admin/category";
    }
}
