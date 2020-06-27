package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class HomeActivity extends AppCompatActivity {
    String cases,death,active,recover;
    float c,d,a,r;
    PieChartView pieChart;
    TextView tcases,dead,recovered,activecase,mildcondition,pcondition,scondition,spcondition,acase,closecase,rec,prec,died,pdied;
    TextView todaycase,todaydeath,todayrecover;
    Button track;
    long back;
    Toast backToast;
    SimpleArcLoader loader;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private AdView mAdView,mAdView1,mAdView2,mAdView3,mAdView4;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setuptoolbar();
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        pieChart=findViewById(R.id.piechart);
        tcases=findViewById(R.id.cases);
        dead=findViewById(R.id.death);
        recovered=findViewById(R.id.recover);
        activecase=findViewById(R.id.active);
        mildcondition=findViewById(R.id.mcond);
        pcondition=findViewById(R.id.pcond);
        scondition=findViewById(R.id.scond);
        spcondition=findViewById(R.id.spcond);
        acase=findViewById(R.id.acase);
        closecase=findViewById(R.id.closecase);
        rec=findViewById(R.id.recovered);
        prec=findViewById(R.id.prec);
        died=findViewById(R.id.dead);
        pdied=findViewById(R.id.pdead);
        todaycase=findViewById(R.id.todaycase);
        todaydeath=findViewById(R.id.todaydeath);
        todayrecover=findViewById(R.id.todayrecover);
        track=findViewById(R.id.track);
        loader=findViewById(R.id.loader);
        navigationView=findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        finish();
                        break;
                    case R.id.country:
                        startActivity(new Intent(getApplicationContext(),AllCountry.class));
                        break;
                    case R.id.share:
                        Intent intent=new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String shareBody="https://github.com/sayan2407/Covid-19-Tracker";
                        String shareSub="Hey!! I am using Covid-19 Tracker.Install this application & get Dailly Covid-19 update";
                        intent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                        intent.putExtra(Intent.EXTRA_TEXT,shareBody);
                        startActivity(Intent.createChooser(intent,"Share Using"));
                        break;
                }
                return false;
            }
        });

        MobileAds.initialize(this,"ca-app-pub-6849909083372425~5754406553");
        mAdView = findViewById(R.id.adView);
        mAdView1 = findViewById(R.id.adView1);
        mAdView2 = findViewById(R.id.adView2);
        mAdView3 = findViewById(R.id.adView3);
        mAdView4 = findViewById(R.id.adView4);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView1.loadAd(adRequest);
        mAdView2.loadAd(adRequest);
        mAdView3.loadAd(adRequest);
        mAdView4.loadAd(adRequest);

        final List<SliceValue> pieData = new ArrayList<>();



        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "https://corona.lmao.ninja/v2/all", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //    JSONObject data=response.getJSONObject("data");
                    cases=response.getString("cases");
                    death=response.getString("deaths");
                    active=response.getString("active");
                    recover=response.getString("recovered");
                    todaycase.setText(response.getString("todayCases"));
                    todaydeath.setText(response.getString("todayDeaths"));
                    todayrecover.setText(response.getString( "todayRecovered"));
                  track.setText("Track "+response.getString("affectedCountries")+" Affected Countries");
                    c=Float.parseFloat(cases);
                    d=Float.parseFloat(death);
                    a=Float.parseFloat(active);
                    r=Float.parseFloat(recover);

                    pieData.add(new SliceValue(c, Color.RED));
                    pieData.add(new SliceValue(r, Color.GREEN));
                    pieData.add(new SliceValue(d, Color.GRAY));
                    pieData.add(new SliceValue(a, Color.BLUE));
                    PieChartData pieChartData = new PieChartData(pieData);
                    pieChart.setPieChartData(pieChartData);
                    loader.setVisibility(View.INVISIBLE);


                  //  Toast.makeText(getApplicationContext(),""+c,Toast.LENGTH_LONG).show();

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("covid19:","Something went wrong");
            }
        });
        requestQueue.add(jsonObjectRequest);

        RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, "https://corona-virus-stats.herokuapp.com/api/v1/cases/general-stats", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data=response.getJSONObject("data");
                    tcases.setText(data.getString("total_cases"));
                    recovered.setText(data.getString("recovery_cases"));
                    dead.setText(data.getString("death_cases"));
                    activecase.setText(data.getString("currently_infected"));
                    acase.setText(data.getString("currently_infected"));
                    mildcondition.setText(data.getString( "mild_condition_active_cases"));
                  pcondition.setText("("+data.getString("active_cases_mild_percentage")+"%)");
                    scondition.setText(data.getString("critical_condition_active_cases"));
                    spcondition.setText("("+data.getString( "active_cases_critical_percentage")+"%)");
                    closecase.setText(data.getString("cases_with_outcome"));
                    rec.setText(data.getString( "recovered_closed_cases"));
                    prec.setText("("+data.getString("closed_cases_recovered_percentage")+"%)");
                    died.setText(data.getString("death_closed_cases"));
                    pdied.setText("("+data.getString( "closed_cases_death_percentage")+"%)");

                }catch (Exception ae){
                    ae.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(objectRequest);



    }
    public void setuptoolbar()
    {
        drawerLayout=findViewById(R.id.drawerlayout);
        toolbar=findViewById(R.id.hometoobar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (back+2000>System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }else {
            backToast=Toast.makeText(getApplicationContext(),"please back again to exit",Toast.LENGTH_SHORT);
            backToast.show();
        }
        back=System.currentTimeMillis();
    }

    public void track_country(View view) {
       // startActivity(new Intent(getApplicationContext(),TrackCountry.class));

        Intent intent=new Intent(getApplicationContext(),AllCountry.class);
        startActivity(intent);
    }
}