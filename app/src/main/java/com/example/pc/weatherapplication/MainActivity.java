package com.example.pc.weatherapplication;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.pc.weatherapplication.fragments.DailyFragment;
import com.example.pc.weatherapplication.fragments.NowFragment;
import com.example.pc.weatherapplication.fragments.TomorrowFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentActivityInterface {

    private DrawerLayout mDrawerLayout;
    private ViewPager viewPager;
    private boolean snackbarisseen;

    Fragment dailyFragment = new DailyFragment();
    Fragment tomorrowFragment = new TomorrowFragment();
    Fragment nowFragment = new NowFragment();
    ActivityFragmentInterface nowFragmentCast = (ActivityFragmentInterface) nowFragment;
    ActivityFragmentInterface tomorrowFragmentCast = (ActivityFragmentInterface) tomorrowFragment;
    ActivityFragmentInterface dailyFragmentCast = (ActivityFragmentInterface) dailyFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);

        final List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(nowFragment);
        fragmentList.add(tomorrowFragment);
        fragmentList.add(dailyFragment);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Today"));
        tabLayout.addTab(tabLayout.newTab().setText("Tomorrow"));
        tabLayout.addTab(tabLayout.newTab().setText("Next 10 days"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (!snackbarisseen && ! isOnline(MainActivity.this)) {
                    showofflinesnackbar();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, myToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        if (! isOnline(MainActivity.this)) {
            showofflinesnackbar();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    protected void onResume() {
        super.onResume();
        String city = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_city", "riga");
        city = Character.toUpperCase(city.charAt(0)) + city.substring(1);
        setTitle(city);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        FragmentInterface fragmentToUse = null;

        if (item.getItemId() == R.id.nav_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
        }


        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }



    public void showofflinesnackbar() {
        snackbarisseen = true;
        Snackbar.make(mDrawerLayout, "You are currently offline!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Go online", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.v("VVV", " clicked");
                        nowFragmentCast.reloadData();
                        tomorrowFragmentCast.reloadData();
                        dailyFragmentCast.reloadData();
                        snackbarisseen = false;
                    }
                })
                .show();
    }


}
