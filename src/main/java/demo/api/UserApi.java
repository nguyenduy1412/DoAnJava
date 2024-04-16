package demo.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import demo.modelApi.PasswordUpdate;
import demo.modelApi.UserDTO;
import demo.models.Cart;
import demo.models.Favourite;
import demo.models.User;
import demo.services.CartService;
import demo.services.FavouriteService;
import demo.services.StorageService;
import demo.services.UserService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
public class UserApi {
	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private StorageService storageService;
	@Autowired
	private CartService cartService;
	@Autowired
	private FavouriteService favouriteService;
	@GetMapping
	public User login(@RequestParam("username") String username, @RequestParam("password") String password) {
		User user=this.userService.findByUserName(username);
		if(user==null)
		{

			return null;
		}
		if(passwordEncoder.matches(password, user.getPassWord())) {
			return user;
		}
		return null;
	}
	@GetMapping("/{id}")
	public User getInform(@PathVariable Long id) {
		User user=this.userService.findById(id);
		return user;
	}
	@PostMapping("/signup")
	public ResponseEntity<String> addAccount(@RequestBody User user) {
		User a=this.userService.findByUserName(user.getUserName());
		if(this.userService.findByEmail(user.getEmail())!=null) {
			System.out.println("Alooo");
			return new ResponseEntity<>("Email đã tồn tại", HttpStatus.BAD_REQUEST);
		}
		if(a!=null){
			System.out.println("Alooo");
			return new ResponseEntity<>("Tên đăng nhập đã tồn tại", HttpStatus.BAD_REQUEST);
		}
		
		String pass=new BCryptPasswordEncoder().encode(user.getPassWord());
		
		user.setGender(1);
		user.setPassWord(pass);
		user.setImgCover("coverdefault.png");
		user.setImg("avatadefault.png");
		if(this.userService.update(user)!=null){
			Cart cart = new Cart();
			cart.setUser(user);
			this.cartService.create(cart);
			Favourite favourite=new Favourite();
			favourite.setUser(user);
			this.favouriteService.create(favourite);
			return new ResponseEntity<>("Đăng kí thành công", HttpStatus.OK);
		}
		return new ResponseEntity<>("Đăng kí thất bại", HttpStatus.BAD_REQUEST);
	}
	@PutMapping("/uploadAvata/{id}")
	public ResponseEntity<String> uploadAvata(@PathVariable Long id,@RequestParam("file") MultipartFile file,
			HttpSession session) {
		User user = userService.findById(id);

	    if (user == null) {
	        return new ResponseEntity<>("Người dùng không tồn tại", HttpStatus.BAD_REQUEST);
	    }
		String fileName = file.getOriginalFilename();
		boolean isEmpty = fileName == null || fileName.trim().length() == 0;
		if (!isEmpty) {
			this.storageService.store(file);
			user.setImg(fileName);
			this.userService.update(user);
			session.setAttribute("user", user);
			return new ResponseEntity<>("Thêm thành công", HttpStatus.OK);
		}
		return new ResponseEntity<>("Thêm thất bại", HttpStatus.BAD_REQUEST);
	}
	@PutMapping("/uploadCover/{id}")
	public ResponseEntity<String> uploadCover(@PathVariable Long id,@RequestParam("file") MultipartFile file,
			HttpSession session) {
		User user = userService.findById(id);
	    if (user == null) {
	        return new ResponseEntity<>("Người dùng không tồn tại", HttpStatus.BAD_REQUEST);
	    }
		String fileName = file.getOriginalFilename();
		boolean isEmpty = fileName == null || fileName.trim().length() == 0;
		if (!isEmpty) {
			this.storageService.store(file);
			user.setImgCover(fileName);
			this.userService.update(user);
			session.setAttribute("user", user);
			return new ResponseEntity<>("Thêm thành công", HttpStatus.OK);
		}
		return new ResponseEntity<>("Thêm thất bại", HttpStatus.BAD_REQUEST);
	}
	@PutMapping("/updateAccount/{id}")
	public ResponseEntity<String> updateAccount(@PathVariable Long id,@RequestBody UserDTO user){
		User userOld=this.userService.findById(id);
		User check=this.userService.findByEmail(user.getEmail());
		if(check != null && check!=userOld) {
			return new ResponseEntity<>("Email đã tồn tại", HttpStatus.BAD_REQUEST);
		}
		
		userOld.setFullName(user.getFullName());
		userOld.setAddress(user.getAddress());
		userOld.setEmail(user.getEmail());
		userOld.setTelephone(user.getTelephone());
		userOld.setGender(user.getGender());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
			Date parsedDate = sdf.parse(user.getBirthday());
			userOld.setBirthday(parsedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        if(this.userService.update(userOld)!=null) {
        	return new ResponseEntity<>("thành công", HttpStatus.OK);
		}
		return new ResponseEntity<>("thất bại", HttpStatus.BAD_REQUEST);
	}
	@PutMapping("/updatePass/{id}")
	public ResponseEntity<String> updatePass(@PathVariable Long id,@RequestBody PasswordUpdate pass){
		User user=this.userService.findById(id);
		boolean check=passwordEncoder.matches(pass.getPasswordOld(), user.getPassWord());
		if(check) {
			user.setPassWord(passwordEncoder.encode(pass.getPasswordNew()));
			if(this.userService.update(user)!=null) {
				return new ResponseEntity<>("thành công", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>("thất bại", HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>("Sai mật khẩu", HttpStatus.BAD_REQUEST);
	}
	@PostMapping("/updateEnable/{id}")
	public ResponseEntity<String> updateEnable(@PathVariable Long id){
		User user=this.userService.findById(id);
		user.setEnabled(!user.getEnabled());
		if(this.userService.update(user)!=null) {
			return new ResponseEntity<>("Thành công", HttpStatus.OK);
		}
		return new ResponseEntity<>("thất bại", HttpStatus.BAD_REQUEST);
	}
}
