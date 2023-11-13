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
import demo.models.Publicsher;
import demo.services.AuthorService;
import demo.services.BannerService;
import demo.services.PublicsherService;
import demo.services.StorageService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class PublicsherController {
	@Autowired
	private PublicsherService publicsherService;
	

	@GetMapping("/publicsher")
	public String index(Model model) {
		List<Publicsher> list = this.publicsherService.getAll();
		model.addAttribute("list", list);
		
		return "admin/publicsher/index";

	}

	@GetMapping("/add-publicsher")
	public String add(Model model) {
		Publicsher publicsher = new Publicsher();

		model.addAttribute("publicsher", publicsher);
		return "admin/publicsher/add";
	}

	@PostMapping("/add-publicsher")
	public String save(Model model, @ModelAttribute("publicsher") Publicsher publicsher) {
		
		if (this.publicsherService.create(publicsher)) {
			return "redirect:/admin/publicsher";
		}
		return "admin/publicsher/add";

	}

	@GetMapping("/edit-publicsher/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Publicsher publicsher = this.publicsherService.findById(id);
		model.addAttribute("publicsher", publicsher);

		return "admin/publicsher/edit";
	}

	@PostMapping("/publicsher-edit")
	public String update(Model model, @ModelAttribute("publicsher") Publicsher publicsher) {
		
		if (this.publicsherService.create(publicsher)) {
			return "redirect:/admin/publicsher";
		}
		return "admin/publicsher/edit";

	}
	 @GetMapping("delete-publicsher/{id}")
	    public String delete(Model model,@PathVariable("id") Integer id) {
	    	if(this.publicsherService.delete(id)) {
	    		return "redirect:/admin/publicsher";
	    	}
	    	return "redirect:/admin/publicsher";
	    }
}
