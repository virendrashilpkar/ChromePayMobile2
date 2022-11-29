package com.chromepaymobile;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chromepaymobile.Api.Network.AllUrl;
import com.chromepaymobile.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AgentPerformenceActivity extends AppCompatActivity {

    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    int dec,jan, feb, mar, apr, may, june, july, aug, sep, oct, nov, december;
    TextView name, userNumber, agEmail, location, transaction;
    ImageView backImage;

    ArrayList barEntriesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_performence);

        barChart = findViewById(R.id.bar_chart);
        name = findViewById(R.id.agent_name_tv);
        agEmail = findViewById(R.id.email_ap);
        location = findViewById(R.id.agent_location_tv);
        userNumber = findViewById(R.id.up_status_text);
        transaction = findViewById(R.id.down_status_text);
        backImage = findViewById(R.id.back_img_ap);


        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        AgentDashApi();

        getDataEntries();


    }


    public void getDataEntries(){

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            SharedPreferences sharedPreferences = getSharedPreferences("LoginPreferences",MODE_PRIVATE);
            String agentId = sharedPreferences.getString("ID","");

            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.AgentPerformanceReport+agentId, new Response.Listener<String>() {
                @SuppressLint("SuspiciousIndentation")
                @Override
                public void onResponse(String response) {
                    Log.i("PERFORMANCE_VOLLEY", response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        JSONObject object = jsonObject.getJSONObject("Data");

                        dec = object.getInt("December-2021");
                        jan = object.getInt("January-2022");
                        feb = object.getInt("February-2022");
                        mar = object.getInt("March-2022");
                        apr = object.getInt("April-2022");
                        may = object.getInt("May-2022");
                        june = object.getInt("June-2022");
                        july = object.getInt("July-2022");
                        aug = object.getInt("August-2022");
                        sep = object.getInt("September-2022");
                        oct = object.getInt("October-2022");
                        nov = object.getInt("November-2022");
                        december = object.getInt("December-2022");


                        ArrayList<String> xAxisLabel = new ArrayList<>();

                        xAxisLabel.add("Dec");
                        xAxisLabel.add("Jan");
                        xAxisLabel.add("Feb");
                        xAxisLabel.add("Mar");
                        xAxisLabel.add("Apr");
                        xAxisLabel.add("May");
                        xAxisLabel.add("June");
                        xAxisLabel.add("July");
                        xAxisLabel.add("Aug");
                        xAxisLabel.add("Sep");
                        xAxisLabel.add("Oct");
                        xAxisLabel.add("Nov");
                        xAxisLabel.add("Dec");

                        XAxis xAxis = barChart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
                        xAxis.setGranularityEnabled(true);

                        barEntriesArrayList.add(new BarEntry(0, dec));
                        barEntriesArrayList.add(new BarEntry(1, jan));
                        barEntriesArrayList.add(new BarEntry(2, feb));
                        barEntriesArrayList.add(new BarEntry(3, mar));
                        barEntriesArrayList.add(new BarEntry(4, apr));
                        barEntriesArrayList.add(new BarEntry(5, may));
                        barEntriesArrayList.add(new BarEntry(6, june));
                        barEntriesArrayList.add(new BarEntry(7, july));
                        barEntriesArrayList.add(new BarEntry(8, aug));
                        barEntriesArrayList.add(new BarEntry(9, sep));
                        barEntriesArrayList.add(new BarEntry(10, oct));
                        barEntriesArrayList.add(new BarEntry(11, nov));
                        barEntriesArrayList.add(new BarEntry(12, december));

                        barDataSet = new BarDataSet(barEntriesArrayList,"");
                        barData = new BarData(barDataSet);

                        barChart.setData(barData);
                        barDataSet.setColors(getResources().getColor(R.color.teal_700));
                        barDataSet.setValueTextColor(Color.BLACK);
                        barDataSet.setValueTextSize(10f);
                        barChart.getDescription().setEnabled(false);

                        barChart.setTouchEnabled(true);
                        barChart.getXAxis().setDrawGridLines(false);


                        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                        barChart.notifyDataSetChanged();
                        barChart.invalidate();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AgentPerformenceActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                        }
                    });

            requestQueue.add(stringRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AgentDashApi(){

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            SharedPreferences sharedPreferences = getSharedPreferences("LoginPreferences",MODE_PRIVATE);
            String agentId = sharedPreferences.getString("ID","");

            StringRequest stringRequest = new StringRequest(Request.Method.GET, AllUrl.AgentDashInfo+agentId, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("AGENT_DASH_VOLLEY", response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        boolean status = jsonObject.getBoolean("status");
                        String NumberOFUser = jsonObject.getString("NumberOFUser");
                        String agentName = jsonObject.getString("agentName");
                        String email = jsonObject.getString("email");
                        String country = jsonObject.getString("country");
                        int mobile = jsonObject.getInt("mobile");
                        String totalTransection = jsonObject.getString("totalTransection");

                        if (status == true) {
                            name.setText(agentName);
                            location.setText(country);
                            agEmail.setText(email);
                            userNumber.setText(NumberOFUser);
                            transaction.setText(totalTransection);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            requestQueue.add(stringRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}