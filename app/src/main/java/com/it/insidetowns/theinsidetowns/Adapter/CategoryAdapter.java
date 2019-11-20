package com.it.insidetowns.theinsidetowns.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.gson.reflect.TypeToken;
import com.it.insidetowns.theinsidetowns.Activities.BaseActivity;
import com.it.insidetowns.theinsidetowns.Activities.EventSubCategory;
import com.it.insidetowns.theinsidetowns.Activities.SubCategory;
import com.it.insidetowns.theinsidetowns.R;
import com.it.insidetowns.theinsidetowns.objects.CategoryObject;
import com.it.insidetowns.theinsidetowns.objects.CategoryObjectNew;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell1 on 08-Sep-17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.BooldSampleHolders> {

    private List<CategoryObject> itemList;
    private Context context;
    RelativeLayout relative;
    int number;
    DecimalFormat df ;
    Boolean flag = true;



    private List<CategoryObject> savedQtyList;
    public static List<String> savedItemSNoList;
    public CategoryAdapter(Context context, List<CategoryObject> itemList) {
        this.itemList = itemList;
        this.context = context;
        savedQtyList=new ArrayList<>();
        savedItemSNoList=new ArrayList<>();

    }

    @Override
    public BooldSampleHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row, null);
        return new BooldSampleHolders(layoutView);
    }

    @Override
    public void onBindViewHolder(final BooldSampleHolders holder, final int position) {

        try{
//        holder.countryName.setText(itemList.get(position).getName());
            //  holder.test.setText(itemList.get(position).getTest());
            holder.countryName.setText(""+itemList.get(position).getTest());
            holder.name.setText(itemList.get(position).getCity());

            holder.setIsRecyclable(false);

            List<CategoryObjectNew> list;
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            //   SharedPreferences.Editor editor = sharedPrefs.edit();

            Type type = new TypeToken<List<CategoryObjectNew>>() {
            }.getType();
            Gson gson = new Gson();
            String json = sharedPrefs.getString("AllFavouriteList", null);

            if(!TextUtils.isEmpty(json)) {
                list = gson.fromJson(json, type);

                if (list.size() > 0) {
                    flag=true;
                    for (int x = 0; x < list.size(); x++) {
                        //    Log.e("020919 ", x+" Addfav "+list.get(x).getCity());
                        //    Log.e("020919 ", position+" flag " + "" + itemList.get(position).getId());

                        if (list.get(x).getCity().equalsIgnoreCase("" + itemList.get(position).getId())) {
                            Log.e("170819 ", " Addfav ");
                            Log.e("170819 ", " flag "+position + "      >>>>>   " + itemList.get(position).getId());
                            flag = false;

                        }
                    }
                }
            }
            if(flag == false )
            {
                itemList.get(position).setTest("Leeds");
                holder.favType.setText("Leeds");
            }
            else
            {
                itemList.get(position).setTest("None");
                holder.favType.setText("None");
            }
            String s = itemList.get(position).getTest();

            //   Toast.makeText(context,"view holder "+itemList.get(position).getCity(),Toast.LENGTH_LONG).show();
            if(s.equalsIgnoreCase("Leeds")){
                //holder.discount_text.setVisibility(View.VISIBLE);
                Drawable res = context.getResources().getDrawable(R.mipmap.like_red);
                holder.fav.setImageDrawable(res);

            }
            else
            {
                Drawable res = context.getResources().getDrawable(R.mipmap.like_white);
                holder.fav.setImageDrawable(res);
            }


            holder.favLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                    String ID = sharedPrefs.getString("ID", "");


                    // String s = itemList.get(position).getFav();
                   /* if(s.equalsIgnoreCase("Leeds")){


                    }
                    else
                    {

                    }*/

                    String s = holder.favType.getText().toString().trim();
                    Log.e("170819 "," fav id "+s);
                    //   Toast.makeText(context,"view holder "+itemList.get(position).getCity(),Toast.LENGTH_LONG).show();
                    if(s.equalsIgnoreCase("Leeds")){
                    /*    Log.e("170819 "," Removefav ");
                        Log.e("170819 "," Removefav "+""+itemList.get(position).getId());
*/
                        itemList.get(position).setFav("None");
                        //holder.discount_text.setVisibility(View.VISIBLE);
                        Drawable res = context.getResources().getDrawable(R.mipmap.like_white);

                        holder.fav.setImageDrawable(res);
                        holder.favType.setText("None");
                        AddFav(""+itemList.get(position).getId(),ID,"Removefav");


                    }
                    else
                    {

                    /*    Log.e("170819 "," Addfav ");
                        Log.e("170819 "," Addfav "+""+itemList.get(position).getId());
*/
                        itemList.get(position).setFav("Leeds");
                        holder.favType.setText("Leeds");
                        Drawable res = context.getResources().getDrawable(R.mipmap.like_red);

                        holder.fav.setImageDrawable(res);
                        AddFav(""+itemList.get(position).getId(),ID,"Addfav");

                    }

                }

                private void AddFav(String catId, String uId,String FavFun) {
                  /*  Log.e("010819 "," catId "+catId);
                    Log.e("010819 "," uId "+uId);*/
                  /*  _ProgressDialog = new ProgressDialog(context).show(context,"Favourites","Please wait...",true,true,new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            //  Toast.makeText(DeviceList.this, "Could not find the device!", Toast.LENGTH_SHORT).show();
                            //  finish();
                        }
                    });*/
                    //holder.loaderBg.setVisibility(View.VISIBLE);
                    String url = context.getResources().getString(R.string.AppURL)+"api/Common/"+FavFun+"?Fav.userID="+uId+"&Fav.catid="+catId;
                    Log.e("Url fav",""+url);
                    RequestQueue queue = Volley.newRequestQueue(context);
                    final StringRequest reqQueue = new StringRequest(Request.Method.POST,url , new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("180819 "," "+response);
                            //     loaderBg.setVisibility(View.GONE);
                            //   Toast.makeText(context, "Loged in successfully...", Toast.LENGTH_SHORT);

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                //  Log.e("010819 "," done");
                                Toast.makeText(context, ""+jsonObject.getString("Message"), Toast.LENGTH_LONG);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }


                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
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
                            //      Toast.makeText(context, "Error: " + message, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                JSONObject jsonObject = new JSONObject();


                                // jsonObject.put("Firstname", ""+name.getText().toString());
                               /* jsonObject.put("Email", ""+Email.getText().toString());
                                jsonObject.put("Password", ""+Password.getText().toString());
                               */ //jsonObject.put("Phone", ""+phone.getText().toString());


                                return jsonObject.toString().getBytes("utf-8");
                            } catch (Exception ex) {
                                //    Toast.makeText(context, "Some error occurred. Please try again", Toast.LENGTH_LONG).show();
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
            });



            Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(Color.WHITE)
                    .borderWidthDp(1)
                    .cornerRadiusDp(4)
                    .build();



            Picasso.with(context)
                    .load(itemList.get(position).getPhoto())
                    .fit()
                    .transform(transformation)
                    .into(holder.countryPhoto);

            //     new DownLoadImageTask(holder.countryPhoto).execute(itemList.get(position).getPhoto());

            //   holder.countryPhoto.setImageResource(R.drawable.card);
            final CategoryObject slots = itemList.get(position);
            df = new DecimalFormat("0.00");




            BooldSampleHolders shopListViewHolder = (BooldSampleHolders) holder;
            //  Toast.makeText(context,"view holder",Toast.LENGTH_LONG).show();
            final View view = shopListViewHolder.getItemView();

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString("CatId", ""+itemList.get(position).getId());
                    editor.putString("CatName", ""+holder.name.getText().toString());
                    editor.putString("type", ""+holder.countryName.getText().toString());


                    editor.commit();

                    if(itemList.get(position).getCity().toString().equalsIgnoreCase("Events"))
                    {

                        Intent intent = new Intent(context, EventSubCategory.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    }
                    else {

                        Intent intent = new Intent(context, SubCategory.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    }

              /*  Boolean st = itemList.get(position).getIn_stock();
                if(st)
                {

                    holder.itemNotAvailable.setVisibility(View.GONE);
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    //   Log.e("200918 "," ItemId "+itemList.get(position).getId());
                    editor.putString("ItemId", ""+itemList.get(position).getId());
                    editor.commit();
                    //  Toast.makeText(context,"view holder "+itemList.get(position).getId(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, ItemDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                 //   context.startActivity(new Intent(context,ItemDetails.class));
                }
                else
                    PBUtils.showToast(context, "Sold Out");*/

                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();

        }

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
            if(result!=null){
                imageView.setImageBitmap(result);
            }
            else{
                // imageView.setImageResource(R.drawable.noimage);
            }
        }
    }
    private Bitmap bitmapConvert(String Image) {
        byte[] decodedString = Base64.decode(Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class BooldSampleHolders extends RecyclerView.ViewHolder{
        public TextView countryName,itemNotAvailable;
        public TextView test,name,favType;
        // public CheckedTextView countryPhoto;
        public TextView quantity,countText;
        public ImageView countryPhoto,fav;
        public LinearLayout favLL;
        public ImageView addingImage,removeButton;

        String value;
        public BooldSampleHolders(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            favType = (TextView) itemView.findViewById(R.id.favType);
            countryName = (TextView) itemView.findViewById(R.id.city);
            itemNotAvailable = (TextView) itemView.findViewById(R.id.itemNotAvailable);
            //  test = (TextView)itemView.findViewById(R.id.test);
            //  quantity = (TextView) itemView.findViewById(R.id.quantity);
            countryPhoto = (ImageView) itemView.findViewById(R.id.photo);
            fav = (ImageView) itemView.findViewById(R.id.fav);
            favLL = (LinearLayout) itemView.findViewById(R.id.favLL);


            //  relative= (RelativeLayout)itemView.findViewById(R.id.relative);
     /*       countryPhoto = (CheckedTextView) itemView.findViewById(R.id.simpleCheckedTextView);

            countryPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
*/
        }

        public View getItemView() {
            return this.itemView;
        }

    }
}
