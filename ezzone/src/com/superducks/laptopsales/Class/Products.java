package com.superducks.laptopsales.Class;

public class Products {
    private int productId;
    private String categoryId;
    private String name;
    private String producer;
    private String info;
    private String img;
    private String price;

    public Products(int productId, String categoryId, String name, String price) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
    }
    public Products() {
    }

    public Products(int productId, String categoryId, String name, String producer, String info, String img, String price) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.name = name;
        this.producer = producer;
        this.info = info;
        this.img = img;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
