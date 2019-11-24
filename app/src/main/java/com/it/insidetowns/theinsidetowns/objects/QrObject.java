package com.it.insidetowns.theinsidetowns.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by arthonsystechnologiesllp on 08/03/17.
 */

public class QrObject {


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProduct_Id() {
        return Product_Id;
    }

    public void setProduct_Id(String product_Id) {
        Product_Id = product_Id;
    }

    public String getShop_id() {
        return Shop_id;
    }

    public void setShop_id(String shop_id) {
        Shop_id = shop_id;
    }

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("Product_Id")
    @Expose
    private String Product_Id;

    @SerializedName("Shop_id")
    @Expose
    private String Shop_id;


    public String getDisc_ID() {
        return Disc_ID;
    }

    public void setDisc_ID(String disc_ID) {
        Disc_ID = disc_ID;
    }

    @SerializedName("Disc_ID")
    @Expose
    private String Disc_ID;

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }

    @SerializedName("user_Id")
    @Expose
    private String user_Id;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @SerializedName("Description")
    @Expose
    private String Description;

    @SerializedName("type")
    @Expose
    private String type;


/*
    public QrObject(int v, int d, int u)
    {
        this.Vendor_Id=v;
        this.Disc_ID=d;
        this.user_Id=u;
    }

    public QrObject(int v, int d, int u, int vc)
    {
        this.Vendor_Id=v;
        this.Disc_ID=d;
        this.user_Id=u;
        this.Ven_Cat=vc;
    }
*/


    public QrObject(String d, String u)
    {

        this.Disc_ID=d;
        this.user_Id=u;

    }

    public QrObject(String d, String u,String username, String product_Id,String shop_id,String Description,String type)
    {
        this.Disc_ID=d;
        this.user_Id=u;
        this.username=username;
        this.Product_Id=product_Id;
        this.Shop_id=shop_id;
        this.Description=Description;
        this.type=type;

    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

