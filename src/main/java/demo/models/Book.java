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
@Table(name = "book")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(columnDefinition = "nvarchar(255)")
	private String bookName;
	@Column(columnDefinition = "nvarchar(255)")
	private String author;
	private Double price;
	private Double priceSale;
	private String image;
	@Column(columnDefinition = "nvarchar(255)")
	private String publicsher;
	private Integer publicationYear;
	private Integer sale;
	@Column(columnDefinition = "Date")
	private Date dateAdded;
	@Column(columnDefinition = "nvarchar(4000)")
	private String description;
	private Boolean status;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryId",referencedColumnName = "id")
	@JsonIgnore
	private Category category;
	@OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
//	@JsonIgnore
	private Set<ImageProduct> imageProducts;
}
