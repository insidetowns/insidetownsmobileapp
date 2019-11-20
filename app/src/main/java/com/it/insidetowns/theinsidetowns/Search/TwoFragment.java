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
import com.it.insidetowns.theinsidetowns.Adapter.SubCategoryAdapter;
import com.it.insidetowns.theinsidetowns.R;
import com.it.insidetowns.theinsidetowns.network.RestApi;
import com.it.insidetowns.theinsidetowns.objects.SubCatSearch.SubCatListSearch;
import com.it.insidetowns.theinsidetowns.objects.SubCategoryObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TwoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwoFragment extends Fragment {
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
    List<SubCategoryObject> allItems;


    private OnFragmentInteractionListener mListener;

    public TwoFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TwoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TwoFragment newInstance(String param1, String param2) {
        TwoFragment fragment = new TwoFragment();
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
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        searchText = view.findViewById(R.id.searchText);
        show = view.findViewById(R.id.show);
        rView2 = view.findViewById(R.id.cat_details_rcv);
        loaderBg = view.findViewById(R.id.loaderBg);
        noData = view.findViewById(R.id.noData);
        allItems = new ArrayList<SubCategoryObject>();
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                String searchTex = sharedPrefs.getString("SearchText", "");
                //    Log.e("070919 "," searchTex "+searchTex);
             //   rView2 = searchActivity.findViewById(R.id.cat_details_rcv);
            //    noData = searchActivity.findViewById(R.id.noData);
                allItems = new ArrayList<SubCategoryObject>();

                //   searchText.setText(searchTex);

                if(TextUtils.isEmpty(searchTex))
                {
           /* BaseActivity b = new BaseActivity();
            b.ShowAnimatedDialogAll(getContext(), "9");*/
                    Toast.makeText(getContext(),"Please enter search text ",Toast.LENGTH_LONG).show();
                }
                else
                {
                    sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());
                 /*   String Lat = sharedPrefs.getString("Lat", "");
                    String Long = sharedPrefs.getString("Long", "");
*/
                    //    loaderBg.setVisibility(View.VISIBLE);


                    final Call<SubCatListSearch> dataSyncResponseCall2 = RestApi.get().getRestServiceTest().getSubCatSearch(""+searchTex);
                    dataSyncResponseCall2.enqueue(new Callback<SubCatListSearch>() {
                        @Override
                        public void onResponse(Call<SubCatListSearch> call, retrofit2.Response<SubCatListSearch> response) {
                            //   Log.e("010919 "," 3ww "+response.body().getFavCatDetailsList().get(0).getId());
                            //    loaderBg.setVisibility(View.GONE);
                            allItems = new ArrayList<SubCategoryObject>();

                            allItems.clear();

                            int l = response.body().getSubCategoryObjects().size();
                            if (l > 0) {
                                for (int i = 0; i < l; i++) {
                                    Log.e("080919 ", i + " subdata " + response.body().getSubCategoryObjects().get(i).getSubcategory_Id());
                                    allItems.add(new SubCategoryObject("" + response.body().getSubCategoryObjects().get(i).getSubcategory_Id(), "" + "" + response.body().getSubCategoryObjects().get(i).getSubcat_Image(), "" + response.body().getSubCategoryObjects().get(i).getSubcat_name(), "35" , ""));

                                    //    allItems.add(new SubCategoryObject("" + jsonArray.getJSONObject(i).getString("Subcategory_Id"), "" + "" + jsonArray.getJSONObject(i).getString("Subcat_Image"), "" + jsonArray.getJSONObject(i).getString("Subcat_name"), "35", ""));

                                }

                                SubCategoryAdapter rcAdapter = new SubCategoryAdapter(getContext(), allItems);
                                lLayout2 = new GridLayoutManager(getContext(), 1);
                                rView2.setHasFixedSize(true);
                                rView2.setLayoutManager(lLayout2);
                                rView2.setAdapter(rcAdapter);
                                rView2.setNestedScrollingEnabled(false);
                                noData.setVisibility(View.GONE);



                            }
                            else
                            {
                                rView2.setVisibility(View.GONE);
                                //   JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                   /*     BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAllMsg(getContext(), "m1",""+response.body().getfMessage().getMessage());
*/
                                noData.setText(""+response.body().getfMessage().getMessage());
                                //  noData.setText("No products found");
                                noData.setVisibility(View.VISIBLE);

                            }

                        }

                        @Override
                        public void onFailure(Call<SubCatListSearch> call, Throwable t) {
                            Log.e("010919 "," 3ww "+t);
                            //    loaderBg.setVisibility(View.GONE);
                        }
                    });
                }

            }
        });

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

    public void show2(final SearchActivity searchActivity)
    {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(searchActivity);
        String searchTex = sharedPrefs.getString("SearchText", "");
        //    Log.e("070919 "," searchTex "+searchTex);
        rView2 = searchActivity.findViewById(R.id.cat_details_rcv);
        noData = searchActivity.findViewById(R.id.noData);
        allItems = new ArrayList<SubCategoryObject>();

        //   searchText.setText(searchTex);

        if(TextUtils.isEmpty(searchTex))
        {
           /* BaseActivity b = new BaseActivity();
            b.ShowAnimatedDialogAll(getContext(), "9");*/
            Toast.makeText(searchActivity,"Please enter search text ",Toast.LENGTH_LONG).show();
        }
        else
        {
             sharedPrefs = PreferenceManager.getDefaultSharedPreferences(searchActivity.getApplicationContext());
                 /*   String Lat = sharedPrefs.getString("Lat", "");
                    String Long = sharedPrefs.getString("Long", "");
*/
        //    loaderBg.setVisibility(View.VISIBLE);


            final Call<SubCatListSearch> dataSyncResponseCall2 = RestApi.get().getRestServiceTest().getSubCatSearch(""+searchTex);
            dataSyncResponseCall2.enqueue(new Callback<SubCatListSearch>() {
                @Override
                public void onResponse(Call<SubCatListSearch> call, retrofit2.Response<SubCatListSearch> response) {
                    //   Log.e("010919 "," 3ww "+response.body().getFavCatDetailsList().get(0).getId());
                //    loaderBg.setVisibility(View.GONE);
                    allItems = new ArrayList<SubCategoryObject>();

                    allItems.clear();

                    int l = response.body().getSubCategoryObjects().size();
                    if (l > 0) {
                        for (int i = 0; i < l; i++) {
                                    Log.e("080919 ", i + " subdata " + response.body().getSubCategoryObjects().get(i).getSubcategory_Id());
                            allItems.add(new SubCategoryObject("" + response.body().getSubCategoryObjects().get(i).getSubcategory_Id(), "" + "" + response.body().getSubCategoryObjects().get(i).getSubcat_Image(), "" + response.body().getSubCategoryObjects().get(i).getSubcat_name(), "35" , ""));

                            //    allItems.add(new SubCategoryObject("" + jsonArray.getJSONObject(i).getString("Subcategory_Id"), "" + "" + jsonArray.getJSONObject(i).getString("Subcat_Image"), "" + jsonArray.getJSONObject(i).getString("Subcat_name"), "35", ""));

                        }

                        SubCategoryAdapter rcAdapter = new SubCategoryAdapter(getContext(), allItems);
                        lLayout2 = new GridLayoutManager(getContext(), 1);
                        rView2.setHasFixedSize(true);
                        rView2.setLayoutManager(lLayout2);
                        rView2.setAdapter(rcAdapter);
                        rView2.setNestedScrollingEnabled(false);
                        noData.setVisibility(View.GONE);



                    }
                    else
                    {
                        rView2.setVisibility(View.GONE);
                        //   JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                   /*     BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAllMsg(getContext(), "m1",""+response.body().getfMessage().getMessage());
*/
                        noData.setText(""+response.body().getfMessage().getMessage());
                        //  noData.setText("No products found");
                        noData.setVisibility(View.VISIBLE);

                    }

                }

                @Override
                public void onFailure(Call<SubCatListSearch> call, Throwable t) {
                    Log.e("010919 "," 3ww "+t);
                //    loaderBg.setVisibility(View.GONE);
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
