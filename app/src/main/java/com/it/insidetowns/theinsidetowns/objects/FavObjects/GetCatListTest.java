package com.it.insidetowns.theinsidetowns.objects.FavObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCatListTest {


    public List<FavCatDetails> getFavCatDetailsList() {
        return favCatDetailsList;
    }

    public void setFavCatDetailsList(List<FavCatDetails> favCatDetailsList) {
        this.favCatDetailsList = favCatDetailsList;
    }

    @SerializedName("FavCatDetails")
    @Expose
    private List<FavCatDetails> favCatDetailsList;

    public FMessage getfMessage() {
        return fMessage;
    }

    public void setfMessage(FMessage fMessage) {
        this.fMessage = fMessage;
    }

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
