package com.it.insidetowns.theinsidetowns.objects.ShopSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopsDetails
{


    public Integer getShop_Id() {
        return Shop_Id;
    }

    public void setShop_Id(Integer shop_Id) {
        Shop_Id = shop_Id;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }
/*

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
*/

    public String getShop_Image() {
        return Shop_Image;
    }

    public void setShop_Image(String shop_Image) {
        Shop_Image = shop_Image;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getShop_Name() {
        return Shop_Name;
    }

    public void setShop_Name(String shop_Name) {
        Shop_Name = shop_Name;
    }

    @SerializedName("Shop_Id")
    @Expose
    private Integer Shop_Id;


    @SerializedName("Type")
    @Expose
    private Integer Type;



 /*   @SerializedName("Product_Title")
    @Expose
    private String Product_Title;



    @SerializedName("Description")
    @Expose
    private String Description;*/

    @SerializedName("Shop_Image")
    @Expose
    private String Shop_Image;

    @SerializedName("Address")
    @Expose
    private String Address;

    @SerializedName("Shop_Name")
    @Expose
    private String Shop_Name;




}