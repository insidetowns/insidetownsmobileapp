package com.it.insidetowns.theinsidetowns.objects;

/**
 * Created by Banana on 12-Sep-17.
 */

public class CategoryObjectTypeThree {
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String city,test;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type;





    private String quantity;
   // private int photo;
   public CategoryObjectTypeThree() {
   }

    public CategoryObjectTypeThree(String id, String photo, String city, String test, String quantity) {
        this.id = id;
        this.photo = photo;
        this.city = city;
        this.test = test;
        this.quantity = quantity;
    }

    public CategoryObjectTypeThree(String id, String photo, String city, String test, String quantity,String title) {
        this.id = id;
        this.photo = photo;
        this.city = city;
        this.test = test;
        this.quantity = quantity;
        this.title  = title;
    }

   public CategoryObjectTypeThree(String id, String photo, String city, String test, String quantity,String title,String type) {
        this.id = id;
        this.photo = photo;
        this.city = city;
        this.test = test;
        this.quantity = quantity;
        this.title  = title;
        this.type  = type;
    }

  /*  public CategoryObjectTypeThree(String id, String photo, String city, String test, String quantity, String price, String totPrice) {
        this.id = id;
        this.photo = photo;
        this.city = city;
        this.test = test;
        this.quantity = quantity;
        this.price = price;
        this.totPrice = totPrice;

    }*/


    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
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
