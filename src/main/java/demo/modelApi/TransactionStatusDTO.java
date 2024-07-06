package demo.modelApi;

import lombok.Data;

@Data
public class TransactionStatusDTO {
	private String status;
	private String message;
	private String data;
}
