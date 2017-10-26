package it.fyb.paypal.model;

import java.util.ArrayList;
import java.util.List;

public class ItemList {

    private List<Item> items = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
