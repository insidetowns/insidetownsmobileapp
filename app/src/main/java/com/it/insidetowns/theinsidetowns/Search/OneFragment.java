package com.it.insidetowns.theinsidetowns.Search;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.it.insidetowns.theinsidetowns.Activities.SearchActivity;
import com.it.insidetowns.theinsidetowns.Adapter.CategoryDetailsTypeThreeAdapterSearch;
import com.it.insidetowns.theinsidetowns.R;
import com.it.insidetowns.theinsidetowns.network.RestApi;
import com.it.insidetowns.theinsidetowns.objects.CategoryObjectTypeThree;
import com.it.insidetowns.theinsidetowns.objects.ProductSearch.ProductListSearch;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OneFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OneFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText searchText;
    LinearLayout show;
    private GridLayoutManager lLayout2;
    LinearLayout loaderBg;
    RecyclerView rView2;
    TextView noData;
    List<CategoryObjectTypeThree> allItems;


    private OnFragmentInteractionListener mListener;

    public OneFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OneFragment newInstance(String param1, String param2) {
        OneFragment fragment = new OneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        searchText = view.findViewById(R.id.searchText);
       // show = view.findViewById(R.id.show);
        rView2 = view.findViewById(R.id.cat_details_rcv);
        loaderBg = view.findViewById(R.id.loaderBg);
        noData = view.findViewById(R.id.noData);
        allItems = new ArrayList<CategoryObjectTypeThree>();

       /*

       Log.e("070919 "," 3ww "+searchTex);*/
      /* show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {*/
    //    Log.e("070919 "," 222 "+searchText.getText().toString().trim());

       /*     }
        });
*/
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

 /*   @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void show(final SearchActivity searchActivity)
    {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(searchActivity);
        String searchTex = sharedPrefs.getString("SearchText", "");
    //    Log.e("070919 "," searchTex "+searchTex);
        rView2 = searchActivity.findViewById(R.id.cat_details_rcv);
        noData = searchActivity.findViewById(R.id.noData);
        allItems = new ArrayList<CategoryObjectTypeThree>();

        //   searchText.setText(searchTex);

        if(TextUtils.isEmpty(searchTex))
        {
           /* BaseActivity b = new BaseActivity();
            b.ShowAnimatedDialogAll(getContext(), "9");*/
            Toast.makeText(searchActivity,"Please enter search text ",Toast.LENGTH_LONG).show();
        }
        else
        {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(searchActivity.getApplicationContext());
            String Lat = sharedPref.getString("Lat", "");
            String Long = sharedPref.getString("Long", "");

        //    loaderBg.setVisibility(View.VISIBLE);
            final Call<ProductListSearch> dataSyncResponseCall = RestApi.get().getRestServiceTest().getProductSearch(""+searchTex,""+Lat,""+Long);
            dataSyncResponseCall.enqueue(new Callback<ProductListSearch>() {
                @Override
                public void onResponse(Call<ProductListSearch> call, retrofit2.Response<ProductListSearch> response) {
                    //   Log.e("010919 "," 3ww "+response.body().getFavCatDetailsList().get(0).getId());
                    //  loaderBg.setVisibility(View.GONE);

                    allItems.clear();

                    int l = response.body().getProductDetails().size();
                    if (l > 0) {
                        for (int i = 0; i < l; i++) {
                            //     Log.e("010919 ", i + " data " + response.body().getProductDetails().get(i).getProduct_Id());
                            allItems.add(new CategoryObjectTypeThree("" + response.body().getProductDetails().get(i).getProduct_Id(), "" + "" + response.body().getProductDetails().get(i).getProduct_Image(), "" + response.body().getProductDetails().get(i).getShop_Name(), "" + "" +response.body().getProductDetails().get(i).getShopAddress(), ""+response.body().getProductDetails().get(i).getDescription(), ""+response.body().getProductDetails().get(i).getProduct_Title(), ""+response.body().getProductDetails().get(i).getType()));
                        }
                                /*CategoryDetailsTypeThreeAdapter rcAdapter = new CategoryDetailsTypeThreeAdapter(getContext(), allItems);
                                lLayout2 = new GridLayoutManager(getContext(), 1);
                                rView2.setVisibility(View.VISIBLE);
                                rView2.setHasFixedSize(true);
                                rView2.setLayoutManager(lLayout2);
                                rView2.setAdapter(rcAdapter);*/
                    }
                    else
                    {
                        rView2.setVisibility(View.GONE);
                        //   JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                          /*      BaseActivity b = new BaseActivity();
                                b.ShowAnimatedDialogAllMsg(getContext(), "m1",""+response.body().getfMessage().getMessage());
*/
                    }

                }

                @Override
                public void onFailure(Call<ProductListSearch> call, Throwable t) {
                    Log.e("010919 "," 3ww "+t);
                    loaderBg.setVisibility(View.GONE);
                }
            });


            final Call<ProductListSearch> dataSyncResponseCall2 = RestApi.get().getRestServiceTest().getProductSearch2(""+searchTex,""+Lat,""+Long);
            dataSyncResponseCall2.enqueue(new Callback<ProductListSearch>() {
                @Override
                public void onResponse(Call<ProductListSearch> call, retrofit2.Response<ProductListSearch> response) {
                    //   Log.e("010919 "," 3ww "+response.body().getFavCatDetailsList().get(0).getId());
//                    loaderBg.setVisibility(View.GONE);
                           /* allItems = new ArrayList<CategoryObjectTypeThree>();

                            allItems.clear();*/

                    int l = response.body().getProductDetails().size();
                    if (l > 0) {
                        for (int i = 0; i < l; i++) {
                            //     Log.e("010919 ", i + " data " + response.body().getProductDetails().get(i).getProduct_Id());
                            allItems.add(new CategoryObjectTypeThree("" + response.body().getProductDetails().get(i).getProduct_Id(), "" + "" + response.body().getProductDetails().get(i).getProduct_Image(), "" + response.body().getProductDetails().get(i).getShop_Name(), "" + "" +response.body().getProductDetails().get(i).getShopAddress(), ""+response.body().getProductDetails().get(i).getDescription(), ""+response.body().getProductDetails().get(i).getProduct_Title(), ""+response.body().getProductDetails().get(i).getType()));
                        }
                        if(allItems.size()>0)
                        {
                            CategoryDetailsTypeThreeAdapterSearch rcAdapter = new CategoryDetailsTypeThreeAdapterSearch(searchActivity, allItems);
                            lLayout2 = new GridLayoutManager(getContext(), 1);
                            rView2.setVisibility(View.VISIBLE);
                            rView2.setHasFixedSize(true);
                            rView2.setLayoutManager(lLayout2);
                            rView2.setAdapter(rcAdapter);
                            noData.setVisibility(View.GONE);
                        }
                        else
                        {
                            rView2.setVisibility(View.GONE);
                            //   JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                      /*      BaseActivity b = new BaseActivity();
                            b.ShowAnimatedDialogAllMsg(getContext(), "m1",""+response.body().getfMessage().getMessage());
*/
                            noData.setText(""+response.body().getfMessage().getMessage());
                        }

                    }
                    else
                    {
                        if(allItems.size()>0)
                        {
                            CategoryDetailsTypeThreeAdapterSearch rcAdapter = new CategoryDetailsTypeThreeAdapterSearch(getContext(), allItems);
                            lLayout2 = new GridLayoutManager(getContext(), 1);
                            rView2.setVisibility(View.VISIBLE);
                            rView2.setHasFixedSize(true);
                            rView2.setLayoutManager(lLayout2);
                            rView2.setAdapter(rcAdapter);
                            noData.setVisibility(View.GONE);
                        }
                        else
                        {
                            rView2.setVisibility(View.GONE);
                            //   JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                       /*     BaseActivity b = new BaseActivity();
                            b.ShowAnimatedDialogAllMsg(getContext(), "m1",""+response.body().getfMessage().getMessage());
*/      //   Toast.makeText(searchActivity,"No data found ",Toast.LENGTH_LONG).show();

                      //      Log.e("080919 "," "+response.body().getfMessage().getMessage());
                            noData.setText(""+response.body().getfMessage().getMessage());
                          //  noData.setText("No products found");
                            noData.setVisibility(View.VISIBLE);


                        }
                    }

                }

                @Override
                public void onFailure(Call<ProductListSearch> call, Throwable t) {
                    Log.e("010919 "," 3ww "+t);
                    loaderBg.setVisibility(View.GONE);
                }
            });
        }

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
