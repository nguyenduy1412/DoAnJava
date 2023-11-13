package demo.controllers.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.models.Author;
import demo.models.WareHouse;
import demo.services.WareHouseService;

@Controller
@RequestMapping("/admin")
public class WareHouseController {
	@Autowired
	private WareHouseService wareHouseService;
	@GetMapping("/warehouse")
	public String index(Model model) {
		List<WareHouse> list = this.wareHouseService.getAll();
		model.addAttribute("list", list);
		return "admin/warehouse/index";

	}
}
