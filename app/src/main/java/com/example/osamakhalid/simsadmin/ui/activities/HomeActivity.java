package com.example.osamakhalid.simsadmin.ui.activities;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.osamakhalid.simsadmin.R;
import com.example.osamakhalid.simsadmin.db.StudentDbHelper;
import com.example.osamakhalid.simsadmin.ui.fragments.AttendanceSmsFragment;
import com.example.osamakhalid.simsadmin.ui.fragments.FeesSmsFragment;
import com.example.osamakhalid.simsadmin.ui.fragments.GeneralSmsFragment;
import com.example.osamakhalid.simsadmin.ui.fragments.ImportDataFragment;
import com.example.osamakhalid.simsadmin.ui.fragments.ResultSmsFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        , GeneralSmsFragment.OnFragmentInteractionListener
        , AttendanceSmsFragment.OnFragmentInteractionListener
        , ResultSmsFragment.OnFragmentInteractionListener
        , FeesSmsFragment.OnFragmentInteractionListener
        , ImportDataFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_fragment, new GeneralSmsFragment())
                .commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.general_sms) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_fragment, new GeneralSmsFragment())
                    .commit();

        } else if (id == R.id.attendance_sms) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_fragment, new AttendanceSmsFragment())
                    .commit();

        } else if (id == R.id.result_sms) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_fragment, new ResultSmsFragment())
                    .commit();

        } else if (id == R.id.fees_sms) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_fragment, new FeesSmsFragment())
                    .commit();
        } else if (id == R.id.import_data) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_fragment, new ImportDataFragment())
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFeesFragmentInteraction(Uri uri) {

    }

    @Override
    public void onResultFragmentInteraction(Uri uri) {

    }

    @Override
    public void onAttendanceFragmentInteraction(Uri uri) {

    }

    @Override
    public void onGeneralFragmentInteraction(Uri uri) {

    }

    @Override
    public void onImportFragmentInteraction(Uri uri) {

    }
}
