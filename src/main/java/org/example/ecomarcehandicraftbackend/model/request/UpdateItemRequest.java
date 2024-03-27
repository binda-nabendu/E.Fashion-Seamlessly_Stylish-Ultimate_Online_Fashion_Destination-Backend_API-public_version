package org.example.ecomarcehandicraftbackend.model.request;

public class UpdateItemRequest {
    private Long productId;
    private String size;
    private int quantity;
    private String color;

    public UpdateItemRequest() {
    }

    public UpdateItemRequest(Long productId, String size, int quantity, String color) {
        this.productId = productId;
        this.size = size;
        this.quantity = quantity;
        this.color = color;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
