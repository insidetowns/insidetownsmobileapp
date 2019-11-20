package com.it.insidetowns.theinsidetowns.objects;

/**
 * Created by Banana on 12-Sep-17.
 */

public class EventSubCategoryObject {

    private String EventCat_name,Cat_Image;

    public String getEventCat_name() {
        return EventCat_name;
    }

    public void setEventCat_name(String eventCat_name) {
        EventCat_name = eventCat_name;
    }

    public String getCat_Image() {
        return Cat_Image;
    }

    public void setCat_Image(String cat_Image) {
        Cat_Image = cat_Image;
    }

    public String getEventCatid() {
        return EventCatid;
    }

    public void setEventCatid(String eventCatid) {
        EventCatid = eventCatid;
    }

    String EventCatid;


   public EventSubCategoryObject() {
   }

    public EventSubCategoryObject(String EventCatid, String Cat_Image, String EventCat_name) {
        this.EventCatid = EventCatid;
        this.Cat_Image = Cat_Image;
        this.EventCat_name = EventCat_name;

    }



}
