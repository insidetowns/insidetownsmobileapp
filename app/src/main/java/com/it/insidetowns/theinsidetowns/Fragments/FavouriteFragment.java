package com.it.insidetowns.theinsidetowns.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.it.insidetowns.theinsidetowns.Activities.BaseActivity;
import com.it.insidetowns.theinsidetowns.Adapter.FavouriteDetailsAdapter;
import com.it.insidetowns.theinsidetowns.R;
import com.it.insidetowns.theinsidetowns.network.RestApi;
import com.it.insidetowns.theinsidetowns.objects.BannerDetails;
import com.it.insidetowns.theinsidetowns.objects.CategoryObject;
import com.it.insidetowns.theinsidetowns.objects.CategoryObjectNew;
import com.it.insidetowns.theinsidetowns.objects.FavObjects.GetCatListTest;

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
public class FavouriteFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    private OnFragmentInteractionListener mListener;
    private LayoutInflater mInflater;


    private Context context;


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private Location mLocation;
    int i;
    RelativeLayout layout;
    private FragmentActivity myContext;
    private LinearLayout mGallery;
    RecyclerView rView2;
    private GridLayoutManager lLayout2;
    ArrayList<BannerDetails> bannerDetails = new ArrayList<BannerDetails>();
    LinearLayout loaderBg;
    public FavouriteFragment() {
        context = getContext();
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =   inflater.inflate(R.layout.activity_fragment_favourites, container, false);
        mGallery = (LinearLayout)v.findViewById(R.id.id_gallery);
        rView2 = (RecyclerView) v.findViewById(R.id.cat_details_rcv);
        loaderBg = (LinearLayout) v.findViewById(R.id.loaderBg);

        final SwipeRefreshLayout pullToRefresh = v.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //  refreshData(); // your code
                ConnectivityManager con_mngr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo nw_information = con_mngr.getActiveNetworkInfo();
                if (nw_information != null && nw_information.isConnected()) {
                    FavApi();
                }
                else {
                    Toast.makeText(getContext(), "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }

                pullToRefresh.setRefreshing(false);
            }
        });



        List<CategoryObject> rowListItem2 = getAllItemList2();

       /* CategoryDetailsAdapter rcAdapter = new CategoryDetailsAdapter(context, rowListItem2);
        lLayout2 = new GridLayoutManager(context, 1);

        rView2.setHasFixedSize(true);
        rView2.setLayoutManager(lLayout2);
        rView2.setAdapter(rcAdapter);*/

    //  FavApi();

       FavApiRetrofit();


        return v;

    }

    private void FavApiRetrofit() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String ID = sharedPrefs.getString("ID", "");

        loaderBg.setVisibility(View.VISIBLE);
        final Call<GetCatListTest> dataSyncResponseCall = RestApi.get().getRestServiceTest().getCatListTest(""+ID);
        dataSyncResponseCall.enqueue(new Callback<GetCatListTest>() {
            @Override
            public void onResponse(Call<GetCatListTest> call, retrofit2.Response<GetCatListTest> response) {
                //   Log.e("010919 "," 3ww "+response.body().getFavCatDetailsList().get(0).getId());
                loaderBg.setVisibility(View.GONE);
                List<CategoryObjectNew> allItems = new ArrayList<CategoryObjectNew>();

                allItems.clear();

                int l = response.body().getFavCatDetailsList().size();
                if (l > 0) {


                    for (int i = 0; i < l; i++) {
                        Log.e("010919 ", i + " data " + response.body().getFavCatDetailsList().get(i).getCategory_name());
                        allItems.add(new CategoryObjectNew("" + response.body().getFavCatDetailsList().get(i).getId(), "" + "" + response.body().getFavCatDetailsList().get(i).getCat_Image(), "" + response.body().getFavCatDetailsList().get(i).getCat_id(), "" + "" + response.body().getFavCatDetailsList().get(i).getCategory_name(), ""+response.body().getFavCatDetailsList().get(i).getType()));


                    }
                    FavouriteDetailsAdapter rcAdapter = new FavouriteDetailsAdapter(context, allItems);
                    rcAdapter.notifyDataSetChanged();
                    lLayout2 = new GridLayoutManager(context, 2);

                    rView2.setHasFixedSize(true);
                    rView2.setLayoutManager(lLayout2);
                    rView2.clearFocus();

                    rView2.setAdapter(rcAdapter);
                }
                else
                {
                //   JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAllMsg(context, "m1",""+response.body().getfMessage().getMessage());

                }

            }

            @Override
            public void onFailure(Call<GetCatListTest> call, Throwable t) {
                Log.e("010919 "," 3ww "+t);
                loaderBg.setVisibility(View.GONE);
            }
        });
    }

    private void FavApi() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String ID = sharedPrefs.getString("ID", "");
        loaderBg.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(context);
        final StringRequest reqQueue = new StringRequest(Request.Method.GET, ""+getResources().getString(R.string.AppURL)+"api/Common/FavCategories?Userid="+ID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loaderBg.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("FavCatDetails");

                    List<CategoryObjectNew> allItems = new ArrayList<CategoryObjectNew>();

                    allItems.clear();
                    if(jsonArray.length()>0) {


                        for (int i = 0; i < jsonArray.length(); i++) {
                               String ss = jsonArray.getJSONObject(i).getString("Category_name");
                               Log.e("030819 "," ss "+ss);
                               allItems.add(new CategoryObjectNew("" + jsonArray.getJSONObject(i).getString("id"), "" + "" + jsonArray.getJSONObject(i).getString("Cat_Image"), "" + jsonArray.getJSONObject(i).getString("Cat_id"), "" + "" + jsonArray.getJSONObject(i).getString("Category_name"), ""+jsonArray.getJSONObject(i).getString("Type")));


                        }
                        FavouriteDetailsAdapter rcAdapter = new FavouriteDetailsAdapter(context, allItems);
                        rcAdapter.notifyDataSetChanged();
                        lLayout2 = new GridLayoutManager(context, 2);

                        rView2.setHasFixedSize(true);
                        rView2.setLayoutManager(lLayout2);
                        rView2.clearFocus();

                        rView2.setAdapter(rcAdapter);

                    }
                    else
                    {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAllMsg(context, "m1",""+jsonObject1.getString("Message"));
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
                    // message = "No SubCategories are registered.";
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "7");
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
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "1");
                } else if (volleyError instanceof ParseError) {
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "4");
                } else if (volleyError instanceof NoConnectionError) {
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "1");
                } else if (volleyError instanceof TimeoutError) {
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(context, "8");
                }
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonObject = new JSONObject();

                    return jsonObject.toString().getBytes("utf-8");
                } catch (Exception ex) {
                  //  Toast.makeText(context, "Some error occurred. Please try again", Toast.LENGTH_LONG).show();
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
     /*   allItems.add(new CategoryObject("5",R.mipmap.bikes,"Bikes", "Bhemili","simply dummy text of the printing and typesetting industry.  "));
        allItems.add(new CategoryObject("6",R.mipmap.house_keeping,"House Keeping","Bhemili","simply dummy text of the printing and typesetting industry.  "));
        allItems.add(new CategoryObject("7",R.mipmap.food,"Food","Beach Road","simply dummy text of the printing and typesetting industry.  "));
        allItems.add(new CategoryObject("8",R.mipmap.fitness,"Fitness", "Bhemili","simply dummy text of the printing and typesetting industry.  "));

        allItems.add(new CategoryObject("1",R.mipmap.fashion,"Palm beach concert", "Beach Road","simply dummy text of the printing and typesetting industry.  "));
        allItems.add(new CategoryObject("2",R.mipmap.beauty,"Novotel resort event","Bhemili","simply dummy text of the printing and typesetting industry.  "));
        allItems.add(new CategoryObject("3",R.mipmap.events,"Navy day 2019","Beach Road","simply dummy text of the printing and typesetting industry.  "));
        allItems.add(new CategoryObject("4",R.mipmap.travel,"Speed boat championship", "Beach Road","simply dummy text of the printing and typesetting industry.  "));
*/

        return allItems;
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
        myContext=(FragmentActivity) context;
        this.context = context;
        super.onAttach(context);

     //   this.activity = context;
   /*   if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
          else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/

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



}
