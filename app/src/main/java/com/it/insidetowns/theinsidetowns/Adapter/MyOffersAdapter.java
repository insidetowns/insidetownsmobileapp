package com.it.insidetowns.theinsidetowns.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.it.insidetowns.theinsidetowns.R;
import com.it.insidetowns.theinsidetowns.objects.MyOffersObject;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell1 on 08-Sep-17.
 */

public class MyOffersAdapter extends RecyclerView.Adapter<MyOffersAdapter.BooldSampleHolders> {

    private List<MyOffersObject> itemList;
    private Context context;

    RelativeLayout relative;
    int number;
    DecimalFormat df ;


    private List<MyOffersObject> savedQtyList;
    public static List<String> savedItemSNoList;
    public MyOffersAdapter(Context context, List<MyOffersObject> itemList) {
        this.itemList = itemList;
        this.context = context;
        savedQtyList=new ArrayList<>();
        savedItemSNoList=new ArrayList<>();

    }

    @Override
    public BooldSampleHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_offers_details_row, null);
        return new BooldSampleHolders(layoutView);
    }

    @Override
    public void onBindViewHolder(final BooldSampleHolders holder, final int position) {

        try{
//        holder.countryName.setText(itemList.get(position).getName());
      //  holder.test.setText(itemList.get(position).getTest());
          holder.countryName.setText(""+itemList.get(position).getVendorname());
          holder.name.setText(itemList.get(position).getDiscount());
          String dt[] = itemList.get(position).getReedemedOn().split("T");
          String dtt[] = dt[0].split("-");
          holder.desc.setText(dtt[2]+"-"+dtt[1]+"-"+dtt[0]+ " "+dt[1].substring(0,5));

          holder.setIsRecyclable(false);


          if(!TextUtils.isEmpty(itemList.get(position).getProductImage()))
          {
              Transformation transformation = new RoundedTransformationBuilder()
                      .borderColor(Color.WHITE)
                      .borderWidthDp(1)
                      .cornerRadiusDp(4)
                      .build();



              Picasso.with(context)
                      .load(itemList.get(position).getProductImage())
                      .fit()
                      .transform(transformation)
                      .into(holder.countryPhoto);
          }


     //   holder.countryPhoto.setImageResource(R.drawable.card);
        final MyOffersObject slots = itemList.get(position);
        df = new DecimalFormat("0.00");




        BooldSampleHolders shopListViewHolder = (BooldSampleHolders) holder;
        //  Toast.makeText(context,"view holder",Toast.LENGTH_LONG).show();
        final View view = shopListViewHolder.getItemView();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
