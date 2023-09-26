package demo.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ChatController {
	@RequestMapping("/chat")
	public String chat() {
		return "admin/chat";
	}
}
