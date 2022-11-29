package com.chromepaymobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.chromepaymobile.Adapter.CDBRecycleAdapter;
import com.chromepaymobile.Models.CDBRecycleModel;
import com.chromepaymobile.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class CustomerDashBoardActivity extends AppCompatActivity {

    MaterialButton moreServicesBtn;
    RecyclerView cdbRecycle;
    ImageView backImage;

    ArrayList<CDBRecycleModel> cDBList = new ArrayList<>();
    CDBRecycleAdapter cdbRecycleAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dash_board);

        moreServicesBtn = findViewById(R.id.more_services_btn);
        cdbRecycle = findViewById(R.id.cdb_recycle);
        backImage = findViewById(R.id.back_img_cdb);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        cDBList.add(new CDBRecycleModel("Profile",R.drawable.home_page_icon_08));
        cDBList.add(new CDBRecycleModel("Transfers",R.drawable.home_page_icon_06));
        cDBList.add(new CDBRecycleModel("Microloans",R.drawable.home_page_icon_03));
        cDBList.add(new CDBRecycleModel("Bills",R.drawable.home_page_icon_04));
        cDBList.add(new CDBRecycleModel("Airtime",R.drawable.home_page_icon_05));
        cDBList.add(new CDBRecycleModel("Cash in / Cash out",R.drawable.home_page_icon_06));

        cdbRecycleAdapter = new CDBRecycleAdapter(cDBList,CustomerDashBoardActivity.this);
        cdbRecycle.setAdapter(cdbRecycleAdapter);
        cdbRecycleAdapter.notifyDataSetChanged();

        moreServicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CustomerDashBoardActivity.this,MoreServicesActivity.class);
                startActivity(intent);
            }
        });
    }
}