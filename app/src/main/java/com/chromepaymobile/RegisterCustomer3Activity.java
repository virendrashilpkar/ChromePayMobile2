package com.chromepaymobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chromepaymobile.Api.Network.AllUrl;
import com.chromepaymobile.Api.Network.VolleyMultipartRequest;
import com.google.android.material.button.MaterialButton;
import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;
import com.scanlibrary.Utils;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class RegisterCustomer3Activity extends AppCompatActivity {

    MaterialButton submitBtnFrc3;
    String mobile, assetType, assetId;
    Uri residence,document,registration;
    Bitmap bmp,proofResidenceImage,localGovDocumentImage,landRegistrationImage,residenceBmp,documentBmp,registrationBmp;
    LinearLayout proofResidence,logDocument,landRegistration;
    private final int GALLERY_REQ_CODE_RESIDENCE = 1100,GALLERY_REQ_CODE_DOCUMENT = 1200,GALLERY_REQ_CODE_REGISTRATION = 1300;
    byte[] byteArray,residenceArray,documentArray,registrationArray;
    EditText landSize;
    ImageView proof,local,land,backImage;
    int REQUEST_CODE = 99;
    Spinner asset_type, asset_id;

    String[] selectAssetType = {"Asset","Land","House","Car","Store"};
    String[] selectAssetId = {"Gender","Land","House","Car","Store"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer3);
        submitBtnFrc3 = findViewById(R.id.submit_btn_frc3);
        landSize = findViewById(R.id.et_land_size);
        proofResidence = findViewById(R.id.proof_residence_l);
        logDocument = findViewById(R.id.local_gov_doc_l);
        landRegistration = findViewById(R.id.land_registration_l);
        backImage = findViewById(R.id.back_img_frc3);
        proof = findViewById(R.id.proof_residence_image);
        local = findViewById(R.id.local_gov_doc_image);
        land = findViewById(R.id.land_registration_image);
        asset_id = findViewById(R.id.sp_asset_id);
        asset_type = findViewById(R.id.sp_asset_type);

        mobile = getIntent().getStringExtra("mobile");

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ArrayAdapter AssetTypeList = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
                selectAssetType);

        AssetTypeList.setDropDownViewResource(android.R.layout.simple_spinner_item);
        asset_type.setAdapter(AssetTypeList);

        asset_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        proofResidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              /*  int preference = ScanConstants.OPEN_MEDIA;
                Intent intent = new Intent(RegisterCustomer3Activity.this, ScanActivity.class);
                intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
                startActivityForResult(intent, REQUEST_CODE);*/

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Bitmap.CompressFormat.JPEG.toString();
                startActivityForResult(intent,GALLERY_REQ_CODE_RESIDENCE);
            }
        });

        logDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Bitmap.CompressFormat.JPEG.toString();
                startActivityForResult(intent,GALLERY_REQ_CODE_DOCUMENT);
            }
        });

        landRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Bitmap.CompressFormat.JPEG.toString();
                startActivityForResult(intent,GALLERY_REQ_CODE_REGISTRATION);
            }
        });

        submitBtnFrc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UpdateProductApi();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data.getData()!=null)    {

/*
            if (requestCode == REQUEST_CODE && data != null){

                residence = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);;
                proof.setImageBitmap(residenceBmp);
                Bitmap lastBitmap = null;

                try {
                    residenceBmp = MediaStore.Images.Media.getBitmap(getContentResolver(),residence);

                    getContentResolver().delete(residence, null, null);
                    System.out.println("hjsdkahf; "+Utils.getBitmap(RegisterCustomer3Activity.this, residence));
                    proof.setImageURI(Utils.getUri(RegisterCustomer3Activity.this,residenceBmp));
                    lastBitmap = residenceBmp;
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    lastBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                    residenceArray = stream.toByteArray();
                    proofResidenceImage = BitmapFactory.decodeByteArray(residenceArray, 0, residenceArray.length);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
*/
            if (requestCode == GALLERY_REQ_CODE_RESIDENCE && data != null && data.getData() != null) {

                residence = data.getData();
                proof.setImageURI(data.getData());
                try {
                    residenceBmp = MediaStore.Images.Media.getBitmap(getContentResolver(), residence);
                    Bitmap lastBitmap = null;
                    lastBitmap = residenceBmp;
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    lastBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    residenceArray = stream.toByteArray();
                    proofResidenceImage = BitmapFactory.decodeByteArray(residenceArray, 0, residenceArray.length);

                    String image = getStringImage(lastBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        if (resultCode == RESULT_OK && data.getData()!=null) {

            if (requestCode == GALLERY_REQ_CODE_DOCUMENT && data != null && data.getData() != null) {

                document = data.getData();
                local.setImageURI(data.getData());
                try {
                    documentBmp = MediaStore.Images.Media.getBitmap(getContentResolver(), document);
                    Bitmap lastBitmap = null;
                    lastBitmap = documentBmp;
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    lastBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    documentArray = stream.toByteArray();
                    localGovDocumentImage = BitmapFactory.decodeByteArray(documentArray, 0, documentArray.length);

                    String image = getStringImage(lastBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (resultCode == RESULT_OK && data.getData()!=null) {

            if (requestCode == GALLERY_REQ_CODE_REGISTRATION && data != null && data.getData() != null) {

                registration = data.getData();
                land.setImageURI(data.getData());

                try {
                    registrationBmp = MediaStore.Images.Media.getBitmap(getContentResolver(), registration);
                    Bitmap lastBitmap = null;
                    lastBitmap = registrationBmp;
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    lastBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    registrationArray = stream.toByteArray();
                    landRegistrationImage = BitmapFactory.decodeByteArray(registrationArray, 0, registrationArray.length);

                    String image = getStringImage(lastBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        }

    public void Dialog() {

        final Dialog dialog = new Dialog(RegisterCustomer3Activity.this, android.R.style.Theme_Translucent_NoTitleBar);
        View mView = getLayoutInflater().inflate(R.layout.verification_dialodg_layout, null);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView dis = (ImageView) mView.findViewById(R.id.dis_dialog_loan);
        MaterialButton verify = (MaterialButton) mView.findViewById(R.id.verify_did_btn);
        EditText editText1 = (EditText) mView.findViewById(R.id.editText1);
        EditText editText2 = (EditText) mView.findViewById(R.id.editText2);
        EditText editText3 = (EditText) mView.findViewById(R.id.editText3);
        EditText editText4 = (EditText) mView.findViewById(R.id.editText4);
        EditText editText5 = (EditText) mView.findViewById(R.id.editText5);
        EditText editText6 = (EditText) mView.findViewById(R.id.editText6);
        dialog.setContentView(mView);

        {
            editText1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().isEmpty()) {
                        editText2.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            editText2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().isEmpty()) {
                        editText3.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            editText3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().isEmpty()) {
                        editText4.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            editText4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().isEmpty()) {
                        editText5.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            editText5.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().isEmpty()) {
                        editText6.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }

        {
            editText2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().isEmpty()) {
                        editText1.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            editText3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().isEmpty()) {
                        editText2.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            editText4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().isEmpty()) {
                        editText3.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            editText5.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().isEmpty()) {
                        editText4.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            editText6.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().isEmpty()) {
                        editText5.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }

        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    try {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        String otp = editText1.getText().toString()+editText2.getText().toString()+editText3.getText().toString()+editText4.getText().toString()+editText5.getText().toString()+editText6.getText().toString();

//                        JSONObject jsonBody = new JSONObject();
//                        jsonBody.put("OTP",otp);
//                        jsonBody.put("phoneNo",mobile);
//
//                        System.out.println("jsonBody"+jsonBody);

//                        final String mRequestBody = jsonBody.toString();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.VerifyCustomerAgent, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.e("TAG", "onResponse: "+response );
                                try {

                                    JSONObject jsonObject = new JSONObject(response);

                                    boolean status = jsonObject.getBoolean("status");
                                    String msg = jsonObject.getString("msg");

                                    if (status == true){
                                        Toast.makeText(RegisterCustomer3Activity.this, ""+msg, Toast.LENGTH_SHORT).show();
                                        JSONObject jsonObject1 = jsonObject.getJSONObject("create");
                                        System.out.println("zcxvxbxvngngfn<><><><."+jsonObject1);

                                        final Dialog dialog1 = new Dialog(RegisterCustomer3Activity.this, android.R.style.Theme_Translucent_NoTitleBar);
                                        View mView = getLayoutInflater().inflate(R.layout.did_success_dialog_layout, null);


                                        MaterialButton ok = (MaterialButton) mView.findViewById(R.id.ok_btn);
                                        dialog1.setContentView(mView);

                                        // Verify OTP Api////////////

                                        ok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(RegisterCustomer3Activity.this, CustomerDashBoardActivity.class);
                                                startActivity(intent);

                                            }
                                        });
                                        dialog1.show();
                                    }
                                    Toast.makeText(RegisterCustomer3Activity.this, "1223"+msg, Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                })
                        {
//                            @Override
//                            public String getBodyContentType() {
//                                return "application/json; charset=utf-8";
//                            }
//
//                            @Override
//                            public byte[] getBody() throws AuthFailureError {
//                                try {
//                                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
//                                } catch (UnsupportedEncodingException uee) {
//                                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
//                                    return null;
//                                }
//                            }
//
//                            @Override
//                            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                                int mStatusCode = response.statusCode;
//                                return super.parseNetworkResponse(response);
//                            }
//                            {
                                @Override
                                protected Map<String,String> getParams(){
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("OTP",otp);
                                params.put("phoneNo",mobile);
                                    Log.e("TAG", "getParams: "+params.toString() );
                                return params;
                            }
                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("Content-Type","application/x-www-form-urlencoded");
                                return params;
                            }
                        };

                        requestQueue.add(stringRequest);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        dialog.show();

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

    public void UpdateProductApi(){

        SharedPreferences sharedPreferences = getSharedPreferences("LoginPreferences",MODE_PRIVATE);
        String agentID = sharedPreferences.getString("ID","");
        String orgId = sharedPreferences.getString("orgID","");

        System.out.println("image3 "+getIntent().getExtras().getByteArray("image"));
        byteArray = getIntent().getExtras().getByteArray("image");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                AllUrl.Register+agentID+"/"+orgId,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        Log.i("OTP_VOLLEY", new String(response.data));

                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data).toString());

                            boolean status = jsonObject.getBoolean("status");
                            String msg = jsonObject.getString("msg");

                            if (jsonObject.has("service")) {
                                String service = jsonObject.getString("service");
                                if (status == false || service.matches("Linked")) {

                                    Toast.makeText(RegisterCustomer3Activity.this, "" + msg, Toast.LENGTH_SHORT).show();
                                    LinkedDialog();

                                }
                            }else {
                                if (status == true) {

                                    JSONObject data = jsonObject.getJSONObject("data");

                                    Dialog();
                                    System.out.println(msg);
                                    Toast.makeText(RegisterCustomer3Activity.this, ""+msg, Toast.LENGTH_SHORT).show();
                                    System.out.println(data);
                                } else {
                                    Toast.makeText(RegisterCustomer3Activity.this, "" + msg, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError", "" + error.getMessage());
                        Toast.makeText(RegisterCustomer3Activity.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("fullname",getIntent().getStringExtra("name"));
                map.put("dateOfBirth",getIntent().getStringExtra("DoB"));
                map.put("phone",mobile);
                map.put("email",getIntent().getStringExtra("email"));
                map.put("gender",getIntent().getStringExtra("gender"));
                map.put("address",getIntent().getStringExtra("address"));
                map.put("nationality",getIntent().getStringExtra("nationality"));
                map.put("professoin",getIntent().getStringExtra("profession"));
                map.put("nextFOKinName",getIntent().getStringExtra("nickname"));
                map.put("nextFOKniPhone",getIntent().getStringExtra("phone"));
                map.put("landSize",landSize.getText().toString().trim());
                System.out.println("UpdateProductApi parameter>>>>>>>> " + map);
                return map;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                long imagename2 = System.currentTimeMillis();
                long imagename3 = System.currentTimeMillis();
                long imagename4 = System.currentTimeMillis();

                params.put("IDphoto", new DataPart(imagename + ".png", getFileDataFromDrawable(bmp)));
                params.put("residance", new DataPart(imagename2 + ".png", getFileDataFromDrawable(proofResidenceImage)));
                params.put("locaDocument", new DataPart(imagename3 + ".png", getFileDataFromDrawable(localGovDocumentImage)));
                params.put("landRegistration", new DataPart(imagename4 + ".png", getFileDataFromDrawable(landRegistrationImage)));

                System.out.println("UpdateProductApi product_image image>>>>1> " + params);
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void LinkedDialog(){

        final Dialog linkDialog = new Dialog(RegisterCustomer3Activity.this, android.R.style.Theme_Translucent_NoTitleBar);
        View linkView = getLayoutInflater().inflate(R.layout.linked_service_dialog, null);

        MaterialButton button = (MaterialButton) linkView.findViewById(R.id.yes_btn_ls);
        TextView textView = (TextView) linkView.findViewById(R.id.cancel__btn);

        linkDialog.setContentView(linkView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterCustomer3Activity.this, LinkedServiceActivity.class);
                startActivity(intent);
            }
        });

        linkDialog.show();
    }
}

