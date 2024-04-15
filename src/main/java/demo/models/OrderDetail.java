package demo.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_detail")
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	@JoinColumn(name="orderId",referencedColumnName = "id")
	private Orders orders;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="bookId",referencedColumnName = "id")
	private Book book;
	private Integer quantity;
	private long price;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reviewId", referencedColumnName = "id")
	@JsonIgnore
	private Review review;
}
