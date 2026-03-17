/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nhann
 */
@Entity
@Table(name = "OrderDetails")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderDetails.findAll", query = "SELECT o FROM OrderDetails o"),
    @NamedQuery(name = "OrderDetails.findByOrderId", query = "SELECT o FROM OrderDetails o WHERE o.orderDetailsPK.orderId = :orderId"),
    @NamedQuery(name = "OrderDetails.findByProductId", query = "SELECT o FROM OrderDetails o WHERE o.orderDetailsPK.productId = :productId"),
    @NamedQuery(name = "OrderDetails.findByPrice", query = "SELECT o FROM OrderDetails o WHERE o.price = :price"),
    @NamedQuery(name = "OrderDetails.findByQuanty", query = "SELECT o FROM OrderDetails o WHERE o.quanty = :quanty"),
    @NamedQuery(name = "OrderDetails.findByDiscount", query = "SELECT o FROM OrderDetails o WHERE o.discount = :discount")})
public class OrderDetails implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Double price;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrderDetailsPK orderDetailsPK;
    @Column(name = "quanty")
    private Integer quanty;
    @Column(name = "discount")
    private Double discount;
    @JoinColumn(name = "orderId", referencedColumnName = "orderId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Orders orders;
    @JoinColumn(name = "productId", referencedColumnName = "productId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Products products;

    public OrderDetails() {
    }

    public OrderDetails(OrderDetailsPK orderDetailsPK) {
        this.orderDetailsPK = orderDetailsPK;
    }

    public OrderDetails(int orderId, String productId) {
        this.orderDetailsPK = new OrderDetailsPK(orderId, productId);
    }

    public OrderDetailsPK getOrderDetailsPK() {
        return orderDetailsPK;
    }

    public void setOrderDetailsPK(OrderDetailsPK orderDetailsPK) {
        this.orderDetailsPK = orderDetailsPK;
    }


    public Integer getQuanty() {
        return quanty;
    }

    public void setQuanty(Integer quanty) {
        this.quanty = quanty;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderDetailsPK != null ? orderDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderDetails)) {
            return false;
        }
        OrderDetails other = (OrderDetails) object;
        if ((this.orderDetailsPK == null && other.orderDetailsPK != null) || (this.orderDetailsPK != null && !this.orderDetailsPK.equals(other.orderDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.OrderDetails[ orderDetailsPK=" + orderDetailsPK + " ]";
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
}
