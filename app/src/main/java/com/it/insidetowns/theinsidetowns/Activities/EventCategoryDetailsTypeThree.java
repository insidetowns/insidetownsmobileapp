package com.it.insidetowns.theinsidetowns.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
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
import com.it.insidetowns.theinsidetowns.Adapter.CategoryDetailsTypeThreeAdapter;
import com.it.insidetowns.theinsidetowns.R;
import com.it.insidetowns.theinsidetowns.objects.CategoryObjectNew;
import com.it.insidetowns.theinsidetowns.objects.CategoryObjectTypeThree;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventCategoryDetailsTypeThree extends AppCompatActivity {
    RecyclerView rView2;
    private GridLayoutManager lLayout2;
    LinearLayout BackArrow,Home;
    TextView sub_catName;

    LinearLayout loaderBg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        rView2 = (RecyclerView) findViewById(R.id.cat_details_rcv);
        BackArrow = findViewById(R.id.BackArrow);
        loaderBg = (LinearLayout) findViewById(R.id.loaderBg);
      //  sub_catName = findViewById(R.id.sub_catName);

        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventCategoryDetailsTypeThree.super.onBackPressed();
            }
        });

        Home = findViewById(R.id.Home);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventCategoryDetailsTypeThree.this, Home.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
                finish();
            }
        });

        ShopApi();


      //  List<CategoryObjectNew> rowListItem2 = getAllItemList2();

       /* CategoryDetailsAdapter rcAdapter = new CategoryDetailsAdapter(this, rowListItem2);
        lLayout2 = new GridLayoutManager(this, 1);

        rView2.setHasFixedSize(true);
        rView2.setLayoutManager(lLayout2);
        rView2.setAdapter(rcAdapter);*/


    }

    private void ShopApi() {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(EventCategoryDetailsTypeThree.this);
        String Subcategory_Id = sharedPrefs.getString("Subcategory_Id", "");
        String Lat = sharedPrefs.getString("Lat", "");
        String Long = sharedPrefs.getString("Long", "");
     //  sub_catName.setText(""+ sharedPrefs.getString("CatName", ""));

        loaderBg.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(EventCategoryDetailsTypeThree.this);

        final StringRequest reqQueue = new StringRequest(Request.Method.GET, ""+getResources().getString(R.string.AppURL)+"api/Events/Events?lat="+Lat+"&longitude="+Long+"&catid="+Subcategory_Id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loaderBg.setVisibility(View.GONE);
                Toast.makeText(EventCategoryDetailsTypeThree.this, "Data loaded successfully...", Toast.LENGTH_SHORT);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("EventDetails");
                    //    Log.e("030819 "," "+jsonArray);
                    //   JSONArray jsonArray = new JSONArray(jsonObject.get("CategoriesDetails"));
                    //  Log.e("030819 "," jsonArray "+jsonArray);
                    if (jsonArray.length() > 0) {



                    List<CategoryObjectTypeThree> allItems = new ArrayList<CategoryObjectTypeThree>();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        //  allItems.add(new CategoryObjectNew("1",R.mipmap.fashion,"Palm beach concert", "Beach Road","simply dummy text of the printing and typesetting industry.  "));

                        //   String ss = jsonArray.getJSONObject(i).getString("Category_name");
                        //     Log.e("030819 "," ss "+ss);
                        allItems.add(new CategoryObjectTypeThree("" + jsonArray.getJSONObject(i).getString("Event_Id"), "" + "" + jsonArray.getJSONObject(i).getString("Event_Image"), "" + jsonArray.getJSONObject(i).getString("Title"), "" + "" + jsonArray.getJSONObject(i).getString("Event_Discount"), "" + "" + jsonArray.getJSONObject(i).getString("Description")));


                    }
                    CategoryDetailsTypeThreeAdapter rcAdapter = new CategoryDetailsTypeThreeAdapter(EventCategoryDetailsTypeThree.this, allItems);
                    lLayout2 = new GridLayoutManager(EventCategoryDetailsTypeThree.this, 1);

                    rView2.setHasFixedSize(true);
                    rView2.setLayoutManager(lLayout2);
                    rView2.setAdapter(rcAdapter);
                }
                    else
                    {
                        //    Log.e("110819 "," no data ");
                        JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                        //    Log.e("110819 "," no data "+jsonObject1.getString("Message"));
                    //    Toast.makeText(EventCategoryDetailsTypeThree.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_LONG).show();
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAllMsg(EventCategoryDetailsTypeThree.this, "m1",""+jsonObject1.getString("Message"));

                    }
/*
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(Login.this);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString("ID", ""+jsonObject.getString("ID"));
                    editor.commit();
                    Intent intent = new Intent(getContext(), Home.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
                    finish();*/
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
                    b.ShowAnimatedDialogAllMsg(EventCategoryDetailsTypeThree.this, "m1",""+message);
                } else if (volleyError instanceof ServerError) {

                    if (volleyError.networkResponse.statusCode == 417) {
                        message = "Invalid credentials. Please try again...";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAllMsg(EventCategoryDetailsTypeThree.this, "m1",""+message);


                    }
                    else
                    if (volleyError.networkResponse.statusCode == 500) {
                        //   message = "Server could not be found. Please check.";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(EventCategoryDetailsTypeThree.this, "3");
                    } else {
                        //   message = "The server could not be found. Please try again after some time!!";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(EventCategoryDetailsTypeThree.this, "3");
                    }
                } else if (volleyError instanceof AuthFailureError) {
                    //    message = "Cannot connect to Internet...Please check your connection!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(EventCategoryDetailsTypeThree.this, "1");
                } else if (volleyError instanceof ParseError) {
                    //   message = "Parsing error! Please try again after some time!!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(EventCategoryDetailsTypeThree.this, "4");
                } else if (volleyError instanceof NoConnectionError) {
                    //    message = "Cannot connect to Internet...Please check your connection!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(EventCategoryDetailsTypeThree.this, "1");
                } else if (volleyError instanceof TimeoutError) {
                    //    message = "Connection TimeOut! Please check your internet connection.";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(EventCategoryDetailsTypeThree.this, "8");
                }
                //   Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_LONG).show();
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
                  //  Toast.makeText(EventCategoryDetailsTypeThree.this, "Some error occurred. Please try again", Toast.LENGTH_LONG).show();
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(EventCategoryDetailsTypeThree.this, "6");

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

    private List<CategoryObjectNew> getAllItemList2() {
        List<CategoryObjectNew> allItems = new ArrayList<CategoryObjectNew>();
      /*  allItems.add(new CategoryObject("1",R.mipmap.fashion,"Palm beach concert", "Beach Road","simply dummy text of the printing and typesetting industry.  "));
        allItems.add(new CategoryObject("2",R.mipmap.beauty,"Novotel resort event","Bhemili","simply dummy text of the printing and typesetting industry.  "));
        allItems.add(new CategoryObject("3",R.mipmap.events,"Navy day 2019","Beach Road","simply dummy text of the printing and typesetting industry.  "));
        allItems.add(new CategoryObject("4",R.mipmap.travel,"Speed boat championship", "Beach Road","simply dummy text of the printing and typesetting industry.  "));


        allItems.add(new CategoryObject("5",R.mipmap.bikes,"Bikes", "Bhemili","simply dummy text of the printing and typesetting industry.  "));
        allItems.add(new CategoryObject("6",R.mipmap.house_keeping,"House Keeping","Bhemili","simply dummy text of the printing and typesetting industry.  "));
        allItems.add(new CategoryObject("7",R.mipmap.food,"Food","Beach Road","simply dummy text of the printing and typesetting industry.  "));
        allItems.add(new CategoryObject("8",R.mipmap.fitness,"Fitness", "Bhemili","simply dummy text of the printing and typesetting industry.  "));
       */ return allItems;
    }
}
