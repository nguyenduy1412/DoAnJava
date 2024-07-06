package demo.controllers.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import demo.models.DiscountCode;
import demo.models.Publicsher;
import demo.services.AuthorService;
import demo.services.BannerService;
import demo.services.DiscountCodeService;
import demo.services.PublicsherService;
import demo.services.StorageService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class DiscountCodeController {
	@Autowired
	private DiscountCodeService discountCodeService;
	

	@GetMapping("/discountCode")
	public String index(Model model) {
		List<DiscountCode> list = this.discountCodeService.getAll();
		model.addAttribute("list", list);
		System.out.println("Al");
		return "admin/discountCode/index";

	}

	@GetMapping("/add-discountCode")
	public String add(Model model) {
		DiscountCode discountCode = new DiscountCode();
		discountCode.setStatus(true);
		model.addAttribute("discountCode", discountCode);
		return "admin/discountCode/add";
	}

	@PostMapping("/add-discountCode")
	public String save(Model model, @ModelAttribute("discountCode") DiscountCode discountCode,
		@RequestParam("date1") String date1) {
		if(this.discountCodeService.findByNameCode(discountCode.getNameCode())!=null)
		{
			return "admin/discountCode/add";
		}
		System.out.println("bớt đây+"+date1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
			Date parsedDate = sdf.parse(date1);
			discountCode.setExpiry(parsedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        discountCode.setQuantity(discountCode.getTotal());
		if (this.discountCodeService.create(discountCode)) {
			return "redirect:/admin/discountCode";
		}
		return "admin/discountCode/add";

	}

	@GetMapping("/edit-discountCode/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		DiscountCode discountCode = this.discountCodeService.findById(id);
		Date expiry=discountCode.getExpiry();
		model.addAttribute("discountCode", discountCode);
		model.addAttribute("expiry",expiry);
		return "admin/discountCode/edit";
	}

	@PostMapping("/discountCode-edit")
	public String update(Model model, @ModelAttribute("discountCode") DiscountCode discountCode,@RequestParam("date1") String date1) {
		
		if(discountCode.getNameCode()==null)
		{
			return "admin/discountCode/edit";
		}
		System.out.println("bớt đây+"+discountCode.getQuantity());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
			Date parsedDate = sdf.parse(date1);
			discountCode.setExpiry(parsedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		if (this.discountCodeService.create(discountCode)) {
			return "redirect:/admin/discountCode";
			
			
		}
		return "admin/discountCode/edit";


	}
	 @GetMapping("delete-discountCode/{id}")
	    public String delete(Model model,@PathVariable("id") Integer id) {
	    	if(this.discountCodeService.delete(id)) {
	    		return "redirect:/admin/discountCode";
	    	}
	    	return "redirect:/admin/discountCode";
	    }
}
