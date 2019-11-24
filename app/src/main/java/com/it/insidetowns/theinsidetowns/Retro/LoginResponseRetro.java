package com.it.insidetowns.theinsidetowns.Retro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ganesh on 5/23/2018.
 */

public class LoginResponseRetro {
    public List<ItemAttributes> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ItemAttributes> attributes) {
        this.attributes = attributes;
    }

    @SerializedName("FavCatDetails")
    @Expose
    private List<ItemAttributes> attributes= null;
}
