package demo.controllers.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import demo.models.Orders;
import demo.models.Review;
import demo.services.ReviewService;

@Controller
@RequestMapping("/admin")
public class RatingController {
	
	@Autowired
	private ReviewService reviewService;
	@GetMapping("/rating")
	public String index(Model model,@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "date", defaultValue = "0") String date,
			@RequestParam(name = "allday", defaultValue = "1") Integer allday) {
		int day = 0, month = 0, year = 0;
		Date now = new Date();
		// lấy tất cả đánh giá theo ngày tháng năm nếu không lựa chọn ngày thì giá trị
		// mặc định là 0
		// và gắn là ngày hiện tại
		if (date.equals("0")) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			day = cal.get(Calendar.DAY_OF_MONTH);
			month = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH trả về giá trị từ 0-11, nên cần +1
			year = cal.get(Calendar.YEAR);

		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				now = sdf.parse(date);
				Calendar cal = Calendar.getInstance();
				cal.setTime(now);
				day = cal.get(Calendar.DAY_OF_MONTH);
				month = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH trả về giá trị từ 0-11, nên cần +1
				year = cal.get(Calendar.YEAR);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		Page<Review> list;
		// nếu chọn theo ngày thì lấy list review theo ngày ngược lại thì lấy tất cả danh
		// sách review
		if (allday == 1) {
			list = this.reviewService.findByDayMonthAndYear(day, month, year, page, 10);
		} else {
			list = this.reviewService.findAllByOrderByIdDesc(page, 10);
		}
		model.addAttribute("listReview", list);

		model.addAttribute("date", now);

		model.addAttribute("allday", allday);
		return "admin/rating/index";
	}
	@GetMapping("/rating/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Review review=this.reviewService.findById(id);
		model.addAttribute("review", review);
		return "admin/rating/detail-rating";
	}
}
