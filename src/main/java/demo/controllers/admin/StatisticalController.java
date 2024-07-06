package demo.controllers.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
		int day = 0, month = 0, year = 0;
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH trả về giá trị từ 0-11, nên cần +1
		year = cal.get(Calendar.YEAR);
		List<Orders> list=this.orderService.findByDayMonthAndYear(day, month, year);
		int sumOrder=list.size();
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
		
		System.out.println("Data"+dataRevenue);
		model.addAttribute("sumOrder", sumOrder);
		model.addAttribute("dataRevenue", dataRevenue);
		return "admin/statistical/index";
	}
	
}
