package com.example.osamakhalid.simsadmin.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.osamakhalid.simsadmin.baseconnection.RetrofitInitialize;
import com.example.osamakhalid.simsadmin.connectioninterface.ClientAPIs;
import com.example.osamakhalid.simsadmin.consts.Values;
import com.example.osamakhalid.simsadmin.globalcalls.CommonCalls;
import com.example.osamakhalid.simsadmin.R;
import com.example.osamakhalid.simsadmin.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText userName, password;
    private ProgressDialog progressDialog;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = findViewById(R.id.login_button);
        userName = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Login");
        setSupportActionBar(toolbar);
        if (checkUserAlreadyLoggedIn()) {
            Intent i = new Intent(MainActivity.this, com.example.osamakhalid.simsadmin.ui.activities.HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = CommonCalls.createDialouge(MainActivity.this, "", Values.WAIT_MSG);
                loginAdmin();
            }
        });
    }

    public void loginAdmin() {
        Retrofit retrofit = RetrofitInitialize.getApiClient();
        ClientAPIs clientAPIs = retrofit.create(ClientAPIs.class);
        String base = Values.USER_CURL + ":" + Values.PASSWORD_CURL;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<LoginResponse> call = clientAPIs.loginUser(userName.getText().toString(), password.getText().toString(), authHeader);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.getStatus() == 1) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Successfully Logged In..", Toast.LENGTH_SHORT).show();
                        CommonCalls.saveUserData(loginResponse, MainActivity.this);
                        Intent i = new Intent(MainActivity.this, com.example.osamakhalid.simsadmin.ui.activities.HomeActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, Values.INVALID_USER_PASS, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, Values.SERVER_ERROR, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean checkUserAlreadyLoggedIn() {
        if (CommonCalls.getUserData(MainActivity.this) != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }
}

