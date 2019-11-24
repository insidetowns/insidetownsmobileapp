package com.it.insidetowns.theinsidetowns.objects.ProductSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetails
{



    @SerializedName("Product_Id")
    @Expose
    private Integer Product_Id;

    public Integer getProduct_Id() {
        return Product_Id;
    }

    public void setProduct_Id(Integer product_Id) {
        Product_Id = product_Id;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public String getProduct_Title() {
        return Product_Title;
    }

    public void setProduct_Title(String product_Title) {
        Product_Title = product_Title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getProduct_Image() {
        return Product_Image;
    }

    public void setProduct_Image(String product_Image) {
        Product_Image = product_Image;
    }

    public String getShopAddress() {
        return ShopAddress;
    }

    public void setShopAddress(String shopAddress) {
        ShopAddress = shopAddress;
    }

    public String getShop_Name() {
        return Shop_Name;
    }

    public void setShop_Name(String shop_Name) {
        Shop_Name = shop_Name;
    }

    @SerializedName("Type")
    @Expose
    private Integer Type;



    @SerializedName("Product_Title")
    @Expose
    private String Product_Title;



    @SerializedName("Description")
    @Expose
    private String Description;

    @SerializedName("Product_Image")
    @Expose
    private String Product_Image;

    @SerializedName("ShopAddress")
    @Expose
    private String ShopAddress;

    @SerializedName("Shop_Name")
    @Expose
    private String Shop_Name;




}