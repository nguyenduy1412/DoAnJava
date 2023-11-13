package demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.models.Banner;
import demo.models.Category;
import demo.services.BannerService;
import demo.services.BookService;
import demo.services.CategoryService;

@RestController
@RequestMapping("/api/banner")
public class BannerApi {
    @Autowired
    private BannerService bannerService;
    @GetMapping("/")
	public List<Banner> list(){
		return this.bannerService.getAll();
	}
}
