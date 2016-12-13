package com.example.pc.weatherapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.pc.weatherapplication.Daily.DailyFragment;
import com.example.pc.weatherapplication.Settings.SettingsFragment;
import com.example.pc.weatherapplication.WeatherList.WeatherFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment = new WeatherFragment();

        getFragmentManager().beginTransaction().replace(R.id.fragment_conrainer, fragment).commit();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, myToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FragmentInterface fragmentToUse= null;

        if (item.getItemId() == R.id.nav_current_weather) {
            fragmentToUse = new WeatherFragment();
        }

        else if (item.getItemId() == R.id.nav_settings) {
            fragmentToUse = new SettingsFragment();
        }

        else if (item.getItemId() == R.id.nav_test) {
            fragmentToUse = new DailyFragment();
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
        if (currentFragment instanceof WeatherFragment) {
            this.finish();
        } else {
            super.onBackPressed();
        }
    }
}
