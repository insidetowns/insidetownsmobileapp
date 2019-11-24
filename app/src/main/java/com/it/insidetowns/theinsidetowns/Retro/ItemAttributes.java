package com.it.insidetowns.theinsidetowns.Retro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemAttributes {


    public String getCategory_name() {
        return Category_name;
    }

    public void setCategory_name(String category_name) {
        Category_name = category_name;
    }

    @SerializedName("Category_name")
    @Expose
    private String Category_name;








    public ItemAttributes(String Category_name) {

        this.Category_name = Category_name;

    }


}
