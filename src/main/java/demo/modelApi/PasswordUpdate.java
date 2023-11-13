package demo.modelApi;

import lombok.Data;

@Data
public class PasswordUpdate {
	private String passwordOld;
	private String passwordNew;
}
