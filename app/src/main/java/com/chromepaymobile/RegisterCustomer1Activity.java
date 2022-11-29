package com.chromepaymobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chromepaymobile.R;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterCustomer1Activity extends AppCompatActivity {

    EditText editText1,editText2,editText3,etName,etMobile,etEmail,etNationality,etProfession,etNickName,etPhone;
    MaterialButton next_btn_frc1;
    RadioGroup radioGroup;
    RadioButton male,female;
    CircleImageView uploadImg;
    ImageView backImage;
    String str;
    static Bitmap bitmap ;
    int year;
    int month;
    int day;

        private final int GALLERY_REQ_CODE = 1000;
    final Calendar calendar = Calendar.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer1);

        editText1 = findViewById(R.id.date_et);
        editText2 = findViewById(R.id.month_et);
        editText3 = findViewById(R.id.year_et);

        etName = findViewById(R.id.et_name_fcr1);
        etMobile = findViewById(R.id.et_mobile_fcr1);
        etEmail = findViewById(R.id.et_email_fcr1);
        etNationality = findViewById(R.id.et_nationality_fcr1);
        etProfession = findViewById(R.id.et_profession_fcr1);
        etNickName = findViewById(R.id.et_nick_name_fcr1);
        etPhone = findViewById(R.id.et_phone_no_fcr1);
        next_btn_frc1=findViewById(R.id.next_btn_frc1);
        radioGroup = findViewById(R.id.radio);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        uploadImg = findViewById(R.id.profile_img_frc1);
        backImage = findViewById(R.id.back_img_frc1);


        editText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterCustomer1Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        editText1.setText(String.valueOf(i2));
                        editText2.setText(String.valueOf(i1+1));
                        editText3.setText(String.valueOf(i));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Bitmap.CompressFormat.JPEG.toString();
                startActivityForResult(i,GALLERY_REQ_CODE);
            }
        });

        DOBMove();
        DOBRemove();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (male.isChecked()){
                    str = "Male";
                }
                if (female.isChecked()){
                    str = "Female";
                }
                System.out.println(str);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){

            if (requestCode == GALLERY_REQ_CODE && data != null && data.getData() != null){

                uploadImg.setImageURI(data.getData());
                Uri uri = data.getData();
                System.out.println("URI "+uri);

                try {

                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    Bitmap lastBitmap = null;
                    lastBitmap = bitmap;
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    lastBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    //encoding image to string
                    String image = getStringImage(lastBitmap);
                    System.out.println("byteArray"+byteArray);
                    System.out.println("lastBitmap"+lastBitmap);

                        next_btn_frc1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(RegisterCustomer1Activity.this, RegisterCustomer2Activity.class);
//                                Intent intent = new Intent(RegisterCustomer1Activity.this, AddressActivity.class);
                                intent.putExtra("name", etName.getText().toString().trim());
                                intent.putExtra("image",byteArray);
                                intent.putExtra("DOB", editText1.getText().toString() + editText2.getText().toString() + editText3.getText().toString());
                                intent.putExtra("mobile", etMobile.getText().toString());
                                intent.putExtra("email", etEmail.getText().toString());
                                intent.putExtra("gender", str);
                                intent.putExtra("nationality", etNationality.getText().toString());
                                intent.putExtra("profession", etProfession.getText().toString());
                                intent.putExtra("nickname", etNickName.getText().toString());
                                intent.putExtra("phone", etPhone.getText().toString());
                                startActivity(intent);
                            }
                        });

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }

    public void DOBMove(){

        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length()==2){
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
                if (charSequence.toString().length()==2){

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void DOBRemove(){

        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().isEmpty()){
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

                if (charSequence.toString().isEmpty()){
                    editText1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


}
