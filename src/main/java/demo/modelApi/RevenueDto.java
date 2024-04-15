package demo.modelApi;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RevenueDto {
	private Integer month;
	private Long money;
	private Integer quatity;
	private Integer year;
}
