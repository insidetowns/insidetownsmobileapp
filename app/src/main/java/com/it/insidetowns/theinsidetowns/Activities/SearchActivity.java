package com.it.insidetowns.theinsidetowns.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.it.insidetowns.theinsidetowns.R;
import com.it.insidetowns.theinsidetowns.Search.OneFragment;
import com.it.insidetowns.theinsidetowns.Search.ShopSearchFragment;
import com.it.insidetowns.theinsidetowns.Search.ThreeFragment;
import com.it.insidetowns.theinsidetowns.Search.TwoFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
  //  RecyclerView rView2;
    LinearLayout BackArrow;
    TextView sub_catName;
    private GridLayoutManager lLayout2;
    LinearLayout loaderBg,show;
    EditText searchText;
  //  private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        BackArrow = findViewById(R.id.BackArrow);
        searchText = findViewById(R.id.searchText);

        sub_catName  = findViewById(R.id.sub_catName);
        loaderBg = (LinearLayout) findViewById(R.id.loaderBg);
        show = (LinearLayout) findViewById(R.id.show);
        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity.super.onBackPressed();
            }
        });


        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(searchText.getText().toString().trim()))
                {
                    BaseActivity b = new BaseActivity();
                    b.ShowAnimatedDialogAll(SearchActivity.this, "9");
                }
                else
                {
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(SearchActivity.this);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString("SearchText", ""+searchText.getText().toString().trim());
                    editor.commit();

                /*    OneFragment OF =  new OneFragment();
                    OF.show(SearchActivity.this);
*/
                   ThreeFragment OF =  new ThreeFragment();
                            OF.show(SearchActivity.this);
                }

            }
        });




      /*  toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                int position = tab.getPosition();

                    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

                    switch (position) {
                        case 0:
                            //    Toast.makeText(SearchActivity.this,"1",Toast.LENGTH_LONG).show();
                            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(SearchActivity.this);
                            SharedPreferences.Editor editor = sharedPrefs.edit();
                            editor.putString("SearchText", ""+searchText.getText().toString().trim());
                            editor.commit();

                            OneFragment OF =  new OneFragment();
                            OF.show(SearchActivity.this);





                            break;
                        case 1:
                       //      Toast.makeText(SearchActivity.this,"2",Toast.LENGTH_LONG).show();

                            //    new TwoFragment();

                            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(SearchActivity.this);

                            editor = sharedPrefs.edit();
                            editor.putString("SearchText", ""+searchText.getText().toString().trim());
                            editor.commit();


                            TwoFragment TF =  new TwoFragment();
                            TF.show2(SearchActivity.this);
                          //  adapter.addFragment(new TwoFragment(), "Sub_Cat");

                       /*     OneFragment OF2 =  new OneFragment();
                            OF2.show(SearchActivity.this);
*/
                            break;
                        case 2:
                            Toast.makeText(SearchActivity.this,"3",Toast.LENGTH_LONG).show();
                            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(SearchActivity.this);
                             editor = sharedPrefs.edit();
                            editor.putString("SearchText", ""+searchText.getText().toString().trim());
                            editor.commit();

                            ThreeFragment Tff =  new ThreeFragment();
                            Tff.show(SearchActivity.this);
                        /*    OneFragment OF3 =  new OneFragment();
                            OF3.show(SearchActivity.this);
*/
                            break;
                        case 3:

                            break;

                    }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

      //  viewPager.setAdapter(mSectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

                switch (position) {
                    case 0:
                    //    Toast.makeText(SearchActivity.this,"1",Toast.LENGTH_LONG).show();
                        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(SearchActivity.this);
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.putString("SearchText", ""+searchText.getText().toString().trim());
                        editor.commit();
/*
                        OneFragment OF =  new OneFragment();
                        OF.show(SearchActivity.this);
                        adapter.addFragment(new OneFragment(), "Products");*/
                    //    Toast.makeText(SearchActivity.this,"11 "+searchText.getText().toString().trim(),Toast.LENGTH_LONG).show();

                        break;
                    case 1:
                      //  Toast.makeText(SearchActivity.this,"2",Toast.LENGTH_LONG).show();

                    //    new TwoFragment();

                         sharedPrefs = PreferenceManager.getDefaultSharedPreferences(SearchActivity.this);

                         editor = sharedPrefs.edit();
                         editor.putString("SearchText", ""+searchText.getText().toString().trim());
                         editor.commit();


                     /* TwoFragment TF =  new TwoFragment();
                        TF.show(SearchActivity.this);
                        adapter.addFragment(new TwoFragment(), "Sub_Cat");
*/
                        break;
                    case 2:
                        Toast.makeText(SearchActivity.this,"3",Toast.LENGTH_LONG).show();

                        break;
                    case 3:

                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "Products");
        adapter.addFragment(new TwoFragment(), "Sub_Cat");
        adapter.addFragment(new ThreeFragment(), "Events");
        adapter.addFragment(new ShopSearchFragment(), "Shops");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
