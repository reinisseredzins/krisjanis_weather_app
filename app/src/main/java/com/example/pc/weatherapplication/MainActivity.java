package com.example.pc.weatherapplication;

import android.Manifest;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.pc.weatherapplication.database.CityListDbHelper;
import com.example.pc.weatherapplication.fragments.CityChooserDialogFragment;
import com.example.pc.weatherapplication.fragments.DailyFragment;
import com.example.pc.weatherapplication.fragments.NowFragment;
import com.example.pc.weatherapplication.fragments.TomorrowFragment;
import com.example.pc.weatherapplication.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentActivityInterface, FragmentInterface {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.menu_current_location)
    TextView menuCurrentLocation;
    @BindView(R.id.menu_settings)
    TextView menuSettings;
    @BindView(R.id.city_add)
    TextView menuAddCity;

    private boolean isSnackBarShown;
    private DrawerAdapter mDrawerAdapter;

    ActivityFragmentInterface mNowFragmentCast;
    ActivityFragmentInterface mTomorrowFragmentCast;
    ActivityFragmentInterface mDailyFragmentCast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        NowFragment nowFragment = new NowFragment();
        mNowFragmentCast = nowFragment;
        TomorrowFragment tomorrowFragment = new TomorrowFragment();
        mTomorrowFragmentCast = tomorrowFragment;
        DailyFragment dailyFragment = new DailyFragment();
        mDailyFragmentCast = dailyFragment;

        mDrawerAdapter = new DrawerAdapter(this);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.drawer_cities);

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {
            menuCurrentLocation.setVisibility(View.VISIBLE);
        }

        recyclerView.setAdapter(mDrawerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final List citiesList = new ArrayList();
        mDrawerAdapter.setCitySet(citiesList);

        onCityChosen();

        menuCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        menuAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerAdapter.getItemCount() <= Constants.MAX_CITY_LIMIT) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    DialogFragment newFragment = new CityChooserDialogFragment();
                    newFragment.show(ft, "dialog");
                } else {
                    maxCitiesReachedDialog();
                }
            }
        });

        menuSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });

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
                if (!isSnackBarShown && !isOnline(MainActivity.this)) {
                    displayOfflineSnackBar();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, myToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        if (!isOnline(MainActivity.this)) {
            displayOfflineSnackBar();
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


    public void displayOfflineSnackBar() {
        isSnackBarShown = true;
        Snackbar.make(mDrawerLayout, "You are currently offline!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Go online", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mNowFragmentCast.reloadData();
                        mTomorrowFragmentCast.reloadData();
                        mDailyFragmentCast.reloadData();
                        isSnackBarShown = false;
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

    public void maxCitiesReachedDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_warning)
                .setMessage(R.string.max_cities_reached_dialog_text)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                }).show();
    }

    @Override
    public void onCityChosen() {
        if (mDrawerAdapter.getItemCount() <= Constants.MAX_CITY_LIMIT) {
            CityListDbHelper helper = new CityListDbHelper(this);
            final List<CityList> cityLists = helper.searchForFavorites();
            mDrawerAdapter.setCitySet(cityLists);
        } else {
            maxCitiesReachedDialog();
        }
    }

    public void reloadData(String title) {
        mDailyFragmentCast.reloadData();
        mTomorrowFragmentCast.reloadData();
        mNowFragmentCast.reloadData();
        setTitle(title);
        mDrawerLayout.closeDrawers();
    }
}
