package com.chromepaymobile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chromepaymobile.Api.Network.AllUrl;
import com.chromepaymobile.R;
import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity {

    ImageView back,imgToggle;
    MaterialButton login;
    EditText etEmail,etPass;
    TextView forgotPassWord;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        back = findViewById(R.id.back_img);
        login = findViewById(R.id.login);
        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_pass);
        imgToggle = findViewById(R.id.img_toggle);
        forgotPassWord = findViewById(R.id.forgot_pass_tv);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        forgotPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this,VerifyEmailActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Login_Api();
           /*     String emailToText = etEmail.getText().toString();
                String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


                if (!emailToText.matches(emailPattern)){

                    Toast.makeText(LoginActivity.this, "Please Enter valid Email address !", Toast.LENGTH_SHORT).show();
                }
                else if (etPass.getText().toString().trim().isEmpty()){

                    Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();

                }*/
            }
        });

        imgToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etPass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){

                    imgToggle.setImageResource(R.drawable.invisible);
                    etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPass.setSelection(etPass.length());
                }
                else {

                    imgToggle.setImageResource(R.drawable.login_stuff_06);
                    etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPass.setSelection(etPass.length());
                }
            }
        });
    }


    public void Login_Api(){

        try {

            RequestQueue queue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", etEmail.getText().toString());
            jsonBody.put("password", etPass.getText().toString());
            final String mRequestBody = jsonBody.toString();

            StringRequest sr = new StringRequest(Request.Method.POST, AllUrl.Login, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOGIN_VOLLEY", response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        System.out.println("loginApi  responce  >>>>>>>>   "+response);

                        boolean status = jsonObject.getBoolean("status");
                        String msg = jsonObject.getString("msg");


                        if (status == true){

                            String token = jsonObject.getString("token");
                            String id = jsonObject.getString("ID");
                            String orgId = jsonObject.getString("orgID");

                            String br = getDecodedJwt(token);
                            System.out.println(msg);
                            System.out.println(token);
                            System.out.println(br);
                            System.out.println(id);
                            System.out.println(orgId);

                            SharedPreferences sharedPreferences = getSharedPreferences("LoginPreferences",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("ID",id);
                            editor.putString("orgID",orgId);
                            editor.putString("token",token);
                            editor.putBoolean("isLogin",true);
                            editor.commit();

                            Toast.makeText(LoginActivity.this, ""+msg, Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(LoginActivity.this, AgentDashActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("LOGIN_VOLLEY_ERROR", error.toString());
                            Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_LONG).show();

                        }
                    })
            {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    int mStatusCode = response.statusCode;
                    return super.parseNetworkResponse(response);
                }
            };

            queue.add(sr);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getDecodedJwt(String jwt){

        StringBuilder result = new StringBuilder();
        String[] parts = jwt.split("[.]");
        try{

            int index = 0;
            for(String part: parts){

                if (index >= 2)
                    break;
                index++;
                byte[] decodedBytes = Base64.decode(part.getBytes("UTF-8"), Base64.URL_SAFE);
                result.append(new String(decodedBytes, "UTF-8"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}