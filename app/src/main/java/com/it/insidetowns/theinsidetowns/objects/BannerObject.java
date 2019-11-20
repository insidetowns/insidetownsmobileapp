package com.it.insidetowns.theinsidetowns.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ganesh on 5/23/2018.
 */

public class BannerObject {


    public List<ShopsDetails> getShopsDetails() {
        return shopsDetails;
    }

    public void setShopsDetails(List<ShopsDetails> shopsDetails) {
        this.shopsDetails = shopsDetails;
    }

    @SerializedName("ShopsDetails")
    @Expose
    private List<ShopsDetails> shopsDetails = null;

    @SerializedName("Message")
    @Expose
    private MessageRes messageRes;
}
