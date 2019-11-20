package com.it.insidetowns.theinsidetowns.objects;

/**
 * Created by Banana on 12-Sep-17.
 */

public class MyOffersObject {

    String Discount;

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getVendorname() {
        return Vendorname;
    }

    public void setVendorname(String vendorname) {
        Vendorname = vendorname;
    }

    public String getReedemedOn() {
        return ReedemedOn;
    }

    public void setReedemedOn(String reedemedOn) {
        ReedemedOn = reedemedOn;
    }

    String Vendorname;
    String ReedemedOn;

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    String ProductImage;
 //   String price;
   public MyOffersObject() {
   }

    public MyOffersObject(String Discount, String Vendorname, String ReedemedOn,String ProductImage) {
        this.Discount = Discount;
        this.Vendorname = Vendorname;
        this.ReedemedOn = ReedemedOn;
        this.ProductImage = ProductImage;
      //  this.test = test;
      //  this.quantity = quantity;
    }


}
