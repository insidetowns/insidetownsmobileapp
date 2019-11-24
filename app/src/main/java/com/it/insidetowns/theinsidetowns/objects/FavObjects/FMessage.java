package com.it.insidetowns.theinsidetowns.objects.FavObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FMessage
{


    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @SerializedName("Message")
    @Expose
    private String Message;



}