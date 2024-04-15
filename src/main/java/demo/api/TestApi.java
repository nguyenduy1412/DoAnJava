package demo.api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.models.Author;
import demo.models.Notification;
import demo.models.RequiredCancel;
import demo.models.User;
import demo.repository.RequiredCancelRepository;
import demo.services.AuthorService;
import demo.services.NotificationService;
import demo.services.RequiredCancelService;
import demo.services.UserService;

@RestController
@RequestMapping("/api/test")
public class TestApi {
	@Autowired
	private RequiredCancelService requiredCancelService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private UserService userService;
	@PostMapping("/add-required")
	public ResponseEntity<String> addAuthor(){
		RequiredCancel require=new RequiredCancel();
		require.setRequired(true);
		User user=new User();
		require.setCanceller("Duy");
		require.setDateCancel(new Date());
		if(this.requiredCancelService.create(require)) {
			return new ResponseEntity<>("Thêm đối tượng thành công", HttpStatus.OK);
		}
		return new ResponseEntity<>("Thêm đối tượng thất bại", HttpStatus.BAD_REQUEST);
	}
	@PostMapping("/add-notification")
	public ResponseEntity<String> addNotification(){
		Notification notification=new Notification();
		notification.setDate(new Date());
		notification.setMessage("Xin chào");
		User user=this.userService.findById((long)1);
		notification.setUser(user);
		if(this.notificationService.create(notification)) {
			return new ResponseEntity<>("Thêm đối tượng thành công", HttpStatus.OK);
		}
		return new ResponseEntity<>("Thêm đối tượng thất bại", HttpStatus.BAD_REQUEST);
	}
}
