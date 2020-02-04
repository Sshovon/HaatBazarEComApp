package com.example.haatbazarecomapp;

public class WishlistModel {
    private String proudctImage;
    private String productTitle;
    private String productRating;
    private long totalRatings;
    private String productPrice;
    private String productId;

    public WishlistModel(String productId,String proudctImage, String productTitle, String productRating, long totalRatings, String productPrice) {
        this.productId=productId;
        this.proudctImage = proudctImage;
        this.productTitle = productTitle;
        this.productRating = productRating;
        this.totalRatings = totalRatings;
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }


    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProudctImage() {
        return proudctImage;
    }

    public void setProudctImage(String proudctImage) {
        this.proudctImage = proudctImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductRating() {
        return productRating;
    }

    public void setProductRating(String productRating) {
        this.productRating = productRating;
    }

    public long getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(long totalRatings) {
        this.totalRatings = totalRatings;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
