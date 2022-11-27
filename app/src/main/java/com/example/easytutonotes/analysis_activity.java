package com.example.easytutonotes;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.realm.implementation.RealmBarDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class analysis_activity extends AppCompatActivity {
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analysis_activity);

        barChart = findViewById(R.id.barChart_view);

        showBarChart();

//
//        RealmResults<Note> result = realm.where(Note.class).findAll();
//        result.load();
//
//        for (Note p : result) {
//            items.add(p.getMood());
//        }

    }
    private void showBarChart(){
        Realm realm = Realm.getDefaultInstance();
        ArrayList<String> DateList = new ArrayList<String>();
        ArrayList<String> rateList = new ArrayList<String>();

        RealmResults<Note> result = realm.where(Note.class).findAll();
        result.load();

        for (Note p : result) {
            SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
            String formattedTime =  DateFor.format(p.getCreatedTime());
            DateList.add(formattedTime);
            rateList.add(p.getRate());
        }

        ArrayList<BarEntry> entries = new ArrayList<>();
        String title = "Rate";

//        XAxis xAxis = barChart.getXAxis();
//        xAxis.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return xAxisLabel.get((int) value);
//
//            }
//        });

        //fit the data into a bar
        for (int i = 0; i < DateList.size(); i++) {
            BarEntry barEntry = new BarEntry(i, Integer.parseInt(rateList.get(i)));
            entries.add(barEntry);
        }

        BarDataSet barDataSet = new BarDataSet(entries, title);
        XAxis xAxis = barChart.getXAxis();
        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();
        Legend legend = barChart.getLegend();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.animateY(1000);

        xAxis.setDrawGridLines(false);
        leftAxis.setDrawGridLines(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false);

        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);

        BarData data = new BarData(barDataSet);
        barDataSet.setColor(Color.parseColor("#5e548e"));
        //Setting the size of the form in the legend
        barDataSet.setFormSize(15f);
        //showing the value of the bar, default true if not set
        barDataSet.setDrawValues(true);
        //setting the text size of the value of the bar
        barDataSet.setValueTextSize(10f);

        barChart.setData(data);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(DateList));
        barChart.invalidate();
    }

}