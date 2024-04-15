package demo.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detailReceipt")
public class DetailReceipt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private int quantity;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="receiptId",referencedColumnName = "id")
//	@JsonIgnore
	private Receipt receipt;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="bookId",referencedColumnName = "id")
//	@JsonIgnore
	private Book book;

	
}
