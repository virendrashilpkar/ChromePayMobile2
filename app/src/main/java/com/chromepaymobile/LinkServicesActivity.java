package com.chromepaymobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chromepaymobile.R;

public class LinkServicesActivity extends AppCompatActivity {

    TextView fuseId;
    ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_services);

        fuseId = findViewById(R.id.fuse_id);
        backImage = findViewById(R.id.back_img_linkServices);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        SharedPreferences sharedPreferences = getSharedPreferences("CustomerPreferences",MODE_PRIVATE);
        String id = sharedPreferences.getString("walletAddress","");

        fuseId.setText(id);
    }
}