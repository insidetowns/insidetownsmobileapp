package com.it.insidetowns.theinsidetowns.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceIdResponse {


    /**
     * isError : false
     * isSuccess : true
     * Message :  DeviceId Updated.
     * StatusCode : 200
     * FlagID : 0
     */
    @SerializedName("isError")
    @Expose
    private boolean isError;
    @SerializedName("isSuccess")
    @Expose

    private boolean isSuccess;
    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("StatusCode")
    @Expose
    private int StatusCode;
    @SerializedName("FlagID")
    @Expose
    private int FlagID;

    public boolean isIsError() {
        return isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int StatusCode) {
        this.StatusCode = StatusCode;
    }

    public int getFlagID() {
        return FlagID;
    }

    public void setFlagID(int FlagID) {
        this.FlagID = FlagID;
    }
}
