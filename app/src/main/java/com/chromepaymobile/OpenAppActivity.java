package com.chromepaymobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.chromepaymobile.R;

public class OpenAppActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_app);


        SharedPreferences sharedPreferences = getSharedPreferences("LoginPreferences",MODE_PRIVATE);
        boolean Login = sharedPreferences.getBoolean("isLogin",false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Login){
                    Intent intent = new Intent(OpenAppActivity.this,AgentDashActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(OpenAppActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2000);

    }
}