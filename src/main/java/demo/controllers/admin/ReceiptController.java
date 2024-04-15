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
import demo.models.WareHouse;
import demo.services.BookService;
import demo.services.DetailReceiptService;
import demo.services.ReceiptService;
import demo.services.StorageService;
import demo.services.WareHouseService;
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
	@Autowired
	private WareHouseService wareHouseService;
	@GetMapping("/receipt")
	public String index(Model model) {
		// xóa các phiếu nhập clone
		try {
			List<Receipt> listReceipt=this.receiptService.findBySumMoney(0);
			for (Receipt a : listReceipt) {
				if(this.detailReceiptService.deleteByReceiptId(a.getId())) {
					
				}
				this.receiptService.delete(a.getId());
			}	
		} catch (Exception e) {
			// TODO: handle exception
		}
		List<Receipt> list = this.receiptService.getAll();
		model.addAttribute("list", list);
		return "admin/receipt/index";
	}
	@GetMapping("/add-receipt")
	public String add(Model model,HttpSession session) {
		Receipt receipt = new Receipt();
		receipt.setDateAdded(new Date());
		receipt.setSumMoney(0);
		if(this.receiptService.create(receipt)) {
			
		}
		
		List<Book> listBook=this.bookService.findAllByOrderByBookNameAsc();
		model.addAttribute("listBook", listBook);
		
		
		session.setAttribute("receipt", receipt);
		List<DetailReceipt> detai=this.detailReceiptService.findByReceiptId(receipt.getId());
		model.addAttribute("listDetail", detai);
		model.addAttribute("receipt", receipt);
		return "admin/receipt/add";
	}
	@PostMapping("/add-receipt")
	public String addReceipt(@ModelAttribute("receipt") Receipt receipt){
		receipt.setDateAdded(new Date());
		List<DetailReceipt> list=this.detailReceiptService.findByReceiptId(receipt.getId());
		
		if(this.receiptService.create(receipt))
		{
			// thêm số sách vừa nhập vào kho
			// tìm kiếm xem sách đã có trong kho hay chưa nếu có rồi thì cập nhật số lượng
			
			for (DetailReceipt a : list) {
				WareHouse wareHouse=this.wareHouseService.findByBookId(a.getBook().getId());
				//chưa có sách trong kho thì thêm mới
				if(wareHouse==null) {
					wareHouse=new WareHouse();
					wareHouse.setBook(a.getBook());
					wareHouse.setQuantity(a.getQuantity());
					wareHouse.setQuantityEnter(a.getQuantity());
					wareHouse.setSold(0);
				}
				// cap nhat so luong
				else {
					Integer quantityNew=wareHouse.getQuantity()+a.getQuantity();
					wareHouse.setQuantity(quantityNew);
					wareHouse.setQuantityEnter(a.getQuantity()+wareHouse.getQuantityEnter());
				}
				// cập nhật lại kho
				if(this.wareHouseService.create(wareHouse)) {
					System.out.println("Cap nhat thanh cong");
				}
			}
			
			
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
