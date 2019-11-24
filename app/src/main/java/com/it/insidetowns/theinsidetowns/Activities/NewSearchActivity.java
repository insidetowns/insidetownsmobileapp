package com.it.insidetowns.theinsidetowns.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.it.insidetowns.theinsidetowns.Adapter.CategoryDetailsTypeThreeAdapter;
import com.it.insidetowns.theinsidetowns.Adapter.CategoryDetailsTypeThreeAdapterSearch;
import com.it.insidetowns.theinsidetowns.Adapter.SubCategoryAdapterSearch;
import com.it.insidetowns.theinsidetowns.R;
import com.it.insidetowns.theinsidetowns.network.RestApi;
import com.it.insidetowns.theinsidetowns.objects.CategoryObjectTypeThree;
import com.it.insidetowns.theinsidetowns.objects.EventSearch.EventListSearch;
import com.it.insidetowns.theinsidetowns.objects.ProductSearch.ProductListSearch;
import com.it.insidetowns.theinsidetowns.objects.ShopSearch.ShopListSearch;
import com.it.insidetowns.theinsidetowns.objects.SubCatSearch.SubCatListSearch;
import com.it.insidetowns.theinsidetowns.objects.SubCategoryObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class NewSearchActivity extends AppCompatActivity {
    //  RecyclerView rView2;
    LinearLayout BackArrow;
    TextView sub_catName;
    private GridLayoutManager lLayout2;
    LinearLayout loaderBg, show;
    EditText searchText;
    RecyclerView rView2;
    TextView noData;
    //  private Toolbar toolbar;


    Button Restaurants, BarsClubs, events, Others;
    ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_search);

        BackArrow = findViewById(R.id.BackArrow);
        searchText = findViewById(R.id.searchText);

        Restaurants = (Button) findViewById(R.id.Restaurants);
        BarsClubs = (Button) findViewById(R.id.BarsClubs);
        events = (Button) findViewById(R.id.events);
        Others = (Button) findViewById(R.id.Others);

        rView2 = findViewById(R.id.cat_details_rcv);
        loaderBg = findViewById(R.id.loaderBg);
        noData = findViewById(R.id.noData);

        searchText.requestFocus();

        ProductsAPI();


        sub_catName = findViewById(R.id.sub_catName);
        loaderBg = (LinearLayout) findViewById(R.id.loaderBg);
        show = (LinearLayout) findViewById(R.id.show);
        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewSearchActivity.super.onBackPressed();
            }
        });


        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Restaurants.setTextColor(getResources().getColor(R.color.white));

                BarsClubs.setTextColor(getResources().getColor(R.color.gray));

                events.setTextColor(getResources().getColor(R.color.gray));

                Others.setTextColor(getResources().getColor(R.color.gray));

                //   CodesApiCall("1");
                ProductsAPI();

            }
        });


        Restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Restaurants.setTextColor(getResources().getColor(R.color.white));

                BarsClubs.setTextColor(getResources().getColor(R.color.gray));

                events.setTextColor(getResources().getColor(R.color.gray));

                Others.setTextColor(getResources().getColor(R.color.gray));

                //   CodesApiCall("1");
                ProductsAPI();
            }


        });

        BarsClubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restaurants.setTextColor(getResources().getColor(R.color.gray));

                BarsClubs.setTextColor(getResources().getColor(R.color.white));

                events.setTextColor(getResources().getColor(R.color.gray));

                Others.setTextColor(getResources().getColor(R.color.gray));

                SubCatAPI();
            }


        });


        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restaurants.setTextColor(getResources().getColor(R.color.gray));

                BarsClubs.setTextColor(getResources().getColor(R.color.gray));

                events.setTextColor(getResources().getColor(R.color.white));

                Others.setTextColor(getResources().getColor(R.color.gray));

                EventAPI();

            }


        });


        Others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restaurants.setTextColor(getResources().getColor(R.color.gray));

                BarsClubs.setTextColor(getResources().getColor(R.color.gray));

                events.setTextColor(getResources().getColor(R.color.gray));

                Others.setTextColor(getResources().getColor(R.color.white));

                ShopAPI();

            }


        });


    }

    private void ShopAPI() {

        rView2.setVisibility(View.GONE);
        noData.setVisibility(View.GONE);
        final List<CategoryObjectTypeThree> allItems;

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String searchTex = sharedPrefs.getString("SearchText", "");
        Log.e("070919 ", " searchTex " + searchTex);
        //  rView2 = searchActivity.findViewById(R.id.cat_details_rcv);
        //  noData = searchActivity.findViewById(R.id.noData);
        allItems = new ArrayList<CategoryObjectTypeThree>();

        //   searchText.setText(searchTex);

        if (TextUtils.isEmpty(searchText.getText().toString())) {
           /* BaseActivity b = new BaseActivity();
            b.ShowAnimatedDialogAll(getContext(), "9");*/
            Toast.makeText(this, "Please enter search text ", Toast.LENGTH_LONG).show();
        } else {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
            String Lat = sharedPref.getString("Lat", "");
            String Long = sharedPref.getString("Long", "");

            loaderBg.setVisibility(View.VISIBLE);


            final Call<ShopListSearch> dataSyncResponseCall2 = RestApi.get().getRestServiceTest().getShopSearch("" + searchText.getText().toString(), "" + Lat, "" + Long);
            dataSyncResponseCall2.enqueue(new Callback<ShopListSearch>() {
                @Override
                public void onResponse(Call<ShopListSearch> call, retrofit2.Response<ShopListSearch> response) {
                    //   Log.e("010919 "," 3ww "+response.body().getFavCatDetailsList().get(0).getId());
                    loaderBg.setVisibility(View.GONE);
                    /* allItems = new ArrayList<CategoryObjectTypeThree>(); */

                    allItems.clear();
                    try {


                        //    if (TextUtils.isEmpty(response.body().toString())) {
                        int l = response.body().getShopsDetailsList().size();
                        if (l > 0) {
                            for (int i = 0; i < l; i++) {
                                //     Log.e("010919 ", i + " data " + response.body().getProductDetails().get(i).getProduct_Id());
                                allItems.add(new CategoryObjectTypeThree("" + response.body().getShopsDetailsList().get(i).getShop_Id(), "" + "" + response.body().getShopsDetailsList().get(i).getShop_Image(), "" + response.body().getShopsDetailsList().get(i).getShop_Name(), "" + "" + response.body().getShopsDetailsList().get(i).getAddress(), "Description", "" + response.body().getShopsDetailsList().get(i).getShop_Name(), "" + response.body().getShopsDetailsList().get(i).getType()));
                            }
                            if (allItems.size() > 0) {
                                CategoryDetailsTypeThreeAdapterSearch rcAdapter = new CategoryDetailsTypeThreeAdapterSearch(NewSearchActivity.this, allItems);
                                lLayout2 = new GridLayoutManager(NewSearchActivity.this, 1);
                                rView2.setVisibility(View.VISIBLE);
                                rView2.setHasFixedSize(true);
                                rView2.setLayoutManager(lLayout2);
                                rView2.setAdapter(rcAdapter);
                                noData.setVisibility(View.GONE);
                            } else {
                                rView2.setVisibility(View.GONE);
                                //   JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                      /*      BaseActivity b = new BaseActivity();
                            b.ShowAnimatedDialogAllMsg(getContext(), "m1",""+response.body().getfMessage().getMessage());
*/
                                noData.setText("" + response.body().getfMessage().getMessage());
                                noData.setVisibility(View.VISIBLE);
                            }

                        }

                        //      }
                    } catch (Exception e) {
                        e.printStackTrace();
                        loaderBg.setVisibility(View.GONE);
                        rView2.setVisibility(View.GONE);

                        noData.setText("No Shops are registered.");
                        noData.setVisibility(View.VISIBLE);
                    }


                }

                @Override
                public void onFailure(Call<ShopListSearch> call, Throwable t) {
                    //    Log.e("010919 "," 3ww "+t);
                    loaderBg.setVisibility(View.GONE);
                }
            });
        }

    }


    private void EventAPI() {

        rView2.setVisibility(View.GONE);
        noData.setVisibility(View.GONE);

        final List<CategoryObjectTypeThree> allItems;

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(NewSearchActivity.this);
        String searchTex = sharedPrefs.getString("SearchText", "");
        //    Log.e("070919 "," searchTex "+searchTex);
       /* rView2 = searchActivity.findViewById(R.id.cat_details_rcv);
        noData = searchActivity.findViewById(R.id.noData);*/
        allItems = new ArrayList<CategoryObjectTypeThree>();


        {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(NewSearchActivity.this);
            String Lat = sharedPref.getString("Lat", "");
            String Long = sharedPref.getString("Long", "");
           /* Log.e("070919 "," searchTex "+searchTex);
            Log.e("070919 "," Lat "+Lat);
            Log.e("070919 "," Long "+Long);
*/
            loaderBg.setVisibility(View.VISIBLE);

            final Call<EventListSearch> dataSyncResponseCall2 = RestApi.get().getRestServiceTest().getEventSearch("" + Lat, "" + Long, "" + searchText.getText().toString());
            dataSyncResponseCall2.enqueue(new Callback<EventListSearch>() {
                @Override
                public void onResponse(Call<EventListSearch> call, retrofit2.Response<EventListSearch> response) {
                    Log.e("080919 ", " 3ww " + response.body());
                    loaderBg.setVisibility(View.GONE);
                    /* allItems = new ArrayList<CategoryObjectTypeThree>();
                     */
                    allItems.clear();

                    int l = response.body().getEventDetailsList().size();
                    if (l > 0) {
                        for (int i = 0; i < l; i++) {
                            //     Log.e("010919 ", i + " data " + response.body().getProductDetails().get(i).getProduct_Id());
                            allItems.add(new CategoryObjectTypeThree("" + response.body().getEventDetailsList().get(i).getEvent_Id(), "" + "" + response.body().getEventDetailsList().get(i).getProduct_Image(), "" + response.body().getEventDetailsList().get(i).getProduct_Title(), "" + "" + response.body().getEventDetailsList().get(i).getEvent_Discount(), "" + response.body().getEventDetailsList().get(i).getDescription()));
                        }
                        if (allItems.size() > 0) {
                            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(NewSearchActivity.this);
                            SharedPreferences.Editor editor = sharedPrefs.edit();
                            editor.putString("type", "5");
                            editor.commit();

                            CategoryDetailsTypeThreeAdapter rcAdapter = new CategoryDetailsTypeThreeAdapter(NewSearchActivity.this, allItems);
                            lLayout2 = new GridLayoutManager(NewSearchActivity.this, 1);
                            rView2.setVisibility(View.VISIBLE);
                            rView2.setHasFixedSize(true);
                            rView2.setLayoutManager(lLayout2);
                            rView2.setAdapter(rcAdapter);

                            noData.setVisibility(View.GONE);
                        } else {
                            rView2.setVisibility(View.GONE);
                            //   JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                      /*      BaseActivity b = new BaseActivity();
                            b.ShowAnimatedDialogAllMsg(getContext(), "m1",""+response.body().getfMessage().getMessage());
*/
                            noData.setText("" + response.body().getfMessage().getMessage());
                            noData.setVisibility(View.VISIBLE);
                        }

                    } else {
                        rView2.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                        //   JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                      /*      BaseActivity b = new BaseActivity();
                            b.ShowAnimatedDialogAllMsg(getContext(), "m1",""+response.body().getfMessage().getMessage());
*/
                        noData.setText("" + response.body().getfMessage().getMessage());
                    }


                }

                @Override
                public void onFailure(Call<EventListSearch> call, Throwable t) {
                    Log.e("010919 ", " 3ww " + t);
                    loaderBg.setVisibility(View.GONE);
                }
            });
        }

    }

    private void SubCatAPI() {
        rView2.setVisibility(View.GONE);
        noData.setVisibility(View.GONE);
        final List<SubCategoryObject> allItems;
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
                 /*   String Lat = sharedPrefs.getString("Lat", "");
                    String Long = sharedPrefs.getString("Long", "");
*/
        loaderBg.setVisibility(View.VISIBLE);

        allItems = new ArrayList<SubCategoryObject>();
        final Call<SubCatListSearch> dataSyncResponseCall2 = RestApi.get().getRestServiceTest().getSubCatSearch("" + searchText.getText().toString());
        dataSyncResponseCall2.enqueue(new Callback<SubCatListSearch>() {
            @Override
            public void onResponse(Call<SubCatListSearch> call, retrofit2.Response<SubCatListSearch> response) {
                //   Log.e("010919 "," 3ww "+response.body().getFavCatDetailsList().get(0).getId());
                loaderBg.setVisibility(View.GONE);


                allItems.clear();

                int l = response.body().getSubCategoryObjects().size();
                if (l > 0) {
                    for (int i = 0; i < l; i++) {
                        Log.e("080919 ", i + " subdata " + response.body().getSubCategoryObjects().get(i).getSubCatcount());
                      /*  String c ="";
                        if(TextUtils.isEmpty(""+response.body().getSubCategoryObjects().get(i).getTest()))
                        {
                            c="0";
                        }
                        else
                        {

                            c = "35";
                        }*/
                        allItems.add(new SubCategoryObject("" + response.body().getSubCategoryObjects().get(i).getSubcategory_Id(), "" + "" + response.body().getSubCategoryObjects().get(i).getSubcat_Image(), "" + response.body().getSubCategoryObjects().get(i).getSubcat_name(), "" + response.body().getSubCategoryObjects().get(i).getSubCatcount(), ""+ response.body().getSubCategoryObjects().get(i).getType()));

                        //    allItems.add(new SubCategoryObject("" + jsonArray.getJSONObject(i).getString("Subcategory_Id"), "" + "" + jsonArray.getJSONObject(i).getString("Subcat_Image"), "" + jsonArray.getJSONObject(i).getString("Subcat_name"), "35", ""));

                    }

                    SubCategoryAdapterSearch rcAdapter = new SubCategoryAdapterSearch(NewSearchActivity.this, allItems);
                    lLayout2 = new GridLayoutManager(NewSearchActivity.this, 1);
                    rView2.setHasFixedSize(true);
                    rView2.setLayoutManager(lLayout2);
                    rView2.setAdapter(rcAdapter);
                    rView2.setVisibility(View.VISIBLE);
                    rView2.setNestedScrollingEnabled(false);
                    noData.setVisibility(View.GONE);


                } else {
                    rView2.setVisibility(View.GONE);
                    //   JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                   /*     BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAllMsg(getContext(), "m1",""+response.body().getfMessage().getMessage());
*/
                    noData.setText("" + response.body().getfMessage().getMessage());
                    //  noData.setText("No products found");
                    noData.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onFailure(Call<SubCatListSearch> call, Throwable t) {
                Log.e("010919 ", " 3ww " + t);
                loaderBg.setVisibility(View.GONE);
            }
        });
    }

    private void ProductsAPI() {

        rView2.setVisibility(View.GONE);
        noData.setVisibility(View.GONE);
        final List<CategoryObjectTypeThree> allItems;

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String searchTex = sharedPrefs.getString("SearchText", "");
        Log.e("070919 ", " searchTex " + searchTex);
        //  rView2 = searchActivity.findViewById(R.id.cat_details_rcv);
        //  noData = searchActivity.findViewById(R.id.noData);
        allItems = new ArrayList<CategoryObjectTypeThree>();

        //   searchText.setText(searchTex);

      /*  if(TextUtils.isEmpty(searchText.getText().toString()))
        {
           *//* BaseActivity b = new BaseActivity();
            b.ShowAnimatedDialogAll(getContext(), "9");*//*
            Toast.makeText(this,"Please enter search text ",Toast.LENGTH_LONG).show();
        }*/
        //   else
        {
            allItems.clear();
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
            String Lat = sharedPref.getString("Lat", "");
            String Long = sharedPref.getString("Long", "");

            loaderBg.setVisibility(View.VISIBLE);
            final Call<ProductListSearch> dataSyncResponseCall = RestApi.get().getRestServiceTest().getProductSearch("" + searchText.getText().toString().trim(), "" + Lat, "" + Long);
            dataSyncResponseCall.enqueue(new Callback<ProductListSearch>() {
                @Override
                public void onResponse(Call<ProductListSearch> call, retrofit2.Response<ProductListSearch> response) {
                    //   Log.e("010919 "," 3ww "+response.body().getFavCatDetailsList().get(0).getId());
                    //  loaderBg.setVisibility(View.GONE);


                    int l = response.body().getProductDetails().size();
                    if (l > 0) {
                        for (int i = 0; i < l; i++) {
                            //     Log.e("010919 ", i + " data " + response.body().getProductDetails().get(i).getProduct_Id());
                            allItems.add(new CategoryObjectTypeThree("" + response.body().getProductDetails().get(i).getProduct_Id(), "" + "" + response.body().getProductDetails().get(i).getProduct_Image(), "" + response.body().getProductDetails().get(i).getShop_Name(), "" + "" + response.body().getProductDetails().get(i).getShopAddress(), "" + response.body().getProductDetails().get(i).getDescription(), "" + response.body().getProductDetails().get(i).getProduct_Title(), "" + response.body().getProductDetails().get(i).getType()));
                        }
                                /*CategoryDetailsTypeThreeAdapter rcAdapter = new CategoryDetailsTypeThreeAdapter(getContext(), allItems);
                                lLayout2 = new GridLayoutManager(getContext(), 1);
                                rView2.setVisibility(View.VISIBLE);
                                rView2.setHasFixedSize(true);
                                rView2.setLayoutManager(lLayout2);
                                rView2.setAdapter(rcAdapter);*/
                    } else {
                        rView2.setVisibility(View.GONE);
                        //   JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                          /*      BaseActivity b = new BaseActivity();
                                b.ShowAnimatedDialogAllMsg(getContext(), "m1",""+response.body().getfMessage().getMessage());
*/
                    }

                }

                @Override
                public void onFailure(Call<ProductListSearch> call, Throwable t) {
                    Log.e("010919 ", " 3ww " + t);
                    loaderBg.setVisibility(View.GONE);
                }
            });


            final Call<ProductListSearch> dataSyncResponseCall2 = RestApi.get().getRestServiceTest().getProductSearch2("" + searchText.getText().toString(), "" + Lat, "" + Long);
            dataSyncResponseCall2.enqueue(new Callback<ProductListSearch>() {
                @Override
                public void onResponse(Call<ProductListSearch> call, retrofit2.Response<ProductListSearch> response) {
                    //   Log.e("010919 "," 3ww "+response.body().getFavCatDetailsList().get(0).getId());
                    loaderBg.setVisibility(View.GONE);
                           /* allItems = new ArrayList<CategoryObjectTypeThree>();

                            allItems.clear();*/

                    int l = response.body().getProductDetails().size();
                    if (l > 0) {
                        for (int i = 0; i < l; i++) {
                            //     Log.e("010919 ", i + " data " + response.body().getProductDetails().get(i).getProduct_Id());
                            allItems.add(new CategoryObjectTypeThree("" + response.body().getProductDetails().get(i).getProduct_Id(), "" + "" + response.body().getProductDetails().get(i).getProduct_Image(), "" + response.body().getProductDetails().get(i).getShop_Name(), "" + "" + response.body().getProductDetails().get(i).getShopAddress(), "" + response.body().getProductDetails().get(i).getDescription(), "" + response.body().getProductDetails().get(i).getProduct_Title(), "" + response.body().getProductDetails().get(i).getType()));
                        }
                        if (allItems.size() > 0) {
                            CategoryDetailsTypeThreeAdapterSearch rcAdapter = new CategoryDetailsTypeThreeAdapterSearch(NewSearchActivity.this, allItems);
                            lLayout2 = new GridLayoutManager(NewSearchActivity.this, 1);
                            rView2.setVisibility(View.VISIBLE);
                            rView2.setHasFixedSize(true);
                            rView2.setLayoutManager(lLayout2);
                            rView2.setAdapter(rcAdapter);
                            noData.setVisibility(View.GONE);
                        } else {
                            rView2.setVisibility(View.GONE);
                            //   JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                      /*      BaseActivity b = new BaseActivity();
                            b.ShowAnimatedDialogAllMsg(getContext(), "m1",""+response.body().getfMessage().getMessage());
*/
                            noData.setText("" + response.body().getfMessage().getMessage());
                        }

                    } else {
                        if (allItems.size() > 0) {
                            CategoryDetailsTypeThreeAdapterSearch rcAdapter = new CategoryDetailsTypeThreeAdapterSearch(NewSearchActivity.this, allItems);
                            lLayout2 = new GridLayoutManager(NewSearchActivity.this, 1);
                            rView2.setVisibility(View.VISIBLE);
                            rView2.setHasFixedSize(true);
                            rView2.setLayoutManager(lLayout2);
                            rView2.setAdapter(rcAdapter);
                            noData.setVisibility(View.GONE);
                        } else {
                            rView2.setVisibility(View.GONE);
                            //   JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                       /*     BaseActivity b = new BaseActivity();
                            b.ShowAnimatedDialogAllMsg(getContext(), "m1",""+response.body().getfMessage().getMessage());
*/      //   Toast.makeText(searchActivity,"No data found ",Toast.LENGTH_LONG).show();

                            //      Log.e("080919 "," "+response.body().getfMessage().getMessage());
                            noData.setText("" + response.body().getfMessage().getMessage());
                            //  noData.setText("No products found");
                            noData.setVisibility(View.VISIBLE);


                        }
                    }

                }

                @Override
                public void onFailure(Call<ProductListSearch> call, Throwable t) {
                    Log.e("010919 ", " 3ww " + t);
                    loaderBg.setVisibility(View.GONE);
                }
            });
        }

    }


}
