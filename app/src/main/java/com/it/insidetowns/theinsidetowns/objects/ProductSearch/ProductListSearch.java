package com.it.insidetowns.theinsidetowns.objects.ProductSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.it.insidetowns.theinsidetowns.objects.FavObjects.FMessage;

import java.util.List;

public class ProductListSearch {


    public List<ProductDetails> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDetails> productDetails) {
        this.productDetails = productDetails;
    }

    public FMessage getfMessage() {
        return fMessage;
    }

    public void setfMessage(FMessage fMessage) {
        this.fMessage = fMessage;
    }

    @SerializedName("ProductDetails")
    @Expose
    private List<ProductDetails> productDetails;



    @SerializedName("Message")
    @Expose
    private FMessage fMessage;


/*    public List<FavCatDetails> getFavCatDetailsList() {
        return favCatDetailsList;
    }

    public void setFavCatDetailsList(List<FavCatDetails> favCatDetailsList) {
        this.favCatDetailsList = favCatDetailsList;
    }

    private List<FavCatDetails> favCatDetailsList;



    @Override
    public String toString()
    {
        return "ClassPojo [FavCatDetails = "+favCatDetailsList+
                "]";
    }*/
}
