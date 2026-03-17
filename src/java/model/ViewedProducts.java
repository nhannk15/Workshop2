package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ViewedProducts {

    private HashMap<String, SetOfProducts> viewedProducts = new HashMap<>();

    public void addNewProduct(String account, Products product) {
        SetOfProducts listOfProducts = this.viewedProducts.get(account);

        if (listOfProducts == null) {
            listOfProducts = new SetOfProducts();
        }

        listOfProducts.addNewProduct(product);
        viewedProducts.put(account, listOfProducts);
    }

    public HashMap<String, SetOfProducts> getViewedProducts() {
        return viewedProducts;
    }

    public void setViewedProducts(HashMap<String, SetOfProducts> viewedProducts) {
        this.viewedProducts = viewedProducts;
    }
    
    
}
