package com.it.insidetowns.theinsidetowns.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.it.insidetowns.theinsidetowns.Activities.EventSubCategory;
import com.it.insidetowns.theinsidetowns.Activities.SubCategory;
import com.it.insidetowns.theinsidetowns.R;
import com.it.insidetowns.theinsidetowns.objects.CategoryObjectNew;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell1 on 08-Sep-17.
 */

public class FavouriteDetailsAdapter extends RecyclerView.Adapter<FavouriteDetailsAdapter.BooldSampleHolders> {

    private List<CategoryObjectNew> itemList;
    private Context context;

    RelativeLayout relative;
    int number;
    DecimalFormat df ;


    private List<CategoryObjectNew> savedQtyList;
    public static List<String> savedItemSNoList;
    public FavouriteDetailsAdapter(Context context, List<CategoryObjectNew> itemList) {
        this.itemList = itemList;
        this.context = context;
        savedQtyList=new ArrayList<>();
        savedItemSNoList=new ArrayList<>();

    }

    @Override
    public BooldSampleHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_details_row, null);
        return new BooldSampleHolders(layoutView);
    }

    @Override
    public void onBindViewHolder(final BooldSampleHolders holder, final int position) {

        try{
//        holder.countryName.setText(itemList.get(position).getName());
      //  holder.test.setText(itemList.get(position).getTest());
        holder.countryName.setText(" "+itemList.get(position).getTest());
        holder.name.setText(itemList.get(position).getTest());
        holder.desc.setText(itemList.get(position).getQuantity());
            holder.setIsRecyclable(false);



        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.WHITE)
                .borderWidthDp(1)
                .cornerRadiusDp(8)
                .build();



        Picasso.with(context)
                .load(itemList.get(position).getPhoto())
                .fit()
                .transform(transformation)
                .into(holder.countryPhoto);
     //   holder.countryPhoto.setImageResource(R.drawable.card);
        final CategoryObjectNew slots = itemList.get(position);
        df = new DecimalFormat("0.00");




        BooldSampleHolders shopListViewHolder = (BooldSampleHolders) holder;
        //  Toast.makeText(context,"view holder",Toast.LENGTH_LONG).show();
        final View view = shopListViewHolder.getItemView();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("CatId", ""+itemList.get(position).getCity());
                editor.putString("CatName", ""+holder.name.getText().toString());
                editor.putString("type", ""+holder.desc.getText().toString());
                editor.commit();

                if(itemList.get(position).getTest().toString().equalsIgnoreCase("Events"))
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

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class BooldSampleHolders extends RecyclerView.ViewHolder{
        public TextView countryName,desc;
        public TextView name;

        public ImageView countryPhoto;


        String value;
        public BooldSampleHolders(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            countryName = (TextView) itemView.findViewById(R.id.city);
            desc = (TextView) itemView.findViewById(R.id.desc);
            countryPhoto = (ImageView) itemView.findViewById(R.id.photo);

        }

        public View getItemView() {
            return this.itemView;
        }

    }
}
