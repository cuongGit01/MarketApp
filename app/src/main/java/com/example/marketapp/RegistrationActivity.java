package com.example.marketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class RegistrationActivity extends AppCompatActivity {

    TextView tvSignin;
    ProgressBar progressBar;
    private EditText edTen,edSDT,edMatKhau,edMatKhauXN;
    private Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();
        addClicks();


    }

    private void initViews() {
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        tvSignin = (TextView) findViewById(R.id.tv_signin);
        edTen =  findViewById(R.id.et_name);
        edSDT =  findViewById(R.id.et_phone);
        edMatKhau =  findViewById(R.id.et_pass);
        edMatKhauXN =  findViewById(R.id.et_chkpass);
        btRegister = findViewById(R.id.btn_register);

    }

    private void addClicks() {
        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edSDT.getText().toString().isEmpty() || edTen.getText().toString().isEmpty()
                        || edMatKhau.getText().toString().isEmpty() || edMatKhauXN.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng không để trống các trường", Toast.LENGTH_SHORT).show();
                }else{
                    if(edSDT.getText().toString().length()==10){
                        if(edMatKhau.getText().toString().length()<6){
                            Toast.makeText(getApplicationContext(), "Mật khẩu từ 6 kí tự trở lên", Toast.LENGTH_SHORT).show();
                        }else{
                            if(edMatKhau.getText().toString().equals(edMatKhauXN.getText().toString())){
                                registerServer();
                            }else{
                                Toast.makeText(getApplicationContext(), "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Vui lòng nhập số điện thoại bằng 10", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    public void registerServer() {
        //ket noi toi api
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface requestInterface =
                retrofit.create(RequestInterface.class);
        //chinh sua user
        User user = new User();
        user.setTaiKhoan(edSDT.getText().toString());
        user.setPassword(edMatKhau.getText().toString());
        user.setTenNguoiDung(edTen.getText().toString());
        //thay doi data trong serverrequest
        ServerRequest request = new ServerRequest();
        request.setUser(user);
        //gui va nhan du lieu
        Call<ServerResponse> response = requestInterface.registerInterface(request);
        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call,
                                   Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                // Snackbar.make(getApplicationContext(),"Đăng kí thành công",Snackbar.LENGTH_LONG).show();
                if (resp.getResult().equals(Constants.SUCCESS)) {
                    Toast.makeText(getApplicationContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
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