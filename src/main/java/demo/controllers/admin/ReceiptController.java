package demo.controllers.admin;

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
import org.springframework.web.multipart.MultipartFile;

import demo.models.Author;
import demo.models.Book;
import demo.models.DetailReceipt;
import demo.models.Receipt;
import demo.services.BookService;
import demo.services.DetailReceiptService;
import demo.services.ReceiptService;
import demo.services.StorageService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class ReceiptController {
	@Autowired
	private ReceiptService receiptService;
	@Autowired
	private BookService bookService;
	@Autowired
	private DetailReceiptService detailReceiptService;
	@GetMapping("/receipt")
	public String index(Model model) {
		List<Receipt> list = this.receiptService.getAll();
		model.addAttribute("list", list);
		return "admin/receipt/index";
	}
	@GetMapping("/add-receipt")
	public String add(Model model,HttpSession session) {
		Receipt receipt = new Receipt();
		receipt.setDateAdded(new Date());
		List<Book> listBook=this.bookService.findAllByOrderByBookNameAsc();
		model.addAttribute("listBook", listBook);
		receipt.setSumMoney(0);
		this.receiptService.create(receipt);
		System.out.println("Id: "+receipt.getId());
		session.setAttribute("receipt", receipt);
		List<DetailReceipt> detai=this.detailReceiptService.findByReceiptId(receipt.getId());
		model.addAttribute("listDetail", detai);
		model.addAttribute("receipt", receipt);
		return "admin/receipt/add";
	}
	@PostMapping("/add-receipt")
	public String addReceipt(@ModelAttribute("receipt") Receipt receipt){
		receipt.setDateAdded(new Date());
		if(this.receiptService.create(receipt))
		{
			return "redirect:/admin/receipt";
		}
		return "admin/receipt/add";
	}
	@GetMapping("delete-receipt/{id}")
    public String delete(Model model,@PathVariable("id") Integer id) {
	 	
    	if(this.receiptService.delete(id)) {
    		return "redirect:/admin/receipt";
    	}
    	return "redirect:/admin/receipt";
    }

}
