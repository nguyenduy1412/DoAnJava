package demo.controllers.fe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo.models.Book;
import demo.models.CartItem;
import demo.models.Favourite;
import demo.models.FavouriteItem;
import demo.models.User;
import demo.services.BookService;
import demo.services.CartItemService;
import demo.services.CartService;
import demo.services.FavouriteItemService;
import demo.services.FavouriteService;
import jakarta.servlet.http.HttpSession;

@Controller

public class WishListController {
	@Autowired
	private FavouriteService favouriteService;
	@Autowired
	private FavouriteItemService favouriteItemService;
	@Autowired
	private BookService bookService;
	@GetMapping("/favourite")
	public String getFavourite(HttpSession session, Model model)
	{
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		Favourite favourite=this.favouriteService.findByUserId(user.getId());
		if(favourite==null)
		{
			favourite=new Favourite();
			favourite.setUser(user);
			this.favouriteService.create(favourite);
		}
		
		if (favourite.getId() != null) {
			List<FavouriteItem> listItem = this.favouriteItemService.findByFavouriteIdOrderByIdDesc(favourite.getId());
			model.addAttribute("listItem", listItem);
		}
		return "wishlist";
	}
	@PostMapping("/add-favourite")
	public String addFavourite(HttpSession session, Model model,@RequestParam("id") Integer bookId)
	{
		User user = (User) session.getAttribute("user");
		Book book=this.bookService.findById(bookId);
		if (user == null) {
			return "redirect:/login";
		}
		Favourite favourite=this.favouriteService.findByUserId(user.getId());
		if(favourite==null)
		{
			favourite=new Favourite();
			favourite.setUser(user);
			this.favouriteService.create(favourite);
		}
		FavouriteItem favouriteItem=new FavouriteItem();
		favouriteItem.setBook(book);
		favouriteItem.setFavourite(favourite);
		if(this.favouriteItemService.createOrUpdate(favouriteItem)) {
			return "redirect:/favourite";
		}
		return "redirect:/favourite";
	}
	
}
