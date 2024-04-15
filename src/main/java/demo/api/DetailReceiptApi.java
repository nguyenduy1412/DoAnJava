package demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.models.Author;
import demo.models.Book;
import demo.models.Cart;
import demo.models.CartItem;
import demo.models.Category;
import demo.models.DetailReceipt;
import demo.models.Receipt;
import demo.models.User;
import demo.services.AuthorService;
import demo.services.BookService;
import demo.services.DetailReceiptService;
import demo.services.ReceiptService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/detail-receipt")
public class DetailReceiptApi {
	@Autowired
	private DetailReceiptService detailReceiptService;
	@Autowired
	private ReceiptService receiptService;
	@Autowired
	private BookService bookService;
	@GetMapping
	public List<DetailReceipt> list(){
		return this.detailReceiptService.getAll();
	}
	
	@PostMapping
	public ResponseEntity<String> addDetailReceipt(@RequestParam("receiptId") Integer id,@RequestParam("bookId") Integer bookId,
			HttpSession session) {
		System.out.println("Id "+id);
		long price=0;
		int lai=0;
		System.out.println("1");
		// tìm kiếm xem sách đã có trong chi tiết phiếu nhập chưa 
		// nếu có rồi thì cập nhật số lượng ngược lại thì thêm mới
	    DetailReceipt detailReceipt=this.detailReceiptService.findByReceiptIdAndBookId(id, bookId);
	    if(detailReceipt==null)
	    {
	    	System.out.println("2");
	    	Receipt receipt=this.receiptService.findById(id);
	    	System.out.println("Phieu"+receipt);
			Book book=this.bookService.findById(bookId);
			System.out.println("book"+book.getBookName());
	    	detailReceipt=new DetailReceipt();
	    	detailReceipt.setBook(book);
			detailReceipt.setReceipt(receipt);
			detailReceipt.setQuantity(1);
			System.out.println("detail"+detailReceipt);
	    }
	    else {
	    	detailReceipt.setQuantity(detailReceipt.getQuantity()+1);
	    }
	    System.out.println("Phieu nhap"+detailReceipt);
		this.detailReceiptService.create(detailReceipt);
	    return new ResponseEntity<>("Thêm đối tượng thành công", HttpStatus.OK);
	}
	@PutMapping("/{id}")
	ResponseEntity<String> update(@PathVariable("id") Integer id, @RequestBody DetailReceipt detailReceipt) {
//			long priceNhap=detailReceipt.getPrice();
//			int profit=detailReceipt.getProfit();
//			DetailReceipt detailReceiptOld=this.detailReceiptService.findById(id);
//			detailReceiptOld.setPrice(priceNhap);
//			detailReceiptOld.setProfit(profit);
//			long priceBook=(long) ( priceNhap + (priceNhap * (double)profit/100));
//			System.out.println("Price: "+priceBook);
//			Book book=detailReceiptOld.getBook();
//			book.setPrice(priceBook);
//			long priceSale= (long) (priceBook + priceBook * (double)book.getSale()/100);
//			book.setPriceSale(priceSale);
//			System.out.println(book.getPrice() + "----"+ book.getPriceSale());
//			
//			this.bookService.create(book);
//			if(this.detailReceiptService.create(detailReceiptOld) != null) {
//				return new ResponseEntity<>("Thêm đối tượng thành công", HttpStatus.OK);
//			}
			return new ResponseEntity<>("Thất bại", HttpStatus.BAD_REQUEST);
	}
	@PutMapping("/updatequantity/{id}")
	ResponseEntity<String> updateQuantity(@PathVariable("id") Integer id, @RequestBody DetailReceipt detailReceipt) {
		
		DetailReceipt detailReceiptOld=this.detailReceiptService.findById(id);
		detailReceiptOld.setQuantity(detailReceipt.getQuantity());
		if(this.detailReceiptService.create(detailReceiptOld)!=null)
		{
			return new ResponseEntity<>("Thêm thành công", HttpStatus.OK);
		}
		return new ResponseEntity<>("Thất bại", HttpStatus.BAD_REQUEST);
	}
}
