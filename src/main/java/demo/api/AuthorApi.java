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
import org.springframework.web.bind.annotation.RestController;

import demo.models.Author;
import demo.models.CartItem;
import demo.models.Category;
import demo.services.AuthorService;

@RestController
@RequestMapping("/api/author")
public class AuthorApi {
	@Autowired
	private AuthorService authorService;
	@GetMapping
	public List<Author> list(){
		return this.authorService.getAll();
	}
	@PostMapping("/add-author")
	public ResponseEntity<String> addAuthor(@ModelAttribute("author") Author author){
		if(this.authorService.create(author)) {
			return new ResponseEntity<>("Thêm đối tượng thành công", HttpStatus.OK);
		}
		return new ResponseEntity<>("Thêm đối tượng thất bại", HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAuthor(@PathVariable("id") Integer id) {
	    if(authorService.delete(id))
	    {
	    	 return new ResponseEntity<>("Xóa đối tượng thành công", HttpStatus.OK);
	    }
	    return new ResponseEntity<>("Xóa đối tượng thất bại", HttpStatus.BAD_REQUEST);
	}
}
