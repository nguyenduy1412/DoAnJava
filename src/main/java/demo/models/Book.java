package demo.models;

import java.util.Date;
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
import jakarta.persistence.ManyToOne;
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
@Table(name = "book")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(columnDefinition = "nvarchar(255)")
	private String bookName;
	private long priceEnter;
	private long price;
	private long priceSale;
	private String image;
	private Integer profit;
	private Integer publicationYear;
	private Integer sale;
	@Column(columnDefinition = "nvarchar(4000)")
	private String description;
	private Boolean status;
	// danh muc
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryId", referencedColumnName = "id")
//	@JsonIgnore
	private Category category;
	// tac gia
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "authorId", referencedColumnName = "id")
//	@JsonIgnore
	private Author author;
	// nha xuat ban
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "publicsherId", referencedColumnName = "id")
//	@JsonIgnore
	private Publicsher publicsher;
	// anh chi tiết
	@OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<ImageProduct> imageProducts;
	// chi tiet phieu nhap
	@OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<DetailReceipt> detailReceipts ;
	@OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
	
	private Set<Review> reviews;
	private Double star;
	@OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Recent_Products> recentProducts;
	@OneToOne(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private WareHouse wareHouse;
}
