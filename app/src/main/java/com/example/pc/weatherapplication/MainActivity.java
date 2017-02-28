package com.example.pc.weatherapplication;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.weatherapplication.fragments.DailyFragment;
import com.example.pc.weatherapplication.fragments.NowFragment;
import com.example.pc.weatherapplication.fragments.TomorrowFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentActivityInterface {

    private DrawerLayout mDrawerLayout;
    private ViewPager viewPager;
    private boolean snackbarisseen;

    TextView menuCurrentLocation;
    TextView menuDefault;
    TextView menuSettings;
    TextView menuAddCity;

    TextView city1;
    TextView city2;
    TextView city3;
    TextView city4;
    TextView city5;

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

        menuCurrentLocation = (TextView) findViewById(R.id.menu_current_location);
        menuDefault = (TextView) findViewById(R.id.menu_default);
        menuSettings = (TextView) findViewById(R.id.menu_settings);
        menuAddCity = (TextView) findViewById(R.id.city_add);

        city1 = (TextView) findViewById(R.id.city1);
        city2 = (TextView) findViewById(R.id.city2);
        city3 = (TextView) findViewById(R.id.city3);
        city4 = (TextView) findViewById(R.id.city4);
        city5 = (TextView) findViewById(R.id.city5);

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {
            menuCurrentLocation.setVisibility(View.VISIBLE);
            Log.v("FINE", "Location already granted");
        }


        menuCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        menuDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        menuAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i=1;
                Boolean b = false;
                for (i=1; i<=5; i++) {
                    String city = "city" + i;
                    switch (city) {
                        case "city1":
                           if (city1.getVisibility() == View.GONE) {
                               city1.setText("text1");
                               city1.setVisibility(View.VISIBLE);
                               b = true;
                           }
                            break;
                        case "city2":
                            if (city2.getVisibility() == View.GONE) {
                                city2.setText("text2");
                                city2.setVisibility(View.VISIBLE);
                                b = true;
                            }
                            break;
                        case "city3":
                            if (city3.getVisibility() == View.GONE) {
                                city3.setText("text3");
                                city3.setVisibility(View.VISIBLE);
                                b = true;
                            }
                            break;
                        case "city4":
                            if (city4.getVisibility() == View.GONE) {
                                city4.setText("text4");
                                city4.setVisibility(View.VISIBLE);
                                b = true;
                            }
                            break;
                        case "city5":
                            if (city5.getVisibility() == View.GONE) {
                                city5.setText("text5");
                                city5.setVisibility(View.VISIBLE);
                                b = true;
                            }
                            break;
                    }
                    if (b == true) {
                        break;
                    }
                }
                if (b == false) {
                    Toast.makeText(MainActivity.this, "Number of 5 max cities have already been added", Toast.LENGTH_LONG).show();
                }
            }
        });

        city1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                view.setVisibility(View.GONE);
                return false;
            }
        });

        city2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                view.setVisibility(View.GONE);
                return false;
            }
        });

        city3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                view.setVisibility(View.GONE);
                return false;
            }
        });

        city4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                view.setVisibility(View.GONE);
                return false;
            }
        });

        city5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                view.setVisibility(View.GONE);
                return false;
            }
        });


        menuSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });

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
                if (!snackbarisseen && !isOnline(MainActivity.this)) {
                    showofflinesnackbar();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, myToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        if (!isOnline(MainActivity.this)) {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                menuCurrentLocation.setVisibility(View.VISIBLE);
            } else {
                menuCurrentLocation.setVisibility(View.GONE);
            }
        }
    }
}
