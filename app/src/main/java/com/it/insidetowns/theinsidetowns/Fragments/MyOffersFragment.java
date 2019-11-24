package com.it.insidetowns.theinsidetowns.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.it.insidetowns.theinsidetowns.Adapter.MyOffersAdapter;
import com.it.insidetowns.theinsidetowns.R;
import com.it.insidetowns.theinsidetowns.network.RestApi;
import com.it.insidetowns.theinsidetowns.objects.BannerDetails;
import com.it.insidetowns.theinsidetowns.objects.CategoryObject;
import com.it.insidetowns.theinsidetowns.objects.FavObjects.ProfileObject;
import com.it.insidetowns.theinsidetowns.objects.MyOffersObject;
import com.it.insidetowns.theinsidetowns.objects.ProfileUpdate.ProfUpdateMessage;
import com.it.insidetowns.theinsidetowns.objects.ProfileUpdate.ProfileCredentials;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
public class MyOffersFragment extends Fragment {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    static final Integer CAMERA = 0x1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int i;
    RelativeLayout layout;
    RecyclerView rView2;
    ImageView user_pic;
    TextView curAddress, edit;
    EditText mobile, userName;
    LinearLayout loaderBg;
    Boolean porfileImage = false;
    String userChooseTask;
    String encImage = "", User_Pic;
    ArrayList<BannerDetails> bannerDetails = new ArrayList<BannerDetails>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private LayoutInflater mInflater;
    private Context context;
    private Location mLocation;
    private FragmentActivity myContext;
    private LinearLayout mGallery;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private GridLayoutManager lLayout2;

    public MyOffersFragment() {
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_fragment_myoffers, container, false);
        askForPermission(Manifest.permission.CAMERA, CAMERA);
        //  final SwipeRefreshLayout pullToRefresh = v.findViewById(R.id.pullToRefresh);
       /* pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //  refreshData(); // your code
                ConnectivityManager con_mngr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo nw_information = con_mngr.getActiveNetworkInfo();
                if (nw_information != null && nw_information.isConnected()) {
                    MyOfferApi();

                }
                else {
                    Toast.makeText(getContext(), "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }

                pullToRefresh.setRefreshing(false);
            }
        });*/

        mGallery = (LinearLayout) v.findViewById(R.id.id_gallery);
        rView2 = (RecyclerView) v.findViewById(R.id.recycler_view_govt);
        userName = (EditText) v.findViewById(R.id.userName);
        mobile = (EditText) v.findViewById(R.id.mobile);
        curAddress = (TextView) v.findViewById(R.id.curAddress);
        edit = (TextView) v.findViewById(R.id.edit);
        loaderBg = (LinearLayout) v.findViewById(R.id.loaderBg);
        user_pic = (ImageView) v.findViewById(R.id.user_pic);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        userName.setText("" + sharedPrefs.getString("username", ""));
        if(sharedPrefs.getString("mobile", "").equalsIgnoreCase("Add Ph.no")){
            mobile.setHint("" + sharedPrefs.getString("mobile", ""));

        }else{
            mobile.setText("" + sharedPrefs.getString("mobile", ""));

        }
        curAddress.setText("" + sharedPrefs.getString("CurAddress", ""));
        ProfileAPI();

        //  User_Pic = ""+sharedPrefs.getString("User_Pic", "");
        // User_Pic = ""+"http://theitapp.tech/Content/UserPic/3Image.jpg";

        //   Log.e("290819 "," User_Pic "+User_Pic);

        if (!TextUtils.isEmpty(User_Pic)) {
            Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(Color.WHITE)
                    .borderWidthDp(1)
                    .cornerRadiusDp(50)
                    .build();


            Picasso.with(context)
                    .load(User_Pic)
                    .fit()
                    .transform(transformation)
                    .into(user_pic);

            //    new DownLoadImageTask(user_pic).execute(User_Pic);

        }

        userName.setEnabled(false);

        //  user_pic.setBackground(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        user_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = edit.getText().toString().trim();
                if (s.equalsIgnoreCase("Update")) {
                    final CharSequence[] items = {getResources().getString(R.string.text_take_photo), getResources().getString(R.string.text_choose_from_gallery)};
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                    builder.setTitle(getResources().getString(R.string.text_add_photo));
                    // Log.e("Camera "," 00");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            boolean result = CameraCapture.checkPermission(context);
                            //   Log.e("Camera ", " result " + result);
                            if (items[item].equals(getResources().getString(R.string.text_take_photo))) {
                                //   Log.e("Camera ", " 11");
                                userChooseTask = getResources().getString(R.string.text_take_photo);
                                if (result) {
                                    //      Log.e("Camera ", " 22");
                                    cameraIntent();

                                }
                            } else if (items[item].equals(getResources().getString(R.string.text_choose_from_gallery))) {
                                userChooseTask = getResources().getString(R.string.text_choose_from_gallery);
                                if (result) {
                                    //    Log.e("Camera ", " 33");
                                    galleryIntent();
                                }
                            }
                        }
                    });
                    builder.show();
                }
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = edit.getText().toString().trim();
                if (s.equalsIgnoreCase("edit")) {

                    edit.setText("Update");
                    userName.setEnabled(true);
                    mobile.setEnabled(true);

                   /* getActivity().getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);*/
                    userName.requestFocus();
                } else {
                    if (TextUtils.isEmpty(userName.getText().toString())) {
                        userName.setError("Please enter Username.");
                        userName.requestFocus();
                    } else if (TextUtils.isEmpty(mobile.getText().toString())) {
                        mobile.setError("Please enter mobile number.");
                        mobile.requestFocus();
                    } else if (mobile.getText().length() < 10) {
                        mobile.setError("Please enter valid mobile number.");
                        mobile.requestFocus();
                    } else {
                        edit.setText("Edit");
                        userName.setEnabled(false);
                        mobile.setEnabled(false);
                        //  UpdateApi();

                        NewUpdateApi();
                    }

                }
            }

        });


        MyOfferApi();

        return v;

    }

    private Bitmap bitmapConvert(String Image) {
        byte[] decodedString = Base64.decode(Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.text_select_file)), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void askForPermission(String permission, Integer requestCode) {

        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
            }
        } else {

            // Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
            //  mScannerView.setResultHandler(this);
            //  mScannerView.startCamera();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {

                bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData());

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 75, bytes);
                byte[] b = bytes.toByteArray();
                encImage = Base64.encodeToString(b, Base64.DEFAULT);
                Log.e("200418 gallary", " " + encImage);
                porfileImage = true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        user_pic.setImageBitmap(bm);
        //  user_pic.setRotation(90);
        //   user_pic.setBackground(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

        //   Cache.putData(CatchValue.User_Pic, getContext(), thumbnail, Cache.CACHE_LOCATION_DISK);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        byte[] b = bytes.toByteArray();
        //   String imageString = Base64.encodeBase64String(byteArray);
        encImage = Base64.encodeToString(b, Base64.DEFAULT);
        //Log.e("200418 cam"," "+encImage);
        porfileImage = true;
        // Cache.putData(CatchValue.U_Pic, this, encImage, Cache.CACHE_LOCATION_DISK);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".PNG");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        user_pic.setImageBitmap(thumbnail);
        // user_pic.setRotation(90);
        //   user_pic.setBackground(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    private void NewUpdateApi() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String ID = sharedPrefs.getString("ID", "");

      /*  jsonObject.put("Userid", ""+ID);
        jsonObject.put("Firstname", ""+userName.getText().toString());
        jsonObject.put("phone", ""+mobile.getText().toString());
        if(porfileImage)
            jsonObject.put("Profile_Pic", ""+encImage);*/

        ProfileCredentials profileCredentials = new ProfileCredentials();
        profileCredentials.setUserid(Integer.parseInt(ID));
        profileCredentials.setFirstname(userName.getText().toString());
        profileCredentials.setPhone(mobile.getText().toString());
        if (porfileImage)
            profileCredentials.setProfile_Pic(encImage);


        //   Log.e("090919 ", " Email " + Email.getText().toString().trim());


        loaderBg.setVisibility(View.VISIBLE);
        final Call<ProfUpdateMessage> dataSyncResponseCall = RestApi.get().getRestServiceTest().getProfUpdate(profileCredentials);
        //    final Call<LoginResponseRetro> dataSyncResponseCall = RestApi.get().getRestServiceTest().getLogin(Email.getText().toString().trim(),Password.getText().toString().trim());

        dataSyncResponseCall.enqueue(new Callback<ProfUpdateMessage>() {
            @Override
            public void onResponse(Call<ProfUpdateMessage> call, retrofit2.Response<ProfUpdateMessage> response) {
                //   Log.e("010919 "," 3ww "+response.body().getFavCatDetailsList().get(0).getId());
                loaderBg.setVisibility(View.GONE);


                try {

                    //   Log.e("110919 ", " 3ww " + response.body());
                    //  JSONObject jsonObject = new JSONObject(response);
                    //  Log.e("110919 ", " 3ww " + jsonObject.getString("username"));

                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAllMsg(context, "m2", "" + response.body().getMessage());

                } catch (Exception e) {
                    e.printStackTrace();
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAllMsg(getContext(), "m1", "Profile not updated, Please try again.");

                }


                //    editor.putString("mobile", ""+jsonObject.getString("username"));


            }

            @Override
            public void onFailure(Call<ProfUpdateMessage> call, Throwable t) {


                loaderBg.setVisibility(View.GONE);
            }
        });
    }

    private void UpdateApi() {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        final String ID = sharedPrefs.getString("ID", "");

        Log.e("270819 ", " encImage " + encImage);
        encImage = encImage.replaceAll("\\n", "");
        // encImage = encImage.replace("\5","");
        Log.e("270819 ", " encImage " + encImage);
        loaderBg.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(context);
        final StringRequest reqQueue = new StringRequest(Request.Method.POST, "" + getResources().getString(R.string.AppURL) + "api/user/ProfileUpdate", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loaderBg.setVisibility(View.GONE);
                //    Toast.makeText(context, "Data loaded successfully...", Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    //  Toast.makeText(context, ""+jsonObject1.getString("Message"), Toast.LENGTH_LONG).show();
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAllMsg(context, "m2", "" + jsonObject.getString("Message"));

                   /* SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString("username", ""+userName.getText().toString());
                    editor.putString("mobile", ""+mobile.getText().toString());
                    editor.commit();*/

                    hideSoftKeyboard(userName);
                    hideSoftKeyboard(mobile);

                    ProfileAPI();

                    //  InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    //  imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    //    imm.hideSoftInputFromWindow(new View(context).getWindowToken(), InputMethodManager.RESULT_HIDDEN);
                    // imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getRootView().getWindowToken(), 0);

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
                    b.ShowAnimatedDialogAllMsg(context, "m1", "" + message);

                } else if (volleyError instanceof ServerError) {

                    if (volleyError.networkResponse.statusCode == 417) {
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(context, "7");
                        //   message = "No SubCategories are registered.";
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
                //   Toast.makeText(context, "Error: " + message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject jsonObject = new JSONObject();


                    jsonObject.put("Userid", "" + ID);
                    jsonObject.put("Firstname", "" + userName.getText().toString());
                    jsonObject.put("phone", "" + mobile.getText().toString());
                    if (porfileImage)
                        jsonObject.put("Profile_Pic", "" + encImage);
                    //    jsonObject.put("Phone", ""+phone.getText().toString());


                    return jsonObject.toString().getBytes("utf-8");
                } catch (Exception ex) {
                    // Toast.makeText(context, "Some error occurred. Please try again", Toast.LENGTH_LONG).show();
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

    private void ProfileAPI() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String ID = sharedPrefs.getString("ID", "");

        loaderBg.setVisibility(View.VISIBLE);
        final Call<ProfileObject> dataSyncResponseCall = RestApi.get().getRestServiceTest().getProfile("" + ID);
        dataSyncResponseCall.enqueue(new Callback<ProfileObject>() {
            @Override
            public void onResponse(Call<ProfileObject> call, retrofit2.Response<ProfileObject> response) {
                //   Log.e("010919 "," 3ww "+response.body().getFavCatDetailsList().get(0).getId());
                loaderBg.setVisibility(View.GONE);


                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                //  editor.putString("ID", ""+jsonObject.getString("ID"));
                try {
                    if (response.body().getFirstname() != null) {
                        editor.putString("username", "" + response.body().getFirstname());
                    }

                    if (response.body().getMobilenumber() != null) {
                        editor.putString("mobile", "" + response.body().getMobilenumber());
                    } else {
                        editor.putString("mobile", "" + "Add Ph.no");
                    }
                    editor.putString("User_Pic", "" + response.body().getUser_Pic());
                    editor.commit();
                    //    Log.e("010919 "," profile "+response.body().getUser_Pic());
                    user_pic.setImageDrawable(getResources().getDrawable(R.mipmap.profile_picss));

                    String s = "" + response.body().getUser_Pic();
                    Log.e("110919 ", " " + s);
                    if (!TextUtils.isEmpty(s)) {
                        Transformation transformation = new RoundedTransformationBuilder()
                                .borderColor(Color.WHITE)
                                .borderWidthDp(1)
                                .cornerRadiusDp(50)
                                .build();
                        Picasso.with(context)
                                .load(response.body().getUser_Pic())
                                .fit()
                                .transform(transformation)
                                .into(user_pic);


                        //      new DownLoadImageTask(user_pic).execute(s);
                    }


                    //    editor.putString("mobile", ""+jsonObject.getString("username"));

                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ProfileObject> call, Throwable t) {
                Log.e("010919 ", " 3ww " + t);
                loaderBg.setVisibility(View.GONE);
            }
        });
    }

    public void hideSoftKeyboard(EditText editText) {
      /*  InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
      */
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                editText.getWindowToken(), 0);
    }

    private void MyOfferApi() {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String ID = sharedPrefs.getString("ID", "");

        //  sub_catName.setText(""+ sharedPrefs.getString("CatName", ""));

        loaderBg.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(context);
        final StringRequest reqQueue = new StringRequest(Request.Method.GET, "" + getResources().getString(R.string.AppURL) + "api/user/DiscountReedemptions?UserID=" + ID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loaderBg.setVisibility(View.GONE);
                //    Toast.makeText(context, "Data loaded successfully...", Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("ReedemDetails");
                    //    Log.e("210819 "," "+jsonArray);
                    //   JSONArray jsonArray = new JSONArray(jsonObject.get("CategoriesDetails"));
                    //  Log.e("030819 "," jsonArray "+jsonArray);

                    List<MyOffersObject> allItems = new ArrayList<MyOffersObject>();

                    allItems.clear();
                    if (jsonArray.length() > 0) {


                        for (int i = 0; i < jsonArray.length(); i++) {

                            //  allItems.add(new CategoryObjectNew("1",R.mipmap.fashion,"Palm beach concert", "Beach Road","simply dummy text of the printing and typesetting industry.  "));

                            // String ss = jsonArray.getJSONObject(i).getString("Category_name");
                            // Log.e("030819 "," ss "+ss);
                            allItems.add(new MyOffersObject("" + jsonArray.getJSONObject(i).getString("Discount"), "" + "" + jsonArray.getJSONObject(i).getString("Vendorname"), "" + jsonArray.getJSONObject(i).getString("ReedemedOn"), "" + jsonArray.getJSONObject(i).getString("ProductImage")));


                        }
                       /* MyOffersAdapter rcAdapter = new MyOffersAdapter(context, allItems);
                        lLayout2 = new GridLayoutManager(context, 1);

                        rView2.setHasFixedSize(true);
                        rView2.setLayoutManager(lLayout2);
                        rView2.setAdapter(rcAdapter);*/
                    }

                    jsonArray = jsonObject.getJSONArray("ReedemDetailsType");
                    if (jsonArray.length() > 0) {


                        for (int i = 0; i < jsonArray.length(); i++) {

                            //  allItems.add(new CategoryObjectNew("1",R.mipmap.fashion,"Palm beach concert", "Beach Road","simply dummy text of the printing and typesetting industry.  "));

                            // String ss = jsonArray.getJSONObject(i).getString("Category_name");
                            // Log.e("030819 "," ss "+ss);
                            allItems.add(new MyOffersObject("" + jsonArray.getJSONObject(i).getString("Discount"), "" + "" + jsonArray.getJSONObject(i).getString("Vendorname"), "" + jsonArray.getJSONObject(i).getString("ReedemedOn"), "" + jsonArray.getJSONObject(i).getString("ProductImage")));


                        }

                    }

                    if (allItems.size() > 0) {
                        MyOffersAdapter rcAdapter = new MyOffersAdapter(context, allItems);
                        lLayout2 = new GridLayoutManager(context, 1);

                        rView2.setHasFixedSize(true);
                        rView2.setLayoutManager(lLayout2);
                        rView2.setAdapter(rcAdapter);
                    } else {
                        //    Log.e("110819 "," no data ");
                        JSONObject jsonObject1 = jsonObject.getJSONObject("Message");
                        //    Log.e("110819 "," no data "+jsonObject1.getString("Message"));
                        //     Toast.makeText(context, ""+jsonObject1.getString("Message"), Toast.LENGTH_LONG).show();
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAllMsg(context, "m1", "" + jsonObject1.getString("Message"));

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
                    b.ShowAnimatedDialogAllMsg(context, "m1", "" + message);

                } else if (volleyError instanceof ServerError) {

                    if (volleyError.networkResponse.statusCode == 417) {
                        BaseActivity b = new BaseActivity();
                        b.ShowAnimatedDialogAll(context, "7");
                        //   message = "No SubCategories are registered.";
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
                //   Toast.makeText(context, "Error: " + message, Toast.LENGTH_LONG).show();
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
                    // Toast.makeText(context, "Some error occurred. Please try again", Toast.LENGTH_LONG).show();
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
      /*  allItems.add(new CategoryObject("5",R.mipmap.bikes,"Bikes", "35",""));
        allItems.add(new CategoryObject("6",R.mipmap.house_keeping,"House Keeping","40",""));
        allItems.add(new CategoryObject("7",R.mipmap.food,"Food","35",""));
        allItems.add(new CategoryObject("8",R.mipmap.fitness,"Fitness", "40",""));
        allItems.add(new CategoryObject("1",R.mipmap.fashion,"Fashon", "35",""));
        allItems.add(new CategoryObject("2",R.mipmap.beauty,"Beauty & Spa","40",""));
        allItems.add(new CategoryObject("3",R.mipmap.events,"Events","35",""));
        allItems.add(new CategoryObject("4",R.mipmap.travel,"Travel", "40",""));
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

    class DownLoadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String... urls) {
            String urlOfImage = urls[0];
            Bitmap logo = null;

            try {
                if (!urlOfImage.contains("data:image/jpeg;base64")) {
                    InputStream in = new java.net.URL(urlOfImage).openStream();
                    logo = BitmapFactory.decodeStream(in);

//                    ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(myUri, "r");
//                    FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
//                    logo = BitmapFactory.decodeFileDescriptor(fileDescriptor);
//                    parcelFileDescriptor.close();


                } else {
                    String actualBitmap = urlOfImage.substring(0, urlOfImage.indexOf(",") + 1);
                    urlOfImage = urlOfImage.replace(actualBitmap, "");
                    logo = bitmapConvert(urlOfImage);
                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
            } else {
                // imageView.setImageResource(R.drawable.noimage);
            }
        }
    }


}
