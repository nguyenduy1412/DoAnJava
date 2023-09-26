package demo.controllers.admin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import demo.models.Book;
import demo.models.Category;
import demo.models.ImageProduct;
import demo.repository.CategoryRepository;
import demo.services.CategoryService;
import demo.services.ImageProductService;
import demo.services.BookService;
import demo.services.StorageService;

@Controller
@RequestMapping("/admin")
public class BookController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BookService bookService;
	@Autowired
	private StorageService storageService;
	@Autowired
	private ImageProductService imageProductService;
	@RequestMapping("/book")
	public String index(Model model,@Param("keyword") String keyword,
    		@RequestParam(name="page",defaultValue = "1") Integer page) {
		Page<Book> list=this.bookService.getAll(page);
		if(keyword !=null) {
			list=this.bookService.searchBook(keyword,page,4);
			model.addAttribute("keyword",keyword);
		}
		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage",page);
		model.addAttribute("list",list);
		return "admin/book/index";
	}

	@GetMapping("/add-book")
	public String add(Model model) {
		Book book = new Book();
		book.setStatus(true);
		List<Category> listCate = categoryService.getAll();
		model.addAttribute("book", book);
		model.addAttribute("listCate", listCate);
		return "admin/book/add";
	}

	@PostMapping("/add-book")
	public String save(Model model, @ModelAttribute("book") Book book,@RequestParam("fileImage") MultipartFile file,@RequestParam("ngayNhap") String ngayNhap,
		@RequestParam("fileImages") MultipartFile[] files  ) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
			Date parsedDate = sdf.parse(ngayNhap);
			book.setDateAdded(parsedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Double priceSale;
		priceSale=book.getPrice()- book.getPrice()* book.getSale() /100;
		book.setPriceSale(priceSale);
		this.storageService.store(file);
		String fileName = file.getOriginalFilename();
		book.setImage(fileName);
		String fileName1=files[0].getOriginalFilename();		
		Boolean isEmpty1=fileName1==null || fileName1.trim().length()==0;
		
		if (this.bookService.create(book)) {
			if(!isEmpty1) {
				for (MultipartFile multipartFile : files) {
					ImageProduct imageProduct=new ImageProduct();
					imageProduct.setImage(multipartFile.getOriginalFilename());
					imageProduct.setBook(book);
					this.storageService.store(multipartFile);
					this.imageProductService.create(imageProduct);
				}
			}
			return "redirect:/admin/book";
		}
		return "admin/book/add";

	}

	@GetMapping("/edit-book/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Book book = this.bookService.findById(id);
		model.addAttribute("book", book);
		Date ngayNhap=book.getDateAdded();	
		List<Category> listCate = categoryService.getAll();
		model.addAttribute("listCate", listCate);
		model.addAttribute("ngayNhap",ngayNhap);
		return "admin/book/edit";
	}

	@PostMapping("/book-edit")
	public String update(Model model, @ModelAttribute("book") Book book,
		@RequestParam("fileImage") MultipartFile file,@RequestParam("ngayNhap") String ngayNhap
		,@RequestParam("listChoose")String listChoose,@RequestParam("fileImages") MultipartFile[] files) {
		
		String fileName = file.getOriginalFilename();
		boolean isEmpty = fileName == null || fileName.trim().length() == 0;
		if (!isEmpty) {
			this.storageService.store(file);
			book.setImage(fileName);
		}
		if(!listChoose.isEmpty())
		{
			String[] arrayChoose=listChoose.split(" ");
			for (String i : arrayChoose) {
				this.imageProductService.delete(Integer.parseInt(i));
			}
		}
		
		String fileName1=files[0].getOriginalFilename();
		Boolean isEmpty1=fileName1==null || fileName1.trim().length()==0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
			Date parsedDate = sdf.parse(ngayNhap);
			book.setDateAdded(parsedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Double priceSale;
		priceSale=book.getPrice()- book.getPrice()* book.getSale() /100;
		book.setPriceSale(priceSale);
		
		if (this.bookService.update(book)) {
			if(!isEmpty1) {
				for (MultipartFile multipartFile : files) {
					ImageProduct imageProduct=new ImageProduct();
					imageProduct.setImage(multipartFile.getOriginalFilename());
					imageProduct.setBook(book);
					this.storageService.store(multipartFile);
					this.imageProductService.create(imageProduct);
				}
			}
			return "redirect:/admin/book";
		}
		return "admin/book/edit";

	}

	@GetMapping("delete-book/{id}")
	public String delete(Model model, @PathVariable("id") Integer id) {
		this.imageProductService.deleteByBookId(id);
		if (this.bookService.delete(id)) {
			return "redirect:/admin/book";
		}
		return "redirect:/admin/book";
	}
	
	
}
