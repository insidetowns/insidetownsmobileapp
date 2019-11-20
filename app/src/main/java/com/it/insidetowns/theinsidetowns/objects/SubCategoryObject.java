package com.it.insidetowns.theinsidetowns.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Banana on 12-Sep-17.
 */

public class SubCategoryObject {
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String city;


    public String getSubCatcount() {
        return subCatcount;
    }

    public void setSubCatcount(String subCatcount) {
        this.subCatcount = subCatcount;
    }

    @SerializedName("subCatcount")
    @Expose
    private String subCatcount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public String getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(String totPrice) {
        this.totPrice = totPrice;
    }

    String totPrice;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    String photo;


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    String price;


    public Integer getSubcategory_Id() {
        return Subcategory_Id;
    }

    public void setSubcategory_Id(Integer subcategory_Id) {
        Subcategory_Id = subcategory_Id;
    }

    @SerializedName("Subcategory_Id")
    @Expose
    private Integer Subcategory_Id;

    public String getSubcat_name() {
        return Subcat_name;
    }

    public void setSubcat_name(String subcat_name) {
        Subcat_name = subcat_name;
    }

    public String getSubcat_Image() {
        return Subcat_Image;
    }

    public void setSubcat_Image(String subcat_Image) {
        Subcat_Image = subcat_Image;
    }

    @SerializedName("Subcat_name")
    @Expose
    private String Subcat_name;

    @SerializedName("Subcat_Image")
    @Expose
    private String Subcat_Image;


    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    @SerializedName("Type")
    @Expose
    private Integer Type;

    private String quantity;
    // private int photo;
    public SubCategoryObject() {
    }

    public SubCategoryObject(String id, String photo, String city, String subCatcount, String quantity) {
        this.id = id;
        this.photo = photo;
        this.city = city;
        this.subCatcount = subCatcount;
        this.quantity = quantity;
    }

    public SubCategoryObject(String id, String photo, String city, String subCatcount, String quantity, String price, String totPrice) {
        this.id = id;
        this.photo = photo;
        this.city = city;
        this.subCatcount = subCatcount;
        this.quantity = quantity;
        this.price = price;
        this.totPrice = totPrice;

    }




    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    /* public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }*/
}
