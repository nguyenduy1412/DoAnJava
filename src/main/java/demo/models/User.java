package demo.models;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message = "Tên đăng nhập được không rỗng")
	@Column(columnDefinition = "nvarchar(255)",unique = true)
	private String userName;
	@Size(min = 6, message = "Mật khẩu có ít nhất 6 kí tự")
	@Column(columnDefinition = "nvarchar(255)")
	private String passWord;
	private Boolean enabled;
	private String codeOtp;
	@Column(columnDefinition = "nvarchar(255)")
	private String fullName;
	private Integer gender;
	@Column(columnDefinition = "nvarchar(255)")
	private String address;
	private String email;
	@Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", message = "Số điện thoại không đúng định dạng")
	private String telephone;
	@Column(columnDefinition = "Date")
	private Date birthday;
	@Column(columnDefinition = "nvarchar(255)")
	private String img;
	@Column(columnDefinition = "nvarchar(255)")
	private String imgCover;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<UserRole> userRoles;	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Notification> notifications;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Recent_Products> recentProducts;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Orders> orders;
}
