package com.it.insidetowns.theinsidetowns.objects.SubCatSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.it.insidetowns.theinsidetowns.objects.FavObjects.FMessage;
import com.it.insidetowns.theinsidetowns.objects.SubCategoryObject;

import java.util.List;

public class SubCatListSearch {





    public FMessage getfMessage() {
        return fMessage;
    }

    public void setfMessage(FMessage fMessage) {
        this.fMessage = fMessage;
    }

    public List<SubCategoryObject> getSubCategoryObjects() {
        return subCategoryObjects;
    }

    public void setSubCategoryObjects(List<SubCategoryObject> subCategoryObjects) {
        this.subCategoryObjects = subCategoryObjects;
    }

    @SerializedName("SubCategoriesDetails")
    @Expose
    private List<SubCategoryObject> subCategoryObjects;



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
