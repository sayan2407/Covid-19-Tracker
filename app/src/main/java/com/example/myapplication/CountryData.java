package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class CountryData extends AppCompatActivity {
    Toolbar toolbar;
    CircleImageView flags;
    TextView cname,totalcase,totaldeath,totalactive,totalrecovery,acase,scond,mcond,close,recovered,dead,todaycase,todaydeath,todayrecovery;
    String flagImage,countryname,population,cases,deaths,activecase,recovery,criticalcase,mildcondition,closecases,tcase,tdeath,trecover;
    float c,d,a,r;
    TextView popu;
    PieChartView pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_data);
        setuptoolbar();
        cname=toolbar.findViewById(R.id.country);
        pieChart=findViewById(R.id.piechart);
        flags=toolbar.findViewById(R.id.flagImage);
        popu=findViewById(R.id.population);
        totalcase=findViewById(R.id.cases);
        totaldeath=findViewById(R.id.death);
        totalrecovery=findViewById(R.id.recover);
        totalactive=findViewById(R.id.active);
        acase=findViewById(R.id.acase);
        scond=findViewById(R.id.scond);
        mcond=findViewById(R.id.mcond);
        close=findViewById(R.id.closecase);
        recovered=findViewById(R.id.recovered);
        dead=findViewById(R.id.dead);
        todaycase=findViewById(R.id.todaycase);
        todaydeath=findViewById(R.id.todaydeath);
        todayrecovery=findViewById(R.id.todayrecover);
        Intent i=getIntent();
        Bundle b=i.getExtras();
        flagImage=b.getString("flags");
        countryname=b.getString("countryname");
        population=b.getString("populations");
        cases=b.getString("totalcase");
        deaths=b.getString("totaldeaths");
        recovery=b.getString("totalrecovery");
        activecase=b.getString("activecase");
        criticalcase=b.getString("criticalcase");
        tcase=b.getString("todaycase");
        tdeath=b.getString("todaydeaths");
        trecover=b.getString("todayrecovery");

        c=Float.parseFloat(cases);
        d=Float.parseFloat(deaths);
        a=Float.parseFloat(activecase);
        r=Float.parseFloat(recovery);
        final List<SliceValue> pieData = new ArrayList<>();

        pieData.add(new SliceValue(c, Color.RED));
        pieData.add(new SliceValue(r, Color.GREEN));
        pieData.add(new SliceValue(d, Color.GRAY));
        pieData.add(new SliceValue(a, Color.BLUE));
        PieChartData pieChartData = new PieChartData(pieData);
        pieChart.setPieChartData(pieChartData);

        cname.setText(countryname);
        Glide.with(getApplicationContext()).load(flagImage).into(flags);
        popu.setText("Population of "+countryname+" is : "+population);

        totalcase.setText(cases);
        totaldeath.setText(deaths);
        totalrecovery.setText(recovery);
        totalactive.setText(activecase);
        acase.setText(activecase);
        scond.setText(criticalcase);
        mildcondition=Integer.toString(Integer.parseInt(activecase)-Integer.parseInt(criticalcase));
        mcond.setText(mildcondition);
        closecases=Integer.toString(Integer.parseInt(recovery)+Integer.parseInt(deaths));
        close.setText(closecases);
        recovered.setText(recovery);
        dead.setText(deaths);
        todaycase.setText(tcase);
        todaydeath.setText(tdeath);
        todayrecovery.setText(trecover);

    }
    public void setuptoolbar()
    {
        toolbar=findViewById(R.id.datatoolbar);
       // setSupportActionBar(toolbar);
    }

}