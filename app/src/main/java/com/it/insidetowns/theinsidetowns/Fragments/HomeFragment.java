package com.it.insidetowns.theinsidetowns.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.gson.Gson;
import com.it.insidetowns.theinsidetowns.Activities.BaseActivity;
import com.it.insidetowns.theinsidetowns.Activities.InfoPage;
import com.it.insidetowns.theinsidetowns.Activities.Login;
import com.it.insidetowns.theinsidetowns.Activities.NewSearchActivity;
import com.it.insidetowns.theinsidetowns.Adapter.CategoryAdapter;
import com.it.insidetowns.theinsidetowns.R;
import com.it.insidetowns.theinsidetowns.network.RestApi;
import com.it.insidetowns.theinsidetowns.objects.BannerDetails;
import com.it.insidetowns.theinsidetowns.objects.CategoryObject;
import com.it.insidetowns.theinsidetowns.objects.CategoryObjectNew;
import com.it.insidetowns.theinsidetowns.objects.FavObjects.GetCatListTest;
import com.it.insidetowns.theinsidetowns.objects.chngPwd.ChgMessage;
import com.it.insidetowns.theinsidetowns.objects.chngPwd.Credentials;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int l;


    private OnFragmentInteractionListener mListener;
    private LayoutInflater mInflater;


    private Context context;
    BaseActivity b = new BaseActivity();

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private Location mLocation;
    int i;
    RelativeLayout layout;
    TextView address;
    Dialog dialog;
    private FragmentActivity myContext;
    private LinearLayout mGallery;
    RecyclerView rView2;
    private GridLayoutManager lLayout2;
    LinearLayout loaderBg, llpwd;
    ArrayList<BannerDetails> bannerDetails = new ArrayList<BannerDetails>();

    public HomeFragment() {
        context = getContext();
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInflater = LayoutInflater.from(getContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("ID", "");
            editor.commit();

            Intent intent = new Intent(getContext(), Login.class);
            startActivity(intent);

            return true;
        } else if (id == R.id.action_change_password) {


            Button close;
            TextView changePwd_no_underline;

            final Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.setContentView(R.layout.popup_change_password);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            close = (Button) dialog.findViewById(R.id.save);
            final EditText fname = (EditText) dialog.findViewById(R.id.fname2);
            final EditText lname = (EditText) dialog.findViewById(R.id.lname2);
            final EditText mobile = (EditText) dialog.findViewById(R.id.email2);
            changePwd_no_underline = (TextView) dialog.findViewById(R.id.changePwd_no_underline);


            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String oldpwd = fname.getText().toString();
                    String newpwd = lname.getText().toString();
                    String cnfpwd = mobile.getText().toString();

                    if (TextUtils.isEmpty(oldpwd)) {
                        //   ErrorToast.show(UserProfileActivity.this, getString(R.string.alert_current_password), false);
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAllMsg(context, "m1", "" + getString(R.string.alert_current_password));

                    } else if (TextUtils.isEmpty(newpwd)) {

                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAllMsg(context, "m1", "" + getString(R.string.alert_valid_password));

                    } else if (TextUtils.isEmpty(cnfpwd)) {

                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAllMsg(context, "m1", "" + getString(R.string.alert_confirm_password));

                    } else if (!mobile.getText().toString().equals(lname.getText().toString())) {
                        mobile.setText("");
                        lname.setText("");
                        // ErrorToast.show(UserProfileActivity.this, getString(R.string.alert_password_not_matched), false);

                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAllMsg(context, "m1", "" + getString(R.string.alert_password_not_matched));

                    } else {
                        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                        String ID = sharedPrefs.getString("ID", "");

                        loaderBg.setVisibility(View.VISIBLE);

                        Credentials loginCredentials = new Credentials();
                       /* loginCredentials.id = "" + ID;
                        loginCredentials.password = "" + newpwd;
                        loginCredentials.oldPassword = "" + oldpwd;*/
                        loginCredentials.setUserid(Integer.parseInt(ID));
                        loginCredentials.setPassword(newpwd);
                        loginCredentials.setOldPassword(oldpwd);

                        Log.e("090919 ", " old " + loginCredentials.getOldPassword());


                        final Call<ChgMessage> dataSyncResponseCall = RestApi.get().getRestServiceTest().getChngPwd(loginCredentials);
                        dataSyncResponseCall.enqueue(new Callback<ChgMessage>() {
                            @Override
                            public void onResponse(Call<ChgMessage> call, retrofit2.Response<ChgMessage> response) {
                                loaderBg.setVisibility(View.GONE);
                                dialog.dismiss();

                                Log.e("110919 ", " 3ww " + response.body());
                                try {

                                    if (!TextUtils.isEmpty("" + response.body())) {
                                        BaseActivity b = new BaseActivity();
                                        b.ShowAnimatedDialogAllMsg(context, "m1", "" + response.body().getMessage());
                                    } else {
                                        BaseActivity b = new BaseActivity();
                                        b.ShowAnimatedDialogAllMsg(context, "m1", "Invalid Old Password");

                                    }
                                }
                                catch (Exception e)
                                {
                                    BaseActivity b = new BaseActivity();
                                    b.ShowAnimatedDialogAllMsg(context, "m1", "Invalid Old Password");

                                }

                                //  String l = response.body().getMessage();
                                //    Log.e("090919 ", " 3ww " + response.body().getMessage());

                            }

                            @Override
                            public void onFailure(Call<ChgMessage> call, Throwable t) {
                                loaderBg.setVisibility(View.GONE);
                                dialog.dismiss();
                                //    Log.e("090919 ", " 3ww " + t);
                                BaseActivity b = new BaseActivity();
                                b.ShowAnimatedDialogAllMsg(context, "m1", "Invalid Old Password" );



                            }
                        });
                    }


                }
            });
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_fragment_home, container, false);
        final SwipeRefreshLayout pullToRefresh = v.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //  refreshData(); // your code
                ConnectivityManager con_mngr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo nw_information = con_mngr.getActiveNetworkInfo();
                if (nw_information != null && nw_information.isConnected()) {
                    HorizonatalScroollData();
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                    String ID = sharedPrefs.getString("ID", "");
                    if (!TextUtils.isEmpty(ID)) {
                        FavList();
                    } else
                        CategoriesApiCall();
                } else {
                    Toast.makeText(getContext(), "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }

                pullToRefresh.setRefreshing(false);
            }
        });
        mGallery = (LinearLayout) v.findViewById(R.id.id_gallery);
        llpwd = (LinearLayout) v.findViewById(R.id.llpwd);
        rView2 = (RecyclerView) v.findViewById(R.id.recycler_view_govt);
        address = (TextView) v.findViewById(R.id.address);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String CurAddress = sharedPrefs.getString("CurAddress", "");
        address.setText("" + CurAddress);

        loaderBg = (LinearLayout) v.findViewById(R.id.loaderBg);

        Toolbar toolbar = v.findViewById(R.id.toolbar);
        // context.setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);


        dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.show();


        ConnectivityManager con_mngr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nw_information = con_mngr.getActiveNetworkInfo();
        if (nw_information != null && nw_information.isConnected()) {
            HorizonatalScroollData();
            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            String ID = sharedPrefs.getString("ID", "");
            if (!TextUtils.isEmpty(ID)) {
                FavList();
            } else
                CategoriesApiCall();
        } else {
            Toast.makeText(getContext(), "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }


        llpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewSearchActivity.class);
                startActivity(intent);

            }
        });

        hideKeyboard((Activity)getContext());

        return v;

    }

    private void FavList() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String ID = sharedPrefs.getString("ID", "");

        //    loaderBg.setVisibility(View.VISIBLE);
        final Call<GetCatListTest> dataSyncResponseCall = RestApi.get().getRestServiceTest().getCatListTest("" + ID);
        dataSyncResponseCall.enqueue(new Callback<GetCatListTest>() {
            @Override
            public void onResponse(Call<GetCatListTest> call, retrofit2.Response<GetCatListTest> response) {
                //   Log.e("010919 "," 3ww "+response.body().getFavCatDetailsList().get(0).getId());
                // loaderBg.setVisibility(View.GONE);
                List<CategoryObjectNew> allItems = new ArrayList<CategoryObjectNew>();

                allItems.clear();
                if(response.body().getFavCatDetailsList()!= null){
                    l = response.body().getFavCatDetailsList().size();
                }else{
                    int l =0;
                }
                if (l > 0) {


                    for (int i = 0; i < l; i++) {
                        //     Log.e("010919 ", i + " data " + response.body().getFavCatDetailsList().get(i).getCategory_name());
                        allItems.add(new CategoryObjectNew("" + response.body().getFavCatDetailsList().get(i).getId(), "" + "" + response.body().getFavCatDetailsList().get(i).getCat_Image(), "" + response.body().getFavCatDetailsList().get(i).getCat_id(), "" + "" + response.body().getFavCatDetailsList().get(i).getCategory_name(), "" + response.body().getFavCatDetailsList().get(i).getType()));


                    }
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    Gson gson = new Gson();

                    String json2 = gson.toJson(allItems);
                    editor.putString("AllFavouriteList", json2);
                    editor.commit();

                    CategoriesApiCall();

                } else {

                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    Gson gson = new Gson();

                    String json2 = gson.toJson(allItems);
                    editor.putString("AllFavouriteList", "");
                    editor.commit();
                    //   JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                /*    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAllMsg(context, "m1",""+response.body().getfMessage().getMessage());
*/
                    CategoriesApiCall();

                }

            }

            @Override
            public void onFailure(Call<GetCatListTest> call, Throwable t) {
                Log.e("010919 ", " 3ww " + t);
                //      loaderBg.setVisibility(View.GONE);
                CategoriesApiCall();
            }
        });
    }

/*    private void FavList() {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String ID = sharedPrefs.getString("ID", "");


        RequestQueue queue = Volley.newRequestQueue(getContext());
        final StringRequest reqQueue = new StringRequest(Request.Method.GET, ""+getResources().getString(R.string.AppURL)+"api/Common/FavCategories?Userid="+ID, new com.android.volley.insideResponse.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("FavCatDetails");


                    if(jsonArray.length()>0) {

                        List<FavCatObject> allItems = new ArrayList<FavCatObject>();
                        allItems.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                              allItems.add(new FavCatObject("" + jsonArray.getJSONObject(i).getString("id"), "" + "" + jsonArray.getJSONObject(i).getString("Cat_id"), "" + jsonArray.getJSONObject(i).getString("Category_name"), "" + jsonArray.getJSONObject(i).getString("Cat_Image")));
                        }

                        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        Gson gson = new Gson();

                        String json2 = gson.toJson(allItems);
                        editor.putString("AllFavouriteList", json2);
                        editor.commit();

                        CategoriesApiCall();

                    }
                    else
                    {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                          CategoriesApiCall();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }


        }, new insideResponse.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                loaderBg.setVisibility(View.GONE);
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "No SubCategories are registered.";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAllMsg(context, "m1",""+message);
                } else if (volleyError instanceof ServerError) {
                    if (volleyError.networkResponse.statusCode == 417) {
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(context, "7");
                        //   message = "No SubCategories are registered.";
                    }
                    else
                    if (volleyError.networkResponse.statusCode == 500) {
                        //   message = "Server could not be found. Please check.";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(context, "3");
                    } else {
                        //   message = "The server could not be found. Please try again after some time!!";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(context, "3");
                    }

                } else if (volleyError instanceof AuthFailureError) {
                    //    message = "Cannot connect to Internet...Please check your connection!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "1");
                } else if (volleyError instanceof ParseError) {
                    //   message = "Parsing error! Please try again after some time!!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "4");
                } else if (volleyError instanceof NoConnectionError) {
                    //    message = "Cannot connect to Internet...Please check your connection!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "1");
                } else if (volleyError instanceof TimeoutError) {
                    //    message = "Connection TimeOut! Please check your internet connection.";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "8");
                }
            //    Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_LONG).show();
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
                  //  Toast.makeText(getContext(), "Some error occurred. Please try again", Toast.LENGTH_LONG).show();
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "6");
                }
                return super.getBody();
            }


            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(reqQueue);
    }*/


    private void CategoriesApiCall() {

     /*   _ProgressDialog = new ProgressDialog(getContext()).show(getContext(),"Data Loading","Please wait...",true,true,new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //  Toast.makeText(DeviceList.this, "Could not find the device!", Toast.LENGTH_SHORT).show();
                //  finish();
            }
        });*/
        RequestQueue queue = Volley.newRequestQueue(getContext());
        final StringRequest reqQueue = new StringRequest(Request.Method.GET, "" + getResources().getString(R.string.AppURL) + "api/Common/Categories", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loaderBg.setVisibility(View.GONE);

                //     loaderBg.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Data loaded successfully...", Toast.LENGTH_SHORT);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("CategoriesDetails");
                    //    Log.e("030819 "," "+jsonArray);
                    //   JSONArray jsonArray = new JSONArray(jsonObject.get("CategoriesDetails"));
                    //  Log.e("030819 "," jsonArray "+jsonArray);

                    if (jsonArray.length() > 0) {

                        List<CategoryObject> allItems = new ArrayList<CategoryObject>();

                        for (int i = 0; i < jsonArray.length(); i++) {

                            //   String ss = jsonArray.getJSONObject(i).getString("Category_name");
                            //     Log.e("030819 "," ss "+ss);
                            allItems.add(new CategoryObject("" + jsonArray.getJSONObject(i).getString("Category_Id"), "" + "" + jsonArray.getJSONObject(i).getString("Cat_Image"), "" + jsonArray.getJSONObject(i).getString("Category_name"), "" + jsonArray.getJSONObject(i).getString("type"), ""));


                        }

                        CategoryAdapter rcAdapter = new CategoryAdapter(context, allItems);
                        lLayout2 = new GridLayoutManager(context, 2);
                        rView2.setHasFixedSize(true);
                        rView2.setLayoutManager(lLayout2);
                        rView2.setAdapter(rcAdapter);
                        rView2.setNestedScrollingEnabled(false);
                        hideKeyboard(getActivity());
                    } else {
                        //    Log.e("110819 "," no data ");
                        JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                        //    Log.e("110819 "," no data "+jsonObject1.getString("Message"));
                        Toast.makeText(context, "" + jsonObject1.getString("Message"), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                dialog.dismiss();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                loaderBg.setVisibility(View.GONE);
                dialog.dismiss();
                //   loaderBg.setVisibility(View.GONE);
                String message = null;
                if (volleyError instanceof NetworkError) {
                    //    message = "Cannot connect to Internet...Please check your connection!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "1");
                } else if (volleyError instanceof ServerError) {

                    if (volleyError.networkResponse.statusCode == 417) {
                        message = "Invalid credentials. Please try again...";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAllMsg(context, "m1", "" + message);


                    } else if (volleyError.networkResponse.statusCode == 500) {
                        //   message = "Server could not be found. Please check.";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(context, "3");
                    } else {
                        //   message = "The server could not be found. Please try again after some time!!";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(context, "3");
                    }
                } else if (volleyError instanceof AuthFailureError) {
                    //    message = "Cannot connect to Internet...Please check your connection!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "1");
                } else if (volleyError instanceof ParseError) {
                    //   message = "Parsing error! Please try again after some time!!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "4");
                } else if (volleyError instanceof NoConnectionError) {
                    //    message = "Cannot connect to Internet...Please check your connection!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "1");
                } else if (volleyError instanceof TimeoutError) {
                    //    message = "Connection TimeOut! Please check your internet connection.";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "8");
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
                    //  Toast.makeText(getContext(), "Some error occurred. Please try again", Toast.LENGTH_LONG).show();
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "6");
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
        /*allItems.add(new CategoryObject("1",R.mipmap.fashion,"Fashon", "35",""));
        allItems.add(new CategoryObject("2",R.mipmap.beauty,"Beauty & Spa","40",""));
        allItems.add(new CategoryObject("3",R.mipmap.events,"Events","35",""));
        allItems.add(new CategoryObject("4",R.mipmap.travel,"Travel", "40",""));


        allItems.add(new CategoryObject("5",R.mipmap.bikes,"Bikes", "35",""));
        allItems.add(new CategoryObject("6",R.mipmap.house_keeping,"House Keeping","40",""));
        allItems.add(new CategoryObject("7",R.mipmap.food,"Food","35",""));
        allItems.add(new CategoryObject("8",R.mipmap.fitness,"Fitness", "40",""));
    */
        return allItems;
    }


    private void HorizonatalScroollData() {

        BannerApi();


    }

    private void BannerApi() {
        loaderBg.setVisibility(View.VISIBLE);

     /*   _ProgressDialog = new ProgressDialog(getContext()).show(getContext(),"Data Loading","Please wait...",true,true,new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        _ProgressDialog.setIndeterminate(true);
        _ProgressDialog.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.loader));
        _ProgressDialog.show();*/
        RequestQueue queue = Volley.newRequestQueue(getContext());
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String Lat = sharedPrefs.getString("Lat", "");
        String Long = sharedPrefs.getString("Long", "");

        final StringRequest reqQueue = new StringRequest(Request.Method.GET, "" + getResources().getString(R.string.AppURL) + "api/Common/HomeSlider?latitude=" + Lat + "&longitude=" + Long, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //   loaderBg.setVisibility(View.GONE);
                //    Toast.makeText(getContext(), "Data loaded successfully...", Toast.LENGTH_SHORT);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Sliderdetails");
                    if (jsonArray.length() > 0) {

                        for (int i = 0; i < jsonArray.length(); i++) {

                            bannerDetails.add(new BannerDetails("" + jsonArray.getJSONObject(i).getString("ProductImage"), "" + "" + jsonArray.getJSONObject(i).getString("Shop_Name"), "" + jsonArray.getJSONObject(i).getString("ShopImage"), "" + jsonArray.getJSONObject(i).getString("catname"), "" + jsonArray.getJSONObject(i).getString("Title"), "" + jsonArray.getJSONObject(i).getString("Type"), "" + jsonArray.getJSONObject(i).getString("ProductId")));
                        }

                        for (i = 0; i < bannerDetails.size(); i++) {
                            mInflater = LayoutInflater.from(getContext());
                            View view = mInflater.inflate(R.layout.activity_gallery_item_flag,
                                    mGallery, false);
                            ImageView img = (ImageView) view.findViewById(R.id.id_index_gallery_item_image);
                            ImageView shopImage = (ImageView) view.findViewById(R.id.shopImage);

                            final RelativeLayout llDiscount = (RelativeLayout) view.findViewById(R.id.llDiscount);
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beauty);
                            TextView tv = (TextView) view
                                    .findViewById(R.id.tv);
                            final TextView ProductId = (TextView) view.findViewById(R.id.ProductId);
                            final TextView Ptype = (TextView) view.findViewById(R.id.Ptype);
                            //  Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "helvetica_medium.ttf");
                            final TextView location = (TextView) view.findViewById(R.id.location);
                            final TextView Shop_Name = (TextView) view.findViewById(R.id.Shop_Name);

                            Transformation transformation = new RoundedTransformationBuilder()
                                    .borderColor(Color.WHITE)
                                    .borderWidthDp(1)
                                    .cornerRadiusDp(20)
                                    .build();
                            tv.setText(bannerDetails.get(i).getDiscount());
                            ProductId.setText(bannerDetails.get(i).getProductId());
                            Ptype.setText(bannerDetails.get(i).getPtype());
                            Shop_Name.setText(bannerDetails.get(i).getName());


                            location.setText("" + bannerDetails.get(i).getType());
                            Picasso.with(context)
                                    .load(bannerDetails.get(i).getImagId())
                                    .fit()
                                    .transform(transformation)
                                    .into(img);


                            transformation = new RoundedTransformationBuilder()
                                    .borderColor(Color.WHITE)
                                    .borderWidthDp(1)
                                    .cornerRadiusDp(20)
                                    .build();


                            Picasso.with(context)
                                    .load(bannerDetails.get(i).getShopImage())
                                    .fit()
                                    .transform(transformation)
                                    .into(shopImage);

                            //    mGallery.addView(view);

                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                                    SharedPreferences.Editor editor = sharedPrefs.edit();
                                    //  editor.putString("CatId", ""+itemList.get(position).getId());
                                    editor.putString("CatName", "" + location.getText().toString());
                                    editor.putString("Product_Id", "" + ProductId.getText().toString());
                                    editor.putString("type", "" + Ptype.getText().toString());
                                    editor.putString("shopName", "" + Shop_Name.getText().toString());

                                    //    Log.e("200819 ",""+Ptype.getText().toString());
                                    editor.commit();

                                    Intent intent = new Intent(context, InfoPage.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    context.startActivity(intent);


                                }
                            });


                        }
                    } else {
                        //    Log.e("110819 "," no data ");
                        JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                        //    Log.e("110819 "," no data "+jsonObject1.getString("Message"));
                        Toast.makeText(context, "" + jsonObject1.getString("Message"), Toast.LENGTH_LONG).show();

                    }

                    jsonArray = jsonObject.getJSONArray("SliderTypedetails");
                    if (jsonArray.length() > 0) {

                        try {

                            for (int i = 0; i < jsonArray.length(); i++) {

                                bannerDetails.add(new BannerDetails("" + jsonArray.getJSONObject(i).getString("ProductImage"), "" + "" + jsonArray.getJSONObject(i).getString("Shop_Name"), "" + jsonArray.getJSONObject(i).getString("ShopImage"), "" + jsonArray.getJSONObject(i).getString("catname"), "" + jsonArray.getJSONObject(i).getString("Title"), "" + jsonArray.getJSONObject(i).getString("Type"), "" + jsonArray.getJSONObject(i).getString("ProductId")));
                            }

                            for (i = 0; i < bannerDetails.size(); i++) {
                                mInflater = LayoutInflater.from(getContext());
                                View view = mInflater.inflate(R.layout.activity_gallery_item_flag,
                                        mGallery, false);
                                ImageView img = (ImageView) view
                                        .findViewById(R.id.id_index_gallery_item_image);
                                ImageView shopImage = (ImageView) view.findViewById(R.id.shopImage);

                                final RelativeLayout llDiscount = (RelativeLayout) view.findViewById(R.id.llDiscount);
                                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beauty);
                                TextView tv = (TextView) view
                                        .findViewById(R.id.tv);
                                final TextView ProductId = (TextView) view.findViewById(R.id.ProductId);
                                final TextView Ptype = (TextView) view.findViewById(R.id.Ptype);
                                final TextView location = (TextView) view.findViewById(R.id.location);
                                final TextView Shop_Name = (TextView) view.findViewById(R.id.Shop_Name);

                                Transformation transformation = new RoundedTransformationBuilder()
                                        .borderColor(Color.WHITE)
                                        .borderWidthDp(1)
                                        .cornerRadiusDp(20)
                                        .build();
                                tv.setText(bannerDetails.get(i).getDiscount());
                                ProductId.setText(bannerDetails.get(i).getProductId());
                                Ptype.setText(bannerDetails.get(i).getPtype());
                                Shop_Name.setText(bannerDetails.get(i).getName());


                                location.setText("" + bannerDetails.get(i).getType());
                                Picasso.with(context)
                                        .load(bannerDetails.get(i).getImagId())
                                        .fit()
                                        .transform(transformation)
                                        .into(img);

                                transformation = new RoundedTransformationBuilder()
                                        .borderColor(Color.WHITE)
                                        .borderWidthDp(1)
                                        .cornerRadiusDp(45)
                                        .build();


                                Picasso.with(context)
                                        .load(bannerDetails.get(i).getShopImage())
                                        .fit()
                                        .transform(transformation)
                                        .into(shopImage);
                                mGallery.addView(view);

                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                                        SharedPreferences.Editor editor = sharedPrefs.edit();
                                        //  editor.putString("CatId", ""+itemList.get(position).getId());
                                        editor.putString("CatName", "" + location.getText().toString());
                                        editor.putString("Product_Id", "" + ProductId.getText().toString());
                                        editor.putString("type", "" + Ptype.getText().toString());
                                        editor.putString("shopName", "" + Shop_Name.getText().toString());

                                        editor.commit();

                                        Intent intent = new Intent(context, InfoPage.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        context.startActivity(intent);

                                    }
                                });


                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(e);
                        }

                    } else {
                        //    Log.e("110819 "," no data ");
                        JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                        //    Log.e("110819 "," no data "+jsonObject1.getString("Message"));
                        Toast.makeText(context, "" + jsonObject1.getString("Message"), Toast.LENGTH_LONG).show();

                    }
                    jsonArray = jsonObject.getJSONArray("EventSliderTypedetails");
                    if (jsonArray.length() > 0) {

                        try {

                            for (int i = 0; i < jsonArray.length(); i++) {

                                bannerDetails.add(new BannerDetails("" + jsonArray.getJSONObject(i).getString("ProductImage"), "" + "" + jsonArray.getJSONObject(i).getString("Shop_Name"), "" + jsonArray.getJSONObject(i).getString("ShopImage"), "" + jsonArray.getJSONObject(i).getString("catname"), "" + jsonArray.getJSONObject(i).getString("Title"), "" + jsonArray.getJSONObject(i).getString("Type"), "" + jsonArray.getJSONObject(i).getString("ProductId")));
                            }

                            for (i = 0; i < bannerDetails.size(); i++) {
                                mInflater = LayoutInflater.from(getContext());
                                View view = mInflater.inflate(R.layout.activity_gallery_item_flag,
                                        mGallery, false);
                                ImageView img = (ImageView) view
                                        .findViewById(R.id.id_index_gallery_item_image);
                                ImageView shopImage = (ImageView) view.findViewById(R.id.shopImage);

                                final RelativeLayout llDiscount = (RelativeLayout) view.findViewById(R.id.llDiscount);
                                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beauty);
                                TextView tv = (TextView) view
                                        .findViewById(R.id.tv);
                                final TextView ProductId = (TextView) view.findViewById(R.id.ProductId);
                                final TextView Ptype = (TextView) view.findViewById(R.id.Ptype);
                                final TextView location = (TextView) view.findViewById(R.id.location);
                                final TextView Shop_Name = (TextView) view.findViewById(R.id.Shop_Name);

                                Transformation transformation = new RoundedTransformationBuilder()
                                        .borderColor(Color.WHITE)
                                        .borderWidthDp(1)
                                        .cornerRadiusDp(20)
                                        .build();
                                tv.setText(bannerDetails.get(i).getDiscount());
                                ProductId.setText(bannerDetails.get(i).getProductId());
                                Ptype.setText(bannerDetails.get(i).getPtype());
                                Shop_Name.setText(bannerDetails.get(i).getName());


                                location.setText("" + bannerDetails.get(i).getType());
                                Picasso.with(context)
                                        .load(bannerDetails.get(i).getImagId())
                                        .fit()
                                        .transform(transformation)
                                        .into(img);

                                transformation = new RoundedTransformationBuilder()
                                        .borderColor(Color.WHITE)
                                        .borderWidthDp(1)
                                        .cornerRadiusDp(45)
                                        .build();


                                Picasso.with(context)
                                        .load(bannerDetails.get(i).getShopImage())
                                        .fit()
                                        .transform(transformation)
                                        .into(shopImage);
                                mGallery.addView(view);

                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                                        SharedPreferences.Editor editor = sharedPrefs.edit();
                                        //  editor.putString("CatId", ""+itemList.get(position).getId());
                                        editor.putString("CatName", "" + location.getText().toString());
                                        editor.putString("Product_Id", "" + ProductId.getText().toString());
                                        editor.putString("type", "" + Ptype.getText().toString());
                                        editor.putString("shopName", "" + Shop_Name.getText().toString());

                                        editor.commit();

                                        Intent intent = new Intent(context, InfoPage.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        context.startActivity(intent);

                                    }
                                });


                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(e);
                        }

                    } else {
                        //    Log.e("110819 "," no data ");
                        JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                        //    Log.e("110819 "," no data "+jsonObject1.getString("Message"));
                        Toast.makeText(context, "" + jsonObject1.getString("Message"), Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //      loaderBg.setVisibility(View.GONE);
                loaderBg.setVisibility(View.GONE);
                String message = null;
                if (volleyError instanceof NetworkError) {
                    //    message = "Cannot connect to Internet...Please check your connection!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "1");
                } else if (volleyError instanceof ServerError) {

                    if (volleyError.networkResponse.statusCode == 417) {
                        message = "Invalid credentials. Please try again...";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAllMsg(context, "m1", "" + message);


                    } else if (volleyError.networkResponse.statusCode == 500) {
                        //   message = "Server could not be found. Please check.";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(context, "3");
                    } else {
                        //   message = "The server could not be found. Please try again after some time!!";
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(context, "3");
                    }
                } else if (volleyError instanceof AuthFailureError) {
                    //    message = "Cannot connect to Internet...Please check your connection!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "1");
                } else if (volleyError instanceof ParseError) {
                    //   message = "Parsing error! Please try again after some time!!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "4");
                } else if (volleyError instanceof NoConnectionError) {
                    //    message = "Cannot connect to Internet...Please check your connection!";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "1");
                } else if (volleyError instanceof TimeoutError) {
                    //    message = "Connection TimeOut! Please check your internet connection.";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "8");
                }
                //   Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonObject = new JSONObject();
                    //jsonObject.put("Phone", ""+phone.getText().toString());
                    return jsonObject.toString().getBytes("utf-8");
                } catch (Exception ex) {
                    // Toast.makeText(getContext(), "Some error occurred. Please try again", Toast.LENGTH_LONG).show();
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "6");
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


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.

                return;
            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //    Log.e("100718","HomeFragment Resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        //  Log.e("100718","HomePause");
    }

    private Bitmap bitmapConvert(String Image) {
        byte[] decodedString = Base64.decode(Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        myContext = (FragmentActivity) context;
        this.context = context;
        super.onAttach(context);

    }


    @Override
    public void onDetach() {
        super.onDetach();
        //   mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
