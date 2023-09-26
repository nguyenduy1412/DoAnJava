package demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.models.CartItem;
import demo.models.Orders;
import demo.services.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderApi {
	@Autowired
	private OrderService orderService;
	@GetMapping()
	public List<Orders> list(){
		return this.orderService.getAll();
	}
	@PutMapping("/{id}")
	Orders update(@PathVariable("id") Integer id, @RequestBody Orders order) {
		
		Orders orderOld=this.orderService.findByOrder(id);
		orderOld.setStatus(order.getStatus());
		return this.orderService.update(orderOld);
	}
	@GetMapping("/{id}")
	Orders find(@PathVariable("id") Integer id) {
		
		
		return this.orderService.findByOrder(id);
	}
}
