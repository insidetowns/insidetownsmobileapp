package com.it.insidetowns.theinsidetowns.objects;

public class InfoImagesDetails {


    /**
     * Image_Id : 3
     * Image_Url : http://theitapp.tech/Images/ProductImage/ProductImage_293_25
     */

    private int Image_Id;
    private String Image_Url;

    public InfoImagesDetails(int image_id, String image_url) {
        Image_Id = image_id;
        Image_Url=image_url;
    }

    public int getImage_Id() {
        return Image_Id;
    }

    public void setImage_Id(int Image_Id) {
        this.Image_Id = Image_Id;
    }

    public String getImage_Url() {
        return Image_Url;
    }

    public void setImage_Url(String Image_Url) {
        this.Image_Url = Image_Url;
    }
}
