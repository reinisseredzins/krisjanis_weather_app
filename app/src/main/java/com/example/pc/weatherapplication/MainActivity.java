package com.example.pc.weatherapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.pc.weatherapplication.fragments.DailyFragment;
import com.example.pc.weatherapplication.fragments.NowFragment;
import com.example.pc.weatherapplication.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener, FragmentActivityInterface {

    private DrawerLayout mDrawerLayout;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ActivityFragmentInterface mWeatherFragment;
    private ActivityFragmentInterface mDailyFragment;
    //private ActivityFragmentInterface mNowFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment dailyFragment = new DailyFragment();
        Fragment nowFragment = new NowFragment();

        //mNowFragment = (ActivityFragmentInterface) nowFragment;
        mDailyFragment = (ActivityFragmentInterface) dailyFragment;
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        getFragmentManager().beginTransaction().replace(R.id.daily_conrainer, dailyFragment).commit();
        getFragmentManager().beginTransaction().replace(R.id.fragment_conrainer, nowFragment).commit();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, myToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        showOrHideProgressBar(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FragmentInterface fragmentToUse = null;

         if (item.getItemId() == R.id.nav_settings) {
            fragmentToUse = new SettingsFragment();
        } else if (item.getItemId() == R.id.nav_test) {
            fragmentToUse = new DailyFragment();
            mDailyFragment = (ActivityFragmentInterface) fragmentToUse;
        }

        getFragmentManager().beginTransaction().replace(R.id.fragment_conrainer, (Fragment) fragmentToUse).addToBackStack(fragmentToUse.getFragmentTag()).commit();

        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        final Fragment currentFragment = getFragmentManager().findFragmentById(R.id.fragment_conrainer);
        if (currentFragment instanceof NowFragment) {
            this.finish();
        } else {
            super.onBackPressed();
        }
    }

    private void showOrHideProgressBar(Boolean isVisible) {
        mProgressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onRefresh() {
        //mWeatherFragment.reloadData();
        mDailyFragment.reloadData();
    }

    @Override
    public void hideSwipeLayout() {
        mSwipeRefreshLayout.setRefreshing(false);
        showOrHideProgressBar(false);
    }
}
