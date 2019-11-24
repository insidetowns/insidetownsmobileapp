package com.it.insidetowns.theinsidetowns.Retro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ganesh on 5/23/2018.
 */

public class LoginRequest {
    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    @SerializedName("Userid")
    @Expose
    private String Userid;



}
