package com.example.haatbazarecomapp;

public class HorizontalProductScrollModel {
    ///it will be string after connecting with database
    private String product_ID;
    private String productImage;
    private String productTitle;
    private String productPreviousPrice;
    private String productCurrentPrice;

    public HorizontalProductScrollModel(String product_ID,String productImage, String productTitle, String productPreviousPrice, String productCurrentPrice) {
        this.product_ID=product_ID;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productPreviousPrice = productPreviousPrice;
        this.productCurrentPrice = productCurrentPrice;
    }

    public String getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(String product_ID) {
        this.product_ID = product_ID;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductPreviousPrice() {
        return productPreviousPrice;
    }

    public void setProductPreviousPrice(String productPreviousPrice) {
        this.productPreviousPrice = productPreviousPrice;
    }

    public String getProductCurrentPrice() {
        return productCurrentPrice;
    }

    public void setProductCurrentPrice(String productCurrentPrice) {
        this.productCurrentPrice = productCurrentPrice;
    }
}
