package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<String, CartItem> items = new HashMap<>();

    public void addProduct(Products product) {

        String id = product.getProductId();

        if (items.containsKey(id)) {
            CartItem item = items.get(id);
            item.setQuantity(item.getQuantity() + 1);
        } else {
            items.put(id, new CartItem(product, 1));
        }
    }

    public void removeProduct(String productId) {
        items.remove(productId);
    }

    public void setItems(Map<String, CartItem> items) {
        this.items = items;
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public double getTotal() {

        double total = 0;

        for (CartItem item : items.values()) {
            double price = item.getProduct().getPrice();
            double discount = item.getProduct().getDiscount();
            double finalPrice = price * (1 - discount / 100);

            total += finalPrice * item.getQuantity();
        }

        return total;
    }
}
