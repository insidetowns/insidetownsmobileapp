package com.it.insidetowns.theinsidetowns.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.it.insidetowns.theinsidetowns.Adapter.SideMenuRecyclerAdapter;
import com.it.insidetowns.theinsidetowns.Helpers.Utilities;
import com.it.insidetowns.theinsidetowns.R;

public class SideMenuActivity extends AppCompatActivity {

    //Declaring variables
    RelativeLayout mLeftDrawer,profileLayout;
    public DrawerLayout mDrawerLayout;
    public LinearLayout mForwardLayout;
    RecyclerView sideMenuRecycler;
    TextView sideName, appVersion;
    Activity currentActivity;
    int handlerDuration = 280;  // duration to navigate to other screen as menu closes.
    SideMenuRecyclerAdapter sideMenuRecyclerAdapter;
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_menu);


        // initializing current activity.
        currentActivity = this;

        // initializing items.
        mLeftDrawer       =  findViewById(R.id.left_drawer);
        mDrawerLayout     =  findViewById(R.id.draw_layout);
        appVersion     =  findViewById(R.id.app_version);
        //inflating list view to show locations list
        sideMenuRecycler  = findViewById(R.id.menu_listview);

        profileLayout       = findViewById(R.id.profile_layout);
        sideName = findViewById(R.id.side_name);
        //profileImage = findViewById(R.id.profile_img);

        //Initializing Gson object
        Gson gson = new Gson();
        // using shared preference to signIn and for date format.
//        final SharedPreferences userSharedPreference = getSharedPreferences(Utilities.getInstance().UserPref, MODE_PRIVATE);
//        // session string to check whether this exists in shared preference
//        final String sessionString = userSharedPreference.getString(Utilities.getInstance().sessionCheckingString,"");
//        //Setting data to sign in user model
//        Utilities.getInstance().signInUserModel = gson.fromJson(sessionString, SignInUserModel.class);
//
//        appVersion.setText("Version "+Utilities.getInstance().getAppVersion(currentActivity));

        // to stop the opening of dlayout on swipe gesture.
        //mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //Method to initialize views, classes and variables
        //initialize();
        // to stop the opening of dlayout on swipe gesture.
        //mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        // setting adapter to list view
        sideMenuRecyclerAdapter = new SideMenuRecyclerAdapter(this,0);
        sideMenuRecycler.setAdapter(sideMenuRecyclerAdapter);
        //Method to set and load data
        //setData();
        //Method to initialize all listeners
        //menuInitListeners();

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                // setting x for transition of the screen when side menu is opened.
                mForwardLayout.setX(slideOffset * drawerView.getWidth());
                // to bring the child to front.
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

//        profileLayout.setOnClickListener(view -> {
//            Intent editProfileIntent = new Intent(currentActivity, ProfileActivity.class);
//            startActivity(editProfileIntent);
//            Utilities.getInstance().moveForwardTransition(currentActivity);
//        });

    }


    // method to open or close side menu
    public  void openCloseMenu()
    {
        // hide keyboard if opened
        hideSoftKeyBoard();
        // check if drawer is opened
        if (mDrawerLayout.isDrawerOpen(mLeftDrawer))
        {
            // close drawers(menu)
            mDrawerLayout.closeDrawers();
        }
        else {
            // open drawer
            mDrawerLayout.openDrawer(mLeftDrawer);
        }
    }

    // method to hide keyboard
    private void hideSoftKeyBoard(){
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//        //Setting name in side menu
//        sideName.setText(Utilities.getInstance().signInUserModel.getUserDetails().getUserFname()+ " " + Utilities.getInstance().signInUserModel.getUserDetails().getUserLname());
//        //Setting profile image in side menu
//        Glide.with(getApplicationContext())
//                .load(getResources().getString(R.string.image_base_url)+Utilities.getInstance().signInUserModel.getUserDetails().getUserProfileImgUrl())
//                .apply(new RequestOptions()
//                        .placeholder(R.drawable.profile_image)
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .skipMemoryCache(true)
//                        .dontAnimate())
//                .into(profileImage);
    }
}
