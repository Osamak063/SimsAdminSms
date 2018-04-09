package com.example.osamakhalid.simsadmin.ui.activities;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.osamakhalid.simsadmin.R;
import com.example.osamakhalid.simsadmin.baseconnection.RetrofitInitialize;
import com.example.osamakhalid.simsadmin.connectioninterface.ClientAPIs;
import com.example.osamakhalid.simsadmin.consts.Values;
import com.example.osamakhalid.simsadmin.db.StudentDbHelper;
import com.example.osamakhalid.simsadmin.globalcalls.CommonCalls;
import com.example.osamakhalid.simsadmin.model.Class;
import com.example.osamakhalid.simsadmin.model.ClassesList;
import com.example.osamakhalid.simsadmin.model.LoginResponse;
import com.example.osamakhalid.simsadmin.model.Section;
import com.example.osamakhalid.simsadmin.model.SectionsList;
import com.example.osamakhalid.simsadmin.model.StudentData;
import com.example.osamakhalid.simsadmin.ui.fragments.AttendanceSmsFragment;
import com.example.osamakhalid.simsadmin.ui.fragments.FeesSmsFragment;
import com.example.osamakhalid.simsadmin.ui.fragments.GeneralSmsFragment;
import com.example.osamakhalid.simsadmin.ui.fragments.ImportDataFragment;
import com.example.osamakhalid.simsadmin.ui.fragments.ResultSmsFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        , GeneralSmsFragment.OnFragmentInteractionListener
        , AttendanceSmsFragment.OnFragmentInteractionListener
        , ResultSmsFragment.OnFragmentInteractionListener
        , FeesSmsFragment.OnFragmentInteractionListener
        , ImportDataFragment.OnFragmentInteractionListener {

    LoginResponse userData;
    List<Class> classListItems;
    List<Section> sectionListItems;
    StudentDbHelper DBHelper;
    SQLiteDatabase sqLiteDatabase;
    int apiCallsNum = 0;
    ProgressDialog progressDialog;

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
        userData = CommonCalls.getUserData(HomeActivity.this);
        sectionListItems = new ArrayList<>();
        classListItems = new ArrayList<>();
        progressDialog = CommonCalls.createDialouge(HomeActivity.this,"" ,Values.WAIT_MSG);
        if (sectionListItems != null) {
            if (sectionListItems.size() > 0) {
                sectionListItems.clear();
            }
        }
        getDefaultData();
    }

    public void getDefaultData() {
        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        String base = userData.getUsername() + ":" + userData.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<ClassesList> call = clientAPIs.getClasses(authHeader);
        call.enqueue(new Callback<ClassesList>() {
            @Override
            public void onResponse(Call<ClassesList> call, Response<ClassesList> response) {
                if (response.isSuccessful()) {
                    ClassesList classesList = response.body();
                    if (classesList != null && classesList.getClassesData() != null) {
                        classListItems = classesList.getClassesData();
                        saveClassesListToDatabase();
                        getSectionsList(classListItems);
                    }
                } else {
                    Toast.makeText(HomeActivity.this, Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClassesList> call, Throwable t) {
                Toast.makeText(HomeActivity.this, Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveClassesListToDatabase() {
        DBHelper = new StudentDbHelper(HomeActivity.this);
        sqLiteDatabase = DBHelper.getWritableDatabase();
        for (Class data : classListItems) {
            DBHelper.addInformationToClassTable(data.getClassesID(), data.getClasses(), data.getClassesNumeric()
                    , data.getTeacherID(), sqLiteDatabase);
        }
    }

    public void getSectionsList(List<Class> classes) {
        for (Class classData : classes) {
            System.out.println("logg get sections list in loop");
            getSections(classData.getClassesID());
        }
    }

    public void saveSectionsListToDatabase(List<Section> sectionList) {
        DBHelper = new StudentDbHelper(HomeActivity.this);
        sqLiteDatabase = DBHelper.getWritableDatabase();
        for (Section data : sectionList) {
            DBHelper.addInformationToSectionTable(data.getSectionID(), data.getSection(), data.getCategory()
                    , data.getClassesID(), sqLiteDatabase);
        }
    }

    public void getSections(String classId) {
        final List<Section> sectionList = new ArrayList<>();
        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        String base = userData.getUsername() + ":" + userData.getPassword();
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<SectionsList> call = clientAPIs.getSections(classId, authHeader);
        call.enqueue(new Callback<SectionsList>() {
            @Override
            public void onResponse(Call<SectionsList> call, Response<SectionsList> response) {
                if (response.isSuccessful()) {
                    SectionsList sectionsList = response.body();
                    if (sectionsList != null && sectionsList.getSectionData() != null) {
                        sectionList.addAll(sectionsList.getSectionData());
                        ++apiCallsNum;
                        System.out.println("logg section list size=" + sectionList.size());
                        saveSectionsListToDatabase(sectionList);
                        if (apiCallsNum == classListItems.size()) {
                            progressDialog.dismiss();
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container_fragment, new GeneralSmsFragment())
                                    .commit();
                        }
                    }
                } else {
                    Toast.makeText(HomeActivity.this, Values.DATA_ERROR, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SectionsList> call, Throwable t) {
                Toast.makeText(HomeActivity.this, Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
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
