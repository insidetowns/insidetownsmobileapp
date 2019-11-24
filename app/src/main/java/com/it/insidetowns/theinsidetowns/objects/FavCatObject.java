package com.it.insidetowns.theinsidetowns.objects;

/**
 * Created by Banana on 12-Sep-17.
 */

public class FavCatObject {


    private String id,Cat_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_id() {
        return Cat_id;
    }

    public void setCat_id(String cat_id) {
        Cat_id = cat_id;
    }

    public String getCategory_name() {
        return Category_name;
    }

    public void setCategory_name(String category_name) {
        Category_name = category_name;
    }

    public String getCat_Image() {
        return Cat_Image;
    }

    public void setCat_Image(String cat_Image) {
        Cat_Image = cat_Image;
    }

    String Category_name;


    String Cat_Image;

   public FavCatObject() {
   }

    public FavCatObject(String id, String Cat_id, String Category_name, String Cat_Image) {
        this.id = id;
        this.Cat_id = Cat_id;
        this.Category_name = Category_name;
        this.Cat_Image = Cat_Image;

    }



    /* public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }*/
}
