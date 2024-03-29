package com.it.insidetowns.theinsidetowns.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.it.insidetowns.theinsidetowns.BuildConfig;
import com.it.insidetowns.theinsidetowns.R;
import com.it.insidetowns.theinsidetowns.objects.BannerDetails;
import com.it.insidetowns.theinsidetowns.objects.InfoImagesDetails;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InfoPage extends AppCompatActivity {
    RelativeLayout GetDiscount;
    LinearLayout loaderBg, Home;

    LinearLayout BackArrow;
    TextView location, name, discount, desc, discount_text, Website, discCode;
    ImageView img;
    String imgUrl = "";
    ImageView ivWeb, ivPhone,ivShare;
    String web, call;
    private LinearLayout mGallery;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);

        mInflater = LayoutInflater.from(getApplicationContext());
        GetDiscount = findViewById(R.id.GetDiscount);
        name = findViewById(R.id.name);
        discount = findViewById(R.id.discount);
        Website = findViewById(R.id.Website);
        discount_text = findViewById(R.id.discount_text);
        desc = findViewById(R.id.desc);
        location = findViewById(R.id.location);
        discCode = findViewById(R.id.discCode);
        img = findViewById(R.id.img);
        Home = findViewById(R.id.Home);
        mGallery = (LinearLayout) findViewById(R.id.id_gallery);
        loaderBg = (LinearLayout) findViewById(R.id.loaderBg);
        BackArrow = findViewById(R.id.BackArrow);
        loaderBg = (LinearLayout) findViewById(R.id.loaderBg);
        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfoPage.super.onBackPressed();
            }
        });

        Home = findViewById(R.id.Home);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoPage.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
                finish();
            }
        });
        Website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoPage.this, WebViewShow.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
            }
        });

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(InfoPage.this);
        String CatName = sharedPrefs.getString("CatName", "");
        String type = sharedPrefs.getString("type", "");
        Log.e("080919  ", " " + type);
        if (type.trim().equalsIgnoreCase("5")) {
            Log.e("080919  ", " event");
            InfoApiEvent();

        } else {
            Log.e("080919  ", " product");
            InfoApi();
        }

        GetDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(InfoPage.this);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("ProductDiscount", "" + discount.getText().toString().trim());
                editor.putString("Product_Image", "" + imgUrl);
                editor.putString("Product_Title", "" + name.getText().toString());
                editor.commit();

                Intent intent = new Intent(InfoPage.this, Code.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
                //  finish();
            }
        });

        ivPhone = findViewById(R.id.iv_call);
        ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (call != null) {
                    callTo(call);
                } else {
                    Toast.makeText(InfoPage.this, "No phone linked", Toast.LENGTH_SHORT).show();
                }

            }
        });
        ivWeb = findViewById(R.id.iv_web);
        ivWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (web != null) {
                    openUrlInChrome(web);
                } else {
                    Toast.makeText(InfoPage.this, "No Website linked", Toast.LENGTH_SHORT).show();
                }

            }
        });
 ivShare = findViewById(R.id.iv_share);
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    shareData();
            }
        });
    }

    void openUrlInChrome(String url) {
        try {
            try {
                Uri uri = Uri.parse("googlechrome://navigate?url=" + url);
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            } catch (ActivityNotFoundException e) {
                Uri uri = Uri.parse(url);
                // Chrome is probably not installed
                // OR not selected as default browser OR if no Browser is selected as default browser
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "No website linked", Toast.LENGTH_SHORT).show();
        }
    }

    public void callTo(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    private void InfoApiEvent() {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(InfoPage.this);
        String Product_Id = sharedPrefs.getString("Product_Id", "");

        Log.e("200819 ", " Product_Id " + Product_Id);

        /* String Lat = sharedPrefs.getString("Lat", "");
        String Long = sharedPrefs.getString("Long", "");*/
        //  sub_catName.setText(""+ sharedPrefs.getString("CatName", ""));

        loaderBg.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(InfoPage.this);
        final StringRequest reqQueue = new StringRequest(Request.Method.GET, "" + getResources().getString(R.string.AppURL) + "api/Events/EventByEventID?eventid=" + Product_Id, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loaderBg.setVisibility(View.GONE);
                //    Toast.makeText(InfoPage.this, "Data loaded successfully...", Toast.LENGTH_SHORT);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(InfoPage.this);


                    location.setText("" + jsonObject.getString("VenueName"));
                    name.setText("" + jsonObject.getString("Title"));
                    desc.setText("" + jsonObject.getString("Description"));
                    discount.setText("" + jsonObject.getString("Event_Discount"));
                    getPhoneWebsite(jsonObject);
                    imgUrl = "" + jsonObject.getString("Event_Image");
                    //   discCode.setText(""+jsonObject.getString("Event_Discount"));

                    Log.d("____check2", "" + web + " --------" + call);
                    sharedPrefs = PreferenceManager.getDefaultSharedPreferences(InfoPage.this);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString("Product_Id", "" + jsonObject.getString("Event_Id"));
                    editor.putString("Shop_id", "" + jsonObject.getString("Venue_Id"));

                    editor.commit();


                    JSONArray jsonArray = jsonObject.getJSONArray("Images");
                    ArrayList<InfoImagesDetails> infoImagesDetails = new ArrayList<InfoImagesDetails>();

                    for(int i =0;i<jsonArray.length();i++){
                        infoImagesDetails.add(new InfoImagesDetails(jsonArray.getJSONObject(i).getInt("Image_Id"), "" + "" + jsonArray.getJSONObject(i).getString("Image_Url")));


                        Log.e("Info Image API "+i,""+infoImagesDetails.get(i).getImage_Id());



                    }


                    if(infoImagesDetails.size()>0){


                        for (int i = 0; i < infoImagesDetails.size(); i++) {
                            mInflater = LayoutInflater.from(getApplicationContext());
                            View view = mInflater.inflate(R.layout.activity_gallery_item_flag,
                                    mGallery, false);
                            ImageView img123 = (ImageView) view.findViewById(R.id.id_index_gallery_item_image);
                       /* ImageView shopImage = (ImageView) view.findViewById(R.id.shopImage);

                        final RelativeLayout llDiscount = (RelativeLayout) view.findViewById(R.id.llDiscount);
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beauty);
                        TextView tv = (TextView) view
                                .findViewById(R.id.tv);
                        final TextView ProductId = (TextView) view.findViewById(R.id.ProductId);
                        final TextView Ptype = (TextView) view.findViewById(R.id.Ptype);
                        //  Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "helvetica_medium.ttf");
                        final TextView location = (TextView) view.findViewById(R.id.location);
                        final TextView Shop_Name = (TextView) view.findViewById(R.id.Shop_Name);*/

                            Transformation transformation = new RoundedTransformationBuilder()
                                    .borderColor(Color.WHITE)
                                    .borderWidthDp(1)
                                    .cornerRadiusDp(20)
                                    .build();


                        /*tv.setText(bannerDetails.get(i).getDiscount());
                        ProductId.setText(bannerDetails.get(i).getProductId());
                        Ptype.setText(bannerDetails.get(i).getPtype());
                        Shop_Name.setText(bannerDetails.get(i).getName());*/




//                        location.setText("" + bannerDetails.get(i).getType());
                            Picasso.with(getApplicationContext())
                                    .load(infoImagesDetails.get(i).getImage_Url())
                                    .fit()
                                    .transform(transformation)
                                    .into(img123);



                            mGallery.addView(view);

                        }
                    }else {
                        mGallery.setVisibility(View.GONE);

                        //      discount_text.setText("11 "+""+jsonObject.getString("Shop_Name"));
                        Transformation transformation = new RoundedTransformationBuilder()
                                .borderColor(Color.WHITE)
                                .borderWidthDp(1)
                                .cornerRadiusDp(8)
                                .build();


                        Picasso.with(InfoPage.this)
                                .load("" + jsonObject.getString("Event_Image"))
                                .fit()
                                .transform(transformation)
                                .into(img);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                loaderBg.setVisibility(View.GONE);
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "No SubCategories are registered.";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(InfoPage.this, "7");
                } else if (volleyError instanceof ServerError) {

                    if (volleyError.networkResponse.statusCode == 417) {
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(InfoPage.this, "7");
                        //   message = "No SubCategories are registered.";
                    } else if (volleyError.networkResponse.statusCode == 500) {
                        //   message = "Server could not be found. Please check.";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(InfoPage.this, "3");
                    } else {
                        //   message = "The server could not be found. Please try again after some time!!";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(InfoPage.this, "3");
                    }

                } else if (volleyError instanceof AuthFailureError) {
                    //    message = "Cannot connect to Internet...Please check your connection!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(InfoPage.this, "1");
                } else if (volleyError instanceof ParseError) {
                    //   message = "Parsing error! Please try again after some time!!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(InfoPage.this, "4");
                } else if (volleyError instanceof NoConnectionError) {
                    //    message = "Cannot connect to Internet...Please check your connection!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(InfoPage.this, "1");
                } else if (volleyError instanceof TimeoutError) {
                    //    message = "Connection TimeOut! Please check your internet connection.";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(InfoPage.this, "1");
                }
                //      Toast.makeText(InfoPage.this, "Error: " + message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonObject = new JSONObject();


                    // jsonObject.put("Firstname", ""+name.getText().toString());
                    //    jsonObject.put("Email", ""+Email.getText().toString());
                    //    jsonObject.put("Password", ""+Password.getText().toString());
                    //jsonObject.put("Phone", ""+phone.getText().toString());


                    return jsonObject.toString().getBytes("utf-8");
                } catch (Exception ex) {
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(InfoPage.this, "6");
                    //    Toast.makeText(InfoPage.this, "Some error occurred. Please try again", Toast.LENGTH_LONG).show();
                }
                return super.getBody();
            }


            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(reqQueue);
    }

    private void InfoApi() {

        final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(InfoPage.this);
        String Product_Id = sharedPrefs.getString("Product_Id", "");
        Log.e("200819 ", " Product_Iddd " + Product_Id);

        String API = "";

        String type = sharedPrefs.getString("type", "");
        Log.e("200819 ", " type " + type);
        if (type.equalsIgnoreCase("3")) {

            API = "api/Product/ProductDetailsByProductID?productid=" + Product_Id;
        } else {
            Log.e("180819 ", " type4 shopid " + Product_Id);
            API = "api/ProductType/ProductDetailsByProID?productid=" + Product_Id;
        }

        final String city = sharedPrefs.getString("city", "");
        final String shopName = sharedPrefs.getString("shopName", "");
        discount_text.setText("" + shopName);
//        web = sharedPrefs.getString("Website", "");
//        call = sharedPrefs.getString("mobile", "");
//        Log.d("____check", "" + web + " --------" + call);
        /* String Lat = sharedPrefs.getString("Lat", "");
        String Long = sharedPrefs.getString("Long", "");*/
        //  sub_catName.setText(""+ sharedPrefs.getString("CatName", ""));

        loaderBg.setVisibility(View.VISIBLE);
        Log.e("Api ",""+API);
        RequestQueue queue = Volley.newRequestQueue(InfoPage.this);
        final StringRequest reqQueue = new StringRequest(Request.Method.GET, "" + getResources().getString(R.string.AppURL) + API, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loaderBg.setVisibility(View.GONE);
                Toast.makeText(InfoPage.this, "Data loaded successfully...", Toast.LENGTH_SHORT);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    location.setText("" + city);
                    name.setText("" + jsonObject.getString("Product_Title"));
                    desc.setText("" + jsonObject.getString("Description"));

                    if (!TextUtils.isEmpty(jsonObject.getString("Website")))
                        Website.setText("" + jsonObject.getString("Website"));
                    Website.setPaintFlags(Website.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    //   discount.setText(""+jsonObject.getString("ProductDiscount"));
                    imgUrl = "" + jsonObject.getString("Product_Image");
                    // location.setText(""+jsonObject.getString("Address"));
                    Log.e("030819 ", " " + jsonObject.getString("Product_Image"));

                    getPhoneWebsite(jsonObject);
                    Log.d("____check3", "" + web + " --------" + call);
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(InfoPage.this);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString("Product_Id", "" + jsonObject.getString("Product_Id"));
                    editor.putString("Shop_id", "" + jsonObject.getString("Shop_Id"));
                    editor.putString("Description", "" + jsonObject.getString("Description"));

                    editor.putString("Website", "" + jsonObject.getString("Website"));
                    editor.commit();

                    JSONArray jsonArray = jsonObject.getJSONArray("Images");
                    ArrayList<InfoImagesDetails> infoImagesDetails = new ArrayList<InfoImagesDetails>();

                    for(int i =0;i<jsonArray.length();i++){
                            infoImagesDetails.add(new InfoImagesDetails(jsonArray.getJSONObject(i).getInt("Image_Id"), "" + "" + jsonArray.getJSONObject(i).getString("Image_Url")));


                            Log.e("Info Image API "+i,""+infoImagesDetails.get(i).getImage_Id());



                    }


                    if(infoImagesDetails.size()>0){


                    for (int i = 0; i < infoImagesDetails.size(); i++) {
                        mInflater = LayoutInflater.from(getApplicationContext());
                        View view = mInflater.inflate(R.layout.activity_gallery_item_flag,
                                mGallery, false);
                        ImageView img123 = (ImageView) view.findViewById(R.id.id_index_gallery_item_image);
                       /* ImageView shopImage = (ImageView) view.findViewById(R.id.shopImage);

                        final RelativeLayout llDiscount = (RelativeLayout) view.findViewById(R.id.llDiscount);
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beauty);
                        TextView tv = (TextView) view
                                .findViewById(R.id.tv);
                        final TextView ProductId = (TextView) view.findViewById(R.id.ProductId);
                        final TextView Ptype = (TextView) view.findViewById(R.id.Ptype);
                        //  Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "helvetica_medium.ttf");
                        final TextView location = (TextView) view.findViewById(R.id.location);
                        final TextView Shop_Name = (TextView) view.findViewById(R.id.Shop_Name);*/

                        Transformation transformation = new RoundedTransformationBuilder()
                                .borderColor(Color.WHITE)
                                .borderWidthDp(1)
                                .cornerRadiusDp(20)
                                .build();


                        /*tv.setText(bannerDetails.get(i).getDiscount());
                        ProductId.setText(bannerDetails.get(i).getProductId());
                        Ptype.setText(bannerDetails.get(i).getPtype());
                        Shop_Name.setText(bannerDetails.get(i).getName());*/




//                        location.setText("" + bannerDetails.get(i).getType());
                        Picasso.with(getApplicationContext())
                                .load(infoImagesDetails.get(i).getImage_Url())
                                .fit()
                                .transform(transformation)
                                .into(img123);



                            mGallery.addView(view);

                    }
                    }else {
                    mGallery.setVisibility(View.GONE);

                        //      discount_text.setText("11 "+""+jsonObject.getString("Shop_Name"));
                        Transformation transformation = new RoundedTransformationBuilder()
                                .borderColor(Color.WHITE)
                                .borderWidthDp(1)
                                .cornerRadiusDp(8)
                                .build();


                        Picasso.with(InfoPage.this)
                                .load("" + jsonObject.getString("Product_Image"))
                                .fit()
                                .transform(transformation)
                                .into(img);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                loaderBg.setVisibility(View.GONE);
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "No SubCategories are registered.";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(InfoPage.this, "7");
                } else if (volleyError instanceof ServerError) {

                    if (volleyError.networkResponse.statusCode == 417) {
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(InfoPage.this, "7");
                        //   message = "No SubCategories are registered.";
                    } else if (volleyError.networkResponse.statusCode == 500) {
                        //   message = "Server could not be found. Please check.";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(InfoPage.this, "3");
                    } else {
                        //   message = "The server could not be found. Please try again after some time!!";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(InfoPage.this, "3");
                    }

                } else if (volleyError instanceof AuthFailureError) {
                    //    message = "Cannot connect to Internet...Please check your connection!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(InfoPage.this, "1");
                } else if (volleyError instanceof ParseError) {
                    //   message = "Parsing error! Please try again after some time!!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(InfoPage.this, "4");
                } else if (volleyError instanceof NoConnectionError) {
                    //    message = "Cannot connect to Internet...Please check your connection!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(InfoPage.this, "1");
                } else if (volleyError instanceof TimeoutError) {
                    //    message = "Connection TimeOut! Please check your internet connection.";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(InfoPage.this, "8");
                }
                //      Toast.makeText(InfoPage.this, "Error: " + message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonObject = new JSONObject();


                    // jsonObject.put("Firstname", ""+name.getText().toString());
                    //    jsonObject.put("Email", ""+Email.getText().toString());
                    //    jsonObject.put("Password", ""+Password.getText().toString());
                    //jsonObject.put("Phone", ""+phone.getText().toString());


                    return jsonObject.toString().getBytes("utf-8");
                } catch (Exception ex) {
                    //   Toast.makeText(InfoPage.this, "Some error occurred. Please try again", Toast.LENGTH_LONG).show();
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(InfoPage.this, "6");
                }
                return super.getBody();
            }


            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(reqQueue);
    }

    private void getPhoneWebsite(JSONObject jsonObject) throws JSONException {
        if (jsonObject.has("Website") && jsonObject.getString("Website") != null && !jsonObject.getString("Website").equalsIgnoreCase("null")) {
            web = jsonObject.getString("Website");
        } else {
            ivWeb.setVisibility(View.GONE);
        }
        if (jsonObject.has("Phone") && jsonObject.getString("Phone") != null && !jsonObject.getString("Phone").equalsIgnoreCase("null")) {
            call = jsonObject.getString("Phone");
        } else {
            ivPhone.setVisibility(View.GONE);
        }
    }

    private void shareData(){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "InsideTowns");
            String shareMessage= "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }
}