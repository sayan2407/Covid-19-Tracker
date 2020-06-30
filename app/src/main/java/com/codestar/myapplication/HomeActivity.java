package com.codestar.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
                 /*   case R.id.share:
                        Intent intent=new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String shareBody="https://github.com/sayan2407/Covid-19-Tracker";
                        String shareSub="Hey!! I am using Covid-19 Tracker.Install this application & get Dailly Covid-19 update";
                        intent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                        intent.putExtra(Intent.EXTRA_TEXT,shareBody);
                        startActivity(Intent.createChooser(intent,"Share Using"));
                        break; */
                    case R.id.about:
                        AlertDialog.Builder builder=new AlertDialog.Builder(HomeActivity.this);
                        builder.setCancelable(false);
                        builder.setTitle("About Covid-19 Tracker");
                        builder.setMessage("Covid-19 Tracker application created by CodeStar.\nUsing this application you can see worldwide covid-19 Cases i.e Total confirmed cases,Total Recovery,Total Deaths,Toal active cases etc.\nYou can see all Affected Countries and their covid-19 cases.\nOur data will update within 10 Minutes.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                        break;
                    case R.id.safety:
                        AlertDialog.Builder builder1=new AlertDialog.Builder(HomeActivity.this);
                        builder1.setCancelable(false);
                        builder1.setTitle("Tips and Safety");
                        builder1.setMessage("Under Development");
                        builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder1.create().show();
                        break;

                }
                return false;
            }
        });



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
                    todaycase.setText(test(Integer.parseInt(response.getString("todayCases"))));
                    todaydeath.setText(test(Integer.parseInt(response.getString("todayDeaths"))));
                    todayrecover.setText(test(Integer.parseInt(response.getString( "todayRecovered"))));
                  track.setText("Track "+response.getString("affectedCountries")+" Affected Countries");


                  tcases.setText(test(Integer.parseInt(cases)));
                  dead.setText(test(Integer.parseInt(death)));
                  recovered.setText(test(Integer.parseInt(recover)));
                  activecase.setText(test(Integer.parseInt(active)));
                  acase.setText(test(Integer.parseInt(active)));
                  mildcondition.setText(test(Integer.parseInt(active) - Integer.parseInt(response.getString("critical"))));
                  scondition.setText(test(Integer.parseInt(response.getString( "critical"))));
                  double b=((Integer.parseInt(response.getString( "critical")) / Integer.parseInt(active))* 100);
                  double b1 =((Integer.parseInt(active) - Integer.parseInt(response.getString("critical"))) / Integer.parseInt(active)) * 100 ;
             //     pcondition.setText(new DecimalFormat("##.##").format(b1)+"%");
             //     spcondition.setText(new DecimalFormat("##.##").format(b)+"%");
                  closecase.setText(test(Integer.parseInt(recover) + Integer.parseInt(death)));
                  rec.setText(test(Integer.parseInt(recover)));
                  died.setText(test(Integer.parseInt(death)));

                  double b2=(Integer.parseInt(recover) /(Integer.parseInt(recover) + Integer.parseInt(death))) * 100;
            //      prec.setText(new DecimalFormat("##.##").format(b2)+"%");
                  double b4=(Integer.parseInt(death) / (Integer.parseInt(recover) + Integer.parseInt(death))) *100 ;
            //      pdied.setText(new DecimalFormat("##.##").format(b4)+"%");

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

  /*      RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, "https://corona-virus-stats.herokuapp.com/api/v1/cases/general-stats", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data=response.getJSONObject("data");
                  //  tcases.setText(data.getString("total_cases"));
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
        queue.add(objectRequest); */



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
    public  String test(int n)
    {
        String numberAsString = String.format("%,d", n);
        return numberAsString;
    }

    public void track_country(View view) {
       // startActivity(new Intent(getApplicationContext(),TrackCountry.class));

        Intent intent=new Intent(getApplicationContext(),AllCountry.class);
        startActivity(intent);
    }
}