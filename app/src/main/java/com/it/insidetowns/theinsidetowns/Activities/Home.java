package com.it.insidetowns.theinsidetowns.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;


import com.it.insidetowns.theinsidetowns.Fragments.FavouriteFragment;
import com.it.insidetowns.theinsidetowns.Fragments.HomeFragment;
import com.it.insidetowns.theinsidetowns.Fragments.MyOffersFragment;
import com.it.insidetowns.theinsidetowns.R;
import com.it.insidetowns.theinsidetowns.support.BottomNavigationViewHelper;


/**
 * Created by Banana on 19-Feb-18.
 */

public class Home extends AppCompatActivity {
    BottomNavigationView navigation;
    Toolbar toolbarTop ;
    TextView mTitle ;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
     //   if (Utils.isInternetAvaliable()) //net check
        {
        toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);


      /*  BaseActivity b = new BaseActivity();
        b.AuthorizationTokenValid();
*/
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
                final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
                final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                // set your height here
                layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics);
                // set your width here
                layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics);
                iconView.setLayoutParams(layoutParams);
            }

            try{
                BottomNavigationViewHelper.disableShiftMode(navigation);

            }catch (Exception e){
                e.printStackTrace();
            }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.content, new HomeFragment());
            toolbarTop.setVisibility(View.GONE);

        transaction.commit();
    }
    /*else
        {

        }*/
        //backArrow.setVisibility(View.GONE);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbarTop.setVisibility(View.GONE);
                    transaction.replace(R.id.content, new HomeFragment());
                    transaction.commit();
                    hideKeyboard(Home.this);
                    return true;
                case R.id.navigation_near_me:
                    toolbarTop.setVisibility(View.VISIBLE);
                    mTitle.setText("My Profile");
                    transaction.replace(R.id.content, new MyOffersFragment());
                    transaction.commit();
                    hideKeyboard(Home.this);
                    return true;

                case R.id.navigation_categories:
                    toolbarTop.setVisibility(View.VISIBLE);
                    mTitle.setText("Favourite");
                    transaction.replace(R.id.content, new FavouriteFragment());
                    transaction.commit();
                    hideKeyboard(Home.this);
                    return true;


            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        //Checking for fragment count on backstack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this,"Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
  /*          super.onBackPressed();
            return;*/
  Home.this.finishAffinity();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
      /*  BaseActivity b = new BaseActivity();
        b.AuthorizationTokenValid();*/
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
