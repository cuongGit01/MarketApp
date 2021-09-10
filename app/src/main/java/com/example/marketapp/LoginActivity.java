package com.example.marketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marketapp.Server.Constants;
import com.example.marketapp.Server.RequestInterface;
import com.example.marketapp.Server.ServerRequest;
import com.example.marketapp.Server.ServerResponse;
import com.example.marketapp.models.User;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    TextView tvSignup;
    ProgressBar progressBar;
    private Button btnLogin;
    private EditText edPhone, edPassword;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        addClicks();

    }


    private void initViews() {
        tvSignup = (TextView) findViewById(R.id.tv_signup);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        btnLogin = findViewById(R.id.btn_login);
        edPhone = findViewById(R.id.et_name);
        edPassword = findViewById(R.id.et_pass);
        pref = getPreferences(0);
    }

    private void addClicks() {
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(new Intent(LoginActivity.this, RegistrationActivity.class));
                startActivity(i);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edPhone.getText().toString().isEmpty() || edPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng không để trống các trường", Toast.LENGTH_SHORT).show();
                }else{
                    loginServer();
                }
            }
        });
    }


    private void loginServer() {
        //ket noi toi api
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface =
                retrofit.create(RequestInterface.class);
        //chinh sua user
        User user = new User();
        user.setTaiKhoan(edPhone.getText().toString());
        user.setPassword(edPassword.getText().toString());
        //thay doi data trong serverrequest
        ServerRequest request = new ServerRequest();
        request.setUser(user);
        //gui va nhan du lieu
        Call<ServerResponse> response = requestInterface.loginInterface(request);
        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call,
                                   Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                // Snackbar.make(LoginActivity.this,resp.getMessage(),Snackbar.LENGTH_LONG).show();
                if (resp.getResult().equals(Constants.SUCCESS)) {
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(Constants.IS_LOGGED_IN, true);

                    editor.putString(Constants.TAI_KHOAN, resp.getUser().getTaiKhoan());
                    editor.putString(Constants.TEN, resp.getUser().getTenNguoiDung());

                    editor.apply();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG, "failed");

                // Snackbar.make(,t.getMessage(),Snackbar.LENGTH_LONG).show();
            }
        });
    }
}