package adapt.entities;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.CascadeType;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;

   /** 
   Class generated using Kroki EJBGenerator 
   @Author mrd 
   Creation date: 15.02.2013  15:52:28h
   **/

@Entity
@Table(name = "PRODUCT")
public class Product implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private java.lang.Long id;

	@Column(name = "PRO_NAME", unique = false, nullable = false)
	private java.lang.String name;
	
	@Column(name = "PRO_DESCRIPTION", unique = false, nullable = false)
	private java.lang.String description;
	
	
	@ManyToOne
	@JoinColumn(name="category", referencedColumnName="ID", nullable=true)
	private Category category;
	
	
	@OneToMany(cascade = { ALL }, fetch = FetchType.LAZY, mappedBy = "product")
	private Set<OrderItem> orderItemSet = new HashSet<OrderItem>();
	
	@OneToMany(cascade = { ALL }, fetch = FetchType.LAZY, mappedBy = "product")
	private Set<PriceListItem> priceListItemSet = new HashSet<PriceListItem>();
	
	public Product(){
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
	public java.lang.String getDescription() {
		return this.description;
	}
	
	public void setDescription(java.lang.String description) {
		this.description = description;
	}
	
	public Category getCategory() {
		return this.category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Set<OrderItem> getOrderItemSet() {
		return this.orderItemSet;
	}

	public void setOrderItemSet(Set<OrderItem> orderItemSet) {
		this.orderItemSet = orderItemSet;
	}
	
	public Set<PriceListItem> getPriceListItemSet() {
		return this.priceListItemSet;
	}

	public void setPriceListItemSet(Set<PriceListItem> priceListItemSet) {
		this.priceListItemSet = priceListItemSet;
	}
	
}