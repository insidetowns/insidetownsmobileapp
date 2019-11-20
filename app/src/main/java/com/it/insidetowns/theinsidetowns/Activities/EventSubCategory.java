package com.it.insidetowns.theinsidetowns.Activities;

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
import com.it.insidetowns.theinsidetowns.Adapter.EventSubCategoryAdapter;
import com.it.insidetowns.theinsidetowns.R;
import com.it.insidetowns.theinsidetowns.objects.CategoryObject;
import com.it.insidetowns.theinsidetowns.objects.SubCategoryObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventSubCategory extends AppCompatActivity {
    RecyclerView rView2;
    LinearLayout BackArrow;
    TextView sub_catName;
    private GridLayoutManager lLayout2;
    LinearLayout loaderBg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_category);
        rView2 = (RecyclerView) findViewById(R.id.cat_details_rcv);
        BackArrow = findViewById(R.id.BackArrow);
        sub_catName  = findViewById(R.id.sub_catName);
        loaderBg = (LinearLayout) findViewById(R.id.loaderBg);
        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventSubCategory.super.onBackPressed();
            }
        });


        SubCategoriesApiCall();


      /*  List<CategoryObject> rowListItem2 = getAllItemList2();

        SubCategoryAdapter rcAdapter = new SubCategoryAdapter(this, rowListItem2);
        lLayout2 = new GridLayoutManager(this, 1);

        rView2.setHasFixedSize(true);
        rView2.setLayoutManager(lLayout2);
        rView2.setAdapter(rcAdapter);*/


    }

    private void SubCategoriesApiCall() {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(EventSubCategory.this);
        String CatId = sharedPrefs.getString("CatId", "");
        sub_catName.setText(""+ sharedPrefs.getString("CatName", ""));

        loaderBg.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(EventSubCategory.this);
        final StringRequest reqQueue = new StringRequest(Request.Method.GET, ""+getResources().getString(R.string.AppURL)+"api/Common/EventCategories", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loaderBg.setVisibility(View.GONE);
                Toast.makeText(EventSubCategory.this, "Data loaded successfully...", Toast.LENGTH_SHORT);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("Eventcatlist");
                //    Log.e("030819 "," "+jsonArray);
                    //   JSONArray jsonArray = new JSONArray(jsonObject.get("CategoriesDetails"));
                    //  Log.e("030819 "," jsonArray "+jsonArray);
                    if(jsonArray.length()>0) {
                    List<SubCategoryObject> allItems = new ArrayList<SubCategoryObject>();

                    for(int i=0;i<jsonArray.length();i++)
                    {

                        //   String ss = jsonArray.getJSONObject(i).getString("Category_name");
                        //     Log.e("030819 "," ss "+ss);
                        allItems.add(new SubCategoryObject(""+jsonArray.getJSONObject(i).getString("EventCatid"),""+""+jsonArray.getJSONObject(i).getString("Cat_Image"),""+jsonArray.getJSONObject(i).getString("EventCat_name"), ""+jsonArray.getJSONObject(i).getString("count"),""));


                    }

                    EventSubCategoryAdapter rcAdapter = new EventSubCategoryAdapter(EventSubCategory.this, allItems);
                    lLayout2 = new GridLayoutManager(EventSubCategory.this, 1);
                    rView2.setHasFixedSize(true);
                    rView2.setLayoutManager(lLayout2);
                    rView2.setAdapter(rcAdapter);
                    rView2.setNestedScrollingEnabled(false);
                    }
                    else
                    {
                        //    Log.e("110819 "," no data ");
                        JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                        //    Log.e("110819 "," no data "+jsonObject1.getString("Message"));
                     //   Toast.makeText(EventSubCategory.this, ""+jsonObject1.getString("Message"), Toast.LENGTH_LONG).show();
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAllMsg(EventSubCategory.this, "m1",""+jsonObject1.getString("Message"));

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
                    b.ShowAnimatedDialogAllMsg(EventSubCategory.this, "m1",""+message);
                } else if (volleyError instanceof ServerError) {

                    if (volleyError.networkResponse.statusCode == 417) {
                        message = "Invalid credentials. Please try again...";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAllMsg(EventSubCategory.this, "m1",""+message);


                    }
                    else
                    if (volleyError.networkResponse.statusCode == 500) {
                        //   message = "Server could not be found. Please check.";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(EventSubCategory.this, "3");
                    } else {
                        //   message = "The server could not be found. Please try again after some time!!";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(EventSubCategory.this, "3");
                    }
                } else if (volleyError instanceof AuthFailureError) {
                    //    message = "Cannot connect to Internet...Please check your connection!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(EventSubCategory.this, "1");
                } else if (volleyError instanceof ParseError) {
                    //   message = "Parsing error! Please try again after some time!!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(EventSubCategory.this, "4");
                } else if (volleyError instanceof NoConnectionError) {
                    //    message = "Cannot connect to Internet...Please check your connection!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(EventSubCategory.this, "1");
                } else if (volleyError instanceof TimeoutError) {
                    //    message = "Connection TimeOut! Please check your internet connection.";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(EventSubCategory.this, "8");
                }
                //   Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonObject = new JSONObject();


                    // jsonObject.put("Firstname", ""+name.getText().toString());
                    // jsonObject.put("Email", ""+Email.getText().toString());
                    // jsonObject.put("Password", ""+Password.getText().toString());
                    //jsonObject.put("Phone", ""+phone.getText().toString());


                    return jsonObject.toString().getBytes("utf-8");
                } catch (Exception ex) {
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(EventSubCategory.this, "6");
                 //   Toast.makeText(EventSubCategory.this, "Some error occurred. Please try again", Toast.LENGTH_LONG).show();

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


    private List<CategoryObject> getAllItemList2() {
        List<CategoryObject> allItems = new ArrayList<CategoryObject>();
      /*  allItems.add(new SubCategoryObject("1",R.mipmap.imga,"School Events", "35","simply dummy text of the printing and typesetting industry.  "));
        allItems.add(new SubCategoryObject("2",R.mipmap.imgb,"Sports Events","22","simply dummy text of the printing and typesetting industry.  "));
        allItems.add(new SubCategoryObject("3",R.mipmap.imgc,"Concerts","43","simply dummy text of the printing and typesetting industry.  "));
        allItems.add(new SubCategoryObject("4",R.mipmap.imgd,"Beach party", "9","simply dummy text of the printing and typesetting industry.  "));


        allItems.add(new SubCategoryObject("5",R.mipmap.imge,"Movie audio release", "55","simply dummy text of the printing and typesetting industry.  "));
 */       return allItems;
    }
}
