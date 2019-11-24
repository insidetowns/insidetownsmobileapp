package com.it.insidetowns.theinsidetowns.objects.chngPwd;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChgMessage
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

    public Integer getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(Integer statusCode) {
        StatusCode = statusCode;
    }

    @SerializedName("StatusCode")
    @Expose
    private Integer StatusCode;



}