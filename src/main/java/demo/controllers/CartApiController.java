package demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.models.CartItem;
import demo.services.CartItemService;

@RestController
@RequestMapping("/cartItemApi")
public class CartApiController {
	@Autowired
	private CartItemService cartItemService;
	@GetMapping()
	public List<CartItem> list(){
		return this.cartItemService.getAll();
	}
	@PutMapping("/{id}")
	CartItem update(@PathVariable("id") Integer id, @RequestBody CartItem cartItem) {
		
		CartItem cartItemOld=this.cartItemService.findById(id);
		cartItemOld.setQuantity(cartItem.getQuantity());
		return this.cartItemService.update(cartItemOld);
	}
	
}
