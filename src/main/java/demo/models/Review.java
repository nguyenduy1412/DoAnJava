
package demo.models;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Generated;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bookId", referencedColumnName = "id")
	@JsonIgnore
	private Book book;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "detailOrderId", referencedColumnName = "id")
	@JsonIgnore
	private OrderDetail orderDetail;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", referencedColumnName = "id")
//	@JsonIgnore
	private User user;
	private Integer star;
	@Column(columnDefinition = "nvarchar(255)")
	private String rating;
	@Column(columnDefinition = "nvarchar(255)")
	private String img;
	private Date reviewDate;
}