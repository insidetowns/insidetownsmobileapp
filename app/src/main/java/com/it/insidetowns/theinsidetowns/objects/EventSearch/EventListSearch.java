package com.it.insidetowns.theinsidetowns.objects.EventSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.it.insidetowns.theinsidetowns.objects.FavObjects.FMessage;

import java.util.List;

public class EventListSearch {



    public FMessage getfMessage() {
        return fMessage;
    }

    public void setfMessage(FMessage fMessage) {
        this.fMessage = fMessage;
    }


    public List<EventDetails> getEventDetailsList() {
        return eventDetailsList;
    }

    public void setEventDetailsList(List<EventDetails> eventDetailsList) {
        this.eventDetailsList = eventDetailsList;
    }

    @SerializedName("EventDetails")
    @Expose
    private List<EventDetails> eventDetailsList;



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
