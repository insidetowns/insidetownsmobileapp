package com.it.insidetowns.theinsidetowns.objects.ShopSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.it.insidetowns.theinsidetowns.objects.FavObjects.FMessage;

import java.util.List;

public class ShopListSearch {



    public FMessage getfMessage() {
        return fMessage;
    }

    public void setfMessage(FMessage fMessage) {
        this.fMessage = fMessage;
    }


    public List<ShopsDetails> getShopsDetailsList() {
        return shopsDetailsList;
    }

    public void setShopsDetailsList(List<ShopsDetails> shopsDetailsList) {
        this.shopsDetailsList = shopsDetailsList;
    }

    @SerializedName("ShopsDetails")
    @Expose
    private List<ShopsDetails> shopsDetailsList;



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
