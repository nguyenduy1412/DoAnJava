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

import demo.method.FileName;
import demo.models.Banner;
import demo.models.Category;

import demo.services.BannerService;
import demo.services.StorageService;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/admin")
public class BannerController {
	@Autowired
	private BannerService bannerService;
	@Autowired
	private StorageService storageService;

	@GetMapping("/banner")
	public String index(Model model) {
		List<Banner> list = this.bannerService.getAll();
		model.addAttribute("list", list);
		model.addAttribute("select1", true);
		return "admin/banner/index";

	}

	@GetMapping("/add-banner")
	public String add(Model model) {
		Banner banner = new Banner();
		banner.setStatus(true);
		model.addAttribute("banner", banner);
		return "admin/banner/add";
	}
	 @PostMapping("/add-banner")
	    public String save(Model model, @Valid @ModelAttribute("banner") Banner banner,BindingResult bindingResult  ,@RequestParam("fileImage") MultipartFile file) {
			 if(bindingResult.hasErrors()) {
				 System.out.println("ERORRR");
				 return "admin/banner/add";
			 }
			 String fileName=FileName.getFileNameToDateNow();
		 	this.storageService.store(file,fileName);
			
			 banner.setImg(fileName);
			 if(this.bannerService.create(banner)) {
				 return "redirect:/admin/banner";
			 }
			 return "admin/banner/add";
	    	
	    }
	 @GetMapping("delete-banner/{id}")
    public String delete(Model model,@PathVariable("id") Integer id) {
    	if(this.bannerService.delete(id)) {
    		return "redirect:/admin/banner";
    	}
    	return "redirect:/admin/banner";
    }

	@GetMapping("/edit-banner/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Banner banner = this.bannerService.findById(id);
		model.addAttribute("banner", banner);
		
		return "admin/banner/edit";
	}

	@PostMapping("/banner-edit")
	public String update(Model model, @ModelAttribute("banner") Banner banner,
		@RequestParam("fileImage") MultipartFile file) {
		String fileName = file.getOriginalFilename();
		boolean isEmpty = fileName == null || fileName.trim().length() == 0;
		if (!isEmpty) {
			fileName=FileName.getFileNameToDateNow();
			this.storageService.store(file,fileName);
			banner.setImg(fileName);
		}
		if (this.bannerService.update(banner)) {
			return "redirect:/admin/banner";
		}
		return "admin/banner/edit";

	}
}
