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
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Long sumMoney;
	private Integer status;
	private Date dateOrder;
	@Column(columnDefinition = "nvarchar(255)")
	private String addressShip;
	private String phone;
	@Column(columnDefinition = "nvarchar(255)")
	private String note;
	@OneToMany(mappedBy = "orders", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<OrderDetail> orderDetails;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId",referencedColumnName = "id")
	@JsonIgnore
	private User user;
}
