package com.it.insidetowns.theinsidetowns.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.it.insidetowns.theinsidetowns.Activities.Home;
import com.it.insidetowns.theinsidetowns.Activities.SideMenuActivity;
import com.it.insidetowns.theinsidetowns.R;

public class SideMenuRecyclerAdapter extends RecyclerView.Adapter<SideMenuRecyclerAdapter.ViewHolder> {

    private Activity context;
    private int currentExistingPosition=0;
    private String[] options ;
    //private String[] icons ;

    SharedPreferences loginEnable;

    public SideMenuRecyclerAdapter(Activity context, int currentPosition)
    {
        this.context = context;
        this.currentExistingPosition = currentPosition;
        loginEnable = context.getSharedPreferences("loginEnable", Context.MODE_PRIVATE);

            options = new String[]{"Explore", "My Profile","Favourite","Change Password","Logout"};
//            icons = new String[]{String.valueOf((char) 0xf015), String.valueOf((char) 0xf0f6), String.valueOf((char) 0xf249),
//                    String.valueOf((char) 0xf2bc), String.valueOf((char) 0xf1da), String.valueOf((char) 0xf570), String.valueOf((char) 0xf0a2),
//                    String.valueOf((char) 0xf09d), String.valueOf((char) 0xf09d),
//                    String.valueOf((char) 0xf2f5)};
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sidemenu_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // setting data to items.
        //holder.listIcon.setText(icons[position]);
        holder.listName.setText(options[position]);


       // if (Utilities.getInstance().menuSelectedPosition == position)
            //holder.itemView.setBackgroundResource(R.mipmap.side_menu_selected_background);
       // else
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));


    }

    @Override
    public int getItemCount() {
        return options.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //Initializing Text views
        final TextView listIcon;
        final TextView listName;
        final View mWhiteCircle;

        ViewHolder(View view) {
            super(view);

            listIcon     = view.findViewById(R.id.list_icon);
            listName     = view.findViewById(R.id.list_item_name);
            mWhiteCircle = view.findViewById(R.id.existing_indicator);

            view.setOnClickListener(view1 -> ((Home) context).onClickCalled(getAdapterPosition()));
        }
    }


}
