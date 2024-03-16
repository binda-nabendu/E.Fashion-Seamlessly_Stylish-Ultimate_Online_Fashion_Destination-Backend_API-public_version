package org.example.ecomarcehandicraftbackend.model;

public class Size {
    private String name;
    private String quantity;

    public Size(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Size() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
