package demo.controllers.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import demo.models.Author;
import demo.models.Banner;
import demo.services.AuthorService;
import demo.services.BannerService;
import demo.services.StorageService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AuthorController {
	@Autowired
	private AuthorService authorService;
	@Autowired
	private StorageService storageService;

	@GetMapping("/author")
	public String index(Model model) {
		List<Author> list = this.authorService.getAll();
		model.addAttribute("list", list);
		
		return "admin/author/index";

	}

	@GetMapping("/add-author")
	public String add(Model model) {
		Author author = new Author();

		model.addAttribute("author", author);
		return "admin/author/add";
	}

	@PostMapping("/add-author")
	public String save(Model model,@ModelAttribute("author") Author author,
			@RequestParam("fileImage") MultipartFile file) {
		this.storageService.store(file);
		String fileName = file.getOriginalFilename();
		author.setImg(fileName);
		if (this.authorService.create(author)) {
			return "redirect:/admin/author";
		}
		return "admin/author/add";

	}

	@GetMapping("/edit-author/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Author author = this.authorService.findById(id);
		model.addAttribute("author", author);

		return "admin/author/edit";
	}

	@PostMapping("/author-edit")
	public String update(Model model, @ModelAttribute("author") Author author,
			@RequestParam("fileImage") MultipartFile file) {
		String fileName = file.getOriginalFilename();
		boolean isEmpty = fileName == null || fileName.trim().length() == 0;
		if (!isEmpty) {
			this.storageService.store(file);
			author.setImg(fileName);
		}
		if (this.authorService.create(author)) {
			return "redirect:/admin/author";
		}
		return "admin/author/edit";

	}
	 @GetMapping("delete-author/{id}")
	    public String delete(Model model,@PathVariable("id") Integer id) {
	    	if(this.authorService.delete(id)) {
	    		return "redirect:/admin/author";
	    	}
	    	return "redirect:/admin/author";
	    }
}
