package com.it.insidetowns.theinsidetowns.objects.FavObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavCatDetails
{

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getCat_id() {
        return Cat_id;
    }

    public void setCat_id(Integer cat_id) {
        Cat_id = cat_id;
    }

    public String getCategory_name() {
        return Category_name;
    }

    public void setCategory_name(String category_name) {
        Category_name = category_name;
    }

    @SerializedName("Cat_id")
    @Expose
    private Integer Cat_id;

    @SerializedName("Category_name")
    @Expose
    private String Category_name;


    public String getCat_Image() {
        return Cat_Image;
    }

    public void setCat_Image(String cat_Image) {
        Cat_Image = cat_Image;
    }

    @SerializedName("Cat_Image")
    @Expose
    private String Cat_Image;

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    @SerializedName("Type")
    @Expose
    private Integer Type;


}