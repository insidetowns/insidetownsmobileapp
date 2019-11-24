package com.it.insidetowns.theinsidetowns.objects;


/**
 * Created by arthonsystechnologiesllp on 10/03/17.
 */

public class BannerDetails {



    private String ImagId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String name,type;

    public String getImagId() {
        return ImagId;
    }

    public void setImagId(String imagId) {
        ImagId = imagId;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    String discount;
    String shopImage;
    String likeCount;
    String Ptype;

    public String getPtype() {
        return Ptype;
    }

    public void setPtype(String ptype) {
        Ptype = ptype;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    String ProductId;


    /*public BannerDetails(Integer ImagId,String name,String type) {
        this.ImagId = ImagId;
        this.name = name;
        this.type = type;

    }*/

    public BannerDetails(String ImagId,String name,String shopImage,String type,String discount,String Ptype,String ProductId) {
        this.ImagId = ImagId;
        this.name = name;
        this.shopImage = shopImage;
        this.type = type;
        this.discount = discount;
        this.Ptype = Ptype;
        this.ProductId = ProductId;

    }

}