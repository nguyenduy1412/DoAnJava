package demo.controllers.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo.models.Category;
import demo.models.MethodPay;
import demo.services.MethodPayService;

@Controller
@RequestMapping("/admin")
public class MethodPayController {
	@Autowired
	private MethodPayService methodPayService;
	
	@GetMapping("/methodpay")
	public String index(Model model) {
		List<MethodPay> list=this.methodPayService.getAll();
		System.out.println("List"+list);
		model.addAttribute("listPay",list);	
		return "admin/methodpay/index";
	}
	@GetMapping("/add-pay")
	public String add(Model model) {
		MethodPay pay=new MethodPay();
		pay.setStatus(true);
		model.addAttribute("pay", pay);
		return "admin/methodpay/add";
	}
	@PostMapping("/add-pay")
	public String save(@ModelAttribute("pay") MethodPay pay) {
		if(this.methodPayService.createOrUpdate(pay)) {
    		return "redirect:/admin/methodpay";
    	}
    	else {
    		return "admin/methodpay/add";
    	}
	}
	@GetMapping("/edit-pay/{id}")
	public String edit(Model model,@PathVariable("id") Integer id) {
		MethodPay methodPay =this.methodPayService.findById(id);
    	model.addAttribute("pay",methodPay);
		return "admin/methodpay/edit";
	}
	@PostMapping("/update-pay")
	public String update(@ModelAttribute("pay") MethodPay pay) {
		if(this.methodPayService.createOrUpdate(pay)) {
    		return "redirect:/admin/methodpay";
    	}
    	else {
    		return "admin/methodpay/edit";
    	}
	}
	@GetMapping("/delete-pay/{id}")
	public String delete(Model model,@PathVariable("id") Integer id) {
		if(this.methodPayService.delete(id)) {
    		return "redirect:/admin/methodpay";
    	}
    	return "redirect:/admin/methodpay";
	}
}
