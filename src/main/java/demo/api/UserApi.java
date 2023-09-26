package demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.models.User;
import demo.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserApi {
	@Autowired
	private UserService userService;
	@GetMapping
	public User login(@RequestParam("username") String username, @RequestParam("password") String password) {
		User user=this.userService.findByUserName(username);
		if(user==null)
		{
			return null;
		}
		if(user.getPassWord().equals(password)) {
			return user;
		}
		return null;
	}

}
