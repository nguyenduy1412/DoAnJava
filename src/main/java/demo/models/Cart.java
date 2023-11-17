package demo.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "cart")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="userId",referencedColumnName = "id")
	@JsonIgnore
	private User user;
	@OneToMany(mappedBy ="cart",fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<CartItem> cartItems;
	private Long total;

	public Long totalPrice() {
		Long totalPrice=(long) 0;
		for (CartItem a : cartItems) {
			totalPrice+=a.getQuantity() * a.getBook().getPriceSale();
		}
		return totalPrice;
	}
}
