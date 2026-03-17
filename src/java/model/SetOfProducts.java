package model;

import java.util.HashSet;
import java.util.Set;

public class SetOfProducts {
    private Set<Products> viewedProducts = new HashSet<>();

    public SetOfProducts() {
    }

    public Set<Products> getViewedProducts() {
        return viewedProducts;
    }

    public void setViewedProducts(Set<Products> viewedProducts) {
        this.viewedProducts = viewedProducts;
    }
    
    public void addNewProduct(Products product) {
        this.viewedProducts.add(product);
    }
}
