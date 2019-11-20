package com.it.insidetowns.theinsidetowns.objects.EventSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventDetails
{





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


    public Integer getEvent_Id() {
        return Event_Id;
    }

    public void setEvent_Id(Integer event_Id) {
        Event_Id = event_Id;
    }

    @SerializedName("Event_Id")
    @Expose
    private Integer Event_Id;




    @SerializedName("Type")
    @Expose
    private Integer Type;



    @SerializedName("Title")
    @Expose
    private String Product_Title;



    @SerializedName("Description")
    @Expose
    private String Description;

    @SerializedName("Event_Image")
    @Expose
    private String Product_Image;

    @SerializedName("VenueAddress")
    @Expose
    private String ShopAddress;

    @SerializedName("Venue_Name")
    @Expose
    private String Shop_Name;


    public String getEvent_Discount() {
        return Event_Discount;
    }

    public void setEvent_Discount(String event_Discount) {
        Event_Discount = event_Discount;
    }

    @SerializedName("Event_Discount")
    @Expose
    private String Event_Discount;




}