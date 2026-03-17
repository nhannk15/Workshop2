/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nhann
 */
@Entity
@Table(name = "products")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Products.findAll", query = "SELECT p FROM Products p"),
    @NamedQuery(name = "Products.findByProductId", query = "SELECT p FROM Products p WHERE p.productId = :productId"),
    @NamedQuery(name = "Products.findByProductName", query = "SELECT p FROM Products p WHERE p.productName = :productName"),
    @NamedQuery(name = "Products.findByProductImage", query = "SELECT p FROM Products p WHERE p.productImage = :productImage"),
    @NamedQuery(name = "Products.findByBrief", query = "SELECT p FROM Products p WHERE p.brief = :brief"),
    @NamedQuery(name = "Products.findByPostedDate", query = "SELECT p FROM Products p WHERE p.postedDate = :postedDate"),
    @NamedQuery(name = "Products.findByUnit", query = "SELECT p FROM Products p WHERE p.unit = :unit"),
    @NamedQuery(name = "Products.findByPrice", query = "SELECT p FROM Products p WHERE p.price = :price"),
    @NamedQuery(name = "Products.findByDiscount", query = "SELECT p FROM Products p WHERE p.discount = :discount")})
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "productId")
    private String productId;
    @Basic(optional = false)
    @Column(name = "productName")
    private String productName;
    @Column(name = "productImage")
    private String productImage;
    @Column(name = "brief")
    private String brief;
    @Column(name = "postedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postedDate;
    @Column(name = "unit")
    private String unit;
    @Column(name = "price")
    private Integer price;
    @Column(name = "discount")
    private Integer discount;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "products")
    private Collection<OrderDetails> orderDetailsCollection;
    @JoinColumn(name = "account", referencedColumnName = "account")
    @ManyToOne(optional = false)
    private Accounts account;
    @JoinColumn(name = "typeId", referencedColumnName = "typeId")
    @ManyToOne(optional = false)
    private Categories typeId;

    public Products() {
    }

    public Products(String productId) {
        this.productId = productId;
    }

    public Products(String productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    public Products(String productId, String productName, String productImage, 
            String brief, Date postedDate, String unit, Integer price, Integer discount, 
            Accounts account, Categories typeId) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.brief = brief;
        this.postedDate = postedDate;
        this.unit = unit;
        this.price = price;
        this.discount = discount;
        this.account = account;
        this.typeId = typeId;
    }

    
    
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    @XmlTransient
    public Collection<OrderDetails> getOrderDetailsCollection() {
        return orderDetailsCollection;
    }

    public void setOrderDetailsCollection(Collection<OrderDetails> orderDetailsCollection) {
        this.orderDetailsCollection = orderDetailsCollection;
    }

    public Accounts getAccount() {
        return account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
    }

    public Categories getTypeId() {
        return typeId;
    }

    public void setTypeId(Categories typeId) {
        this.typeId = typeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Products)) {
            return false;
        }
        Products other = (Products) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Products[ productId=" + productId + " ]";
    }
    
}
