package demo.modelApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private String fullName;
	private Integer gender;
	private String address;
	private String email;
	private String telephone;
	private String birthday;
}
