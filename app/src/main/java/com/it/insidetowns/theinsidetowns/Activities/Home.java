package com.it.insidetowns.theinsidetowns.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.it.insidetowns.theinsidetowns.Fragments.FavouriteFragment;
import com.it.insidetowns.theinsidetowns.Fragments.HomeFragment;
import com.it.insidetowns.theinsidetowns.Fragments.MyOffersFragment;
import com.it.insidetowns.theinsidetowns.Helpers.Utilities;
import com.it.insidetowns.theinsidetowns.R;
import com.it.insidetowns.theinsidetowns.network.RestApi;
import com.it.insidetowns.theinsidetowns.objects.chngPwd.ChgMessage;
import com.it.insidetowns.theinsidetowns.objects.chngPwd.Credentials;
import com.it.insidetowns.theinsidetowns.support.BottomNavigationViewHelper;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by Banana on 19-Feb-18.
 */

public class Home extends SideMenuActivity {
    BottomNavigationView navigation;
    Toolbar toolbarTop ;
    TextView mTitle ;
    boolean doubleBackToExitPressedOnce = false;
    TextView menuIcon;
    LinearLayout loaderBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame);
        // inflating contentFrameLayout with the current activity layout.
        LayoutInflater.from(this).inflate(R.layout.home, contentFrameLayout);

        mForwardLayout = findViewById(R.id.main_layout);
        menuIcon = findViewById(R.id.menu_icon);

        loaderBg = (LinearLayout) findViewById(R.id.loaderBg);

     //   if (Utils.isInternetAvaliable()) //net check
        {
        toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);

            menuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // to open or close side menu.
                    openCloseMenu();
                }
            });


      /*  BaseActivity b = new BaseActivity();
        b.AuthorizationTokenValid();
*/
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                final View iconView = menuView.getChildAt(i).findViewById(R.id.icon);
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

    //Method will be called after recycler view item is clicked
    public void  onClickCalled(int itemPosition){

        // to close side menu.
        mDrawerLayout.closeDrawers();

        // checking if old selected position is equal to current selected position
        // intialting the current selected
        Utilities.getInstance().menuSelectedPosition = itemPosition;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // initializing old menu position
        Utilities.getInstance().oldmenuSelectedPosition = itemPosition;

        switch (itemPosition){
            case 0:

                toolbarTop.setVisibility(View.GONE);
                transaction.replace(R.id.content, new HomeFragment());
                transaction.commit();
                hideKeyboard(Home.this);

                break;

            case 1:

                toolbarTop.setVisibility(View.VISIBLE);
                mTitle.setText("My Profile");
                transaction.replace(R.id.content, new MyOffersFragment());
                transaction.commit();
                hideKeyboard(Home.this);

                break;

            case 2:
                toolbarTop.setVisibility(View.VISIBLE);
                mTitle.setText("Favourite");
                transaction.replace(R.id.content, new FavouriteFragment());
                transaction.commit();
                hideKeyboard(Home.this);

                break;

            case 3:

                Button close;
                TextView changePwd_no_underline;

                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.popup_change_password);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                close = (Button) dialog.findViewById(R.id.save);
                final EditText fname = (EditText) dialog.findViewById(R.id.fname2);
                final EditText lname = (EditText) dialog.findViewById(R.id.lname2);
                final EditText mobile = (EditText) dialog.findViewById(R.id.email2);
                changePwd_no_underline = (TextView) dialog.findViewById(R.id.changePwd_no_underline);


                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String oldpwd = fname.getText().toString();
                        String newpwd = lname.getText().toString();
                        String cnfpwd = mobile.getText().toString();

                        if (TextUtils.isEmpty(oldpwd)) {
                            //   ErrorToast.show(UserProfileActivity.this, getString(R.string.alert_current_password), false);
                            BaseActivity b = new BaseActivity();
                            b.ShowAnimatedDialogAllMsg(Home.this, "m1", "" + getString(R.string.alert_current_password));

                        } else if (TextUtils.isEmpty(newpwd)) {

                            BaseActivity b = new BaseActivity();
                            b.ShowAnimatedDialogAllMsg(Home.this, "m1", "" + getString(R.string.alert_valid_password));

                        } else if (TextUtils.isEmpty(cnfpwd)) {

                            BaseActivity b = new BaseActivity();
                            b.ShowAnimatedDialogAllMsg(Home.this, "m1", "" + getString(R.string.alert_confirm_password));

                        } else if (!mobile.getText().toString().equals(lname.getText().toString())) {
                            mobile.setText("");
                            lname.setText("");
                            // ErrorToast.show(UserProfileActivity.this, getString(R.string.alert_password_not_matched), false);

                            BaseActivity b = new BaseActivity();
                            b.ShowAnimatedDialogAllMsg(Home.this, "m1", "" + getString(R.string.alert_password_not_matched));

                        } else {
                            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(Home.this);
                            String ID = sharedPrefs.getString("ID", "");

                            loaderBg.setVisibility(View.VISIBLE);

                            Credentials loginCredentials = new Credentials();
                       /* loginCredentials.id = "" + ID;
                        loginCredentials.password = "" + newpwd;
                        loginCredentials.oldPassword = "" + oldpwd;*/
                            loginCredentials.setUserid(Integer.parseInt(ID));
                            loginCredentials.setPassword(newpwd);
                            loginCredentials.setOldPassword(oldpwd);

                            Log.e("090919 ", " old " + loginCredentials.getOldPassword());


                            final Call<ChgMessage> dataSyncResponseCall = RestApi.get().getRestServiceTest().getChngPwd(loginCredentials);
                            dataSyncResponseCall.enqueue(new Callback<ChgMessage>() {
                                @Override
                                public void onResponse(Call<ChgMessage> call, retrofit2.Response<ChgMessage> response) {
                                    loaderBg.setVisibility(View.GONE);
                                    dialog.dismiss();

                                    Log.e("110919 ", " 3ww " + response.body());
                                    try {

                                        if (!TextUtils.isEmpty("" + response.body())) {
                                            BaseActivity b = new BaseActivity();
                                            b.ShowAnimatedDialogAllMsg(Home.this, "m1", "" + response.body().getMessage());
                                        } else {
                                            BaseActivity b = new BaseActivity();
                                            b.ShowAnimatedDialogAllMsg(Home.this, "m1", "Invalid Old Password");

                                        }
                                    }
                                    catch (Exception e)
                                    {
                                        BaseActivity b = new BaseActivity();
                                        b.ShowAnimatedDialogAllMsg(Home.this, "m1", "Invalid Old Password");

                                    }

                                    //  String l = response.body().getMessage();
                                    //    Log.e("090919 ", " 3ww " + response.body().getMessage());

                                }

                                @Override
                                public void onFailure(Call<ChgMessage> call, Throwable t) {
                                    loaderBg.setVisibility(View.GONE);
                                    dialog.dismiss();
                                    //    Log.e("090919 ", " 3ww " + t);
                                    BaseActivity b = new BaseActivity();
                                    b.ShowAnimatedDialogAllMsg(Home.this, "m1", "Invalid Old Password" );



                                }
                            });
                        }


                    }
                });
                dialog.show();

                break;

            case 4:
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("ID", "");
                editor.commit();

                Intent intent = new Intent(this, Login.class);
                startActivity(intent);

                break;

            default:
                // to close side menu
                mDrawerLayout.closeDrawers();

                break;
        }
    }
}
