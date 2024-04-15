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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "orders")
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private long sumMoney;
	private Integer status;
	private Date dateOrder;
	@Column(columnDefinition = "nvarchar(255)")
	private String addressShip;
	private String phone;
	@Column(columnDefinition = "nvarchar(255)")
	private String note;
	@Column(columnDefinition = "nvarchar(255)")
	private String fullName;
	@OneToMany(mappedBy = "orders", fetch = FetchType.EAGER)
	
	private Set<OrderDetail> orderDetails;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId",referencedColumnName = "id")
	@JsonIgnore
	private User user;
	private String discountCode;
	private Double moneyShip;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "payId",referencedColumnName = "id")
	@JsonIgnore
	private MethodPay methodPay;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cancelId",referencedColumnName = "id")
	@JsonIgnore
	private RequiredCancel requiredCancel;
}
