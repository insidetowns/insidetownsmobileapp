package com.it.insidetowns.theinsidetowns.objects.chngPwd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.it.insidetowns.theinsidetowns.objects.FavObjects.FMessage;

public class ChngPwd {


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
