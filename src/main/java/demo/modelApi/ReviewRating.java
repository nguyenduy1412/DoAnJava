package demo.modelApi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRating {
	private Integer bookId;
	private long userId;
	private Integer orderDetailId;
	private Integer star;
	private String rating;
	private String img;
}
