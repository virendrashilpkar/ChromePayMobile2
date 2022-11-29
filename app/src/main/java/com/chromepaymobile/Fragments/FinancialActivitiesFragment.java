package com.chromepaymobile.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chromepaymobile.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.lang.reflect.Array;
import java.util.ArrayList;

public class FinancialActivitiesFragment extends Fragment {

    PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView =  inflater.inflate(R.layout.fragment_financial_activities, container, false);

        pieChart = mView.findViewById(R.id.pie_chart);

        ArrayList<PieEntry> records = new ArrayList<>();

        records.add(new PieEntry(15,"Loans Paid"));
        records.add(new PieEntry(25,"Income"));
        records.add(new PieEntry(35,"Bill"));
        records.add(new PieEntry(45,"Transaction"));

        PieDataSet dataSet = new PieDataSet(records,"Pie Chart Report");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(22f);

        PieData pieData = new PieData(dataSet);

        pieChart.setData(pieData);
        /*pieChart.getDescription().setEnabled(true);*/
        pieChart.setCenterText("Financial Activities");
        pieChart.setCenterTextSize(20);

//        pieChart.animate();
        return mView;
    }
}