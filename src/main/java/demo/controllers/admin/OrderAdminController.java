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

import demo.models.Category;
import demo.models.Orders;
import demo.services.BookService;
import demo.services.CartItemService;
import demo.services.CartService;
import demo.services.OrderDetailService;
import demo.services.OrderService;

@Controller
@RequestMapping("/admin")
public class OrderAdminController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private CartService cartService;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private BookService bookService;
	@GetMapping("/order")
	public String order(Model model) {
		List<Orders> list=this.orderService.getAllOrderByIdDesc();
		model.addAttribute("listOrder", list);
		return "admin/order/index";
	}
	 @GetMapping("edit-order/{id}")
	    public String edit(Model model,@PathVariable("id") Integer id) {
		 	Orders order=this.orderService.findByOrder(id);
		 	model.addAttribute("order",order);
		 	
	    	return "admin/order/detailorder";
	    }
		/*
		 * @PostMapping("edit-order") public String update(@ModelAttribute("category")
		 * Category category) { if(this.categoryService.create(category)) { return
		 * "redirect:/admin/category"; } return "admin/category/edit"; }
		 */
}
