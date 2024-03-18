package org.example.ecomarcehandicraftbackend.model.request;

import org.example.ecomarcehandicraftbackend.model.Size;

import java.util.HashSet;
import java.util.Set;

public class CreateProductRequestModel {
    private String title;
    private String description;
    private String color, brand;
    private int actualPrice;
    private int discountPrice;
    private int discountPercent;
    private int quantity;
    private Set<Size> size= new HashSet<>();
    private String imageUrl;
    private String topLabelCategory, secondLabelCategory, subCategory;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(int actualPrice) {
        this.actualPrice = actualPrice;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Set<Size> getSize() {
        return size;
    }

    public void setSize(Set<Size> size) {
        this.size = size;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTopLabelCategory() {
        return topLabelCategory;
    }

    public void setTopLabelCategory(String topLabelCategory) {
        this.topLabelCategory = topLabelCategory;
    }

    public String getSecondLabelCategory() {
        return secondLabelCategory;
    }

    public void setSecondLabelCategory(String secondLabelCategory) {
        this.secondLabelCategory = secondLabelCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public CreateProductRequestModel(String title, String description, String color, String brand, int actualPrice, int discountPrice, int discountPercent, int quantity, Set<Size> size, String imageUrl, String topLabelCategory, String secondLabelCategory, String subCategory) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.brand = brand;
        this.actualPrice = actualPrice;
        this.discountPrice = discountPrice;
        this.quantity = quantity;
        this.size = size;
        this.imageUrl = imageUrl;
        this.topLabelCategory = topLabelCategory;
        this.secondLabelCategory = secondLabelCategory;
        this.subCategory = subCategory;
        this.discountPercent = discountPercent;
    }
}
