package demo.controllers.admin;

import java.util.ArrayList;
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

import demo.modelApi.RevenueDto;
import demo.models.Category;
import demo.models.MethodPay;
import demo.models.OrderDetail;
import demo.models.Orders;
import demo.services.MethodPayService;
import demo.services.OrderService;
import java.time.Year;
@Controller
@RequestMapping("/admin")
public class StatisticalController {
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/statistical")
	public String index(Model model) {
		int currentYear = Year.now().getValue();
		List<RevenueDto> listReve=new ArrayList<RevenueDto>();
		String dataRevenue="";
		for(int i=1;i<=12;i++) {
			List<Orders> listOrder=this.orderService.findByMonthAndYear(i, currentYear);
			long totalMoneyOrder=0;
			Integer quantity=0;
			
			for (Orders orders : listOrder) {
				for (OrderDetail a : orders.getOrderDetails()) {
					quantity+=a.getQuantity();
				}
				if(orders.getStatus()==3)
				{
					totalMoneyOrder+=orders.getSumMoney();
				}
			}
			totalMoneyOrder=(long) (totalMoneyOrder * 0.1);
			RevenueDto a=new RevenueDto();
			a.setMoney(totalMoneyOrder);
			a.setQuatity(quantity);
			a.setMonth(i);
			a.setYear(currentYear);
			if(i<12)
			{
				dataRevenue+=totalMoneyOrder+",";
			}
			else {
				dataRevenue+=totalMoneyOrder;
			}
			listReve.add(a);
		}
		
		System.out.println(dataRevenue);
		
		model.addAttribute("dataRevenue", dataRevenue);
		return "admin/statistical/index";
	}
	
}
