package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllCountry extends AppCompatActivity {
    ArrayList<String> countryname, flagpic, cases, todayCases, deaths, todayDeaths, recover, todayRecover, active, critical, populations, tests;
    ListView listView;
    SimpleArcLoader loader;
    EditText search;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_country);
        listView = findViewById(R.id.listview);
        loader = findViewById(R.id.loader);
        search = findViewById(R.id.search1);
        countryname = new ArrayList<String>();
        flagpic = new ArrayList<String>();
        cases = new ArrayList<String>();
        todayCases = new ArrayList<String>();
        deaths = new ArrayList<String>();
        todayDeaths = new ArrayList<String>();
        recover = new ArrayList<String>();
        todayRecover = new ArrayList<String>();
        active = new ArrayList<String>();
        critical = new ArrayList<String>();
        populations = new ArrayList<String>();
        tests = new ArrayList<String>();

    /*  getSupportActionBar().setTitle("Affected Countries");
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true); */


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://corona.lmao.ninja/v2/countries", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        JSONObject pic = object.getJSONObject("countryInfo");
                        countryname.add(object.getString("country"));
                        cases.add(object.getString("cases"));
                        todayCases.add(object.getString("todayCases"));
                        deaths.add(object.getString("deaths"));
                        todayDeaths.add(object.getString("todayDeaths"));
                        recover.add(object.getString("recovered"));
                        todayRecover.add(object.getString("todayRecovered"));
                        active.add(object.getString("active"));
                        critical.add(object.getString("critical"));
                        populations.add(object.getString("population"));
                        tests.add(object.getString("tests"));
                        flagpic.add(pic.getString("flag"));
                    }
                    //    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,countryname);
                    myAdapter = new MyAdapter(getApplication(), countryname, flagpic, cases);
                    listView.setAdapter(myAdapter);
                    loader.setVisibility(View.INVISIBLE);


                    //       Log.d("country",object.getString("country"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AllCountry.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),countryname.get(position),Toast.LENGTH_LONG).show();
                String country = countryname.get(position);
                String totalCase = cases.get(position);
                String daillyCase = todayCases.get(position);
                String died = deaths.get(position);
                String daillyDied = todayDeaths.get(position);
                String recov = recover.get(position);
                String daillyRecov = todayRecover.get(position);
                String activeCase = active.get(position);
                String criticalCase = critical.get(position);
                String poulation = populations.get(position);
                String test = tests.get(position);
                String flagImage = flagpic.get(position);

                Bundle bundle = new Bundle();
                bundle.putString("countryname", country);
                bundle.putString("totalcase", totalCase);
                bundle.putString("todaycase", daillyCase);
                bundle.putString("totaldeaths", died);
                bundle.putString("todaydeaths", daillyDied);
                bundle.putString("totalrecovery", recov);
                bundle.putString("todayrecovery", daillyRecov);
                bundle.putString("activecase", activeCase);
                bundle.putString("criticalcase", criticalCase);
                bundle.putString("populations", poulation);
                bundle.putString("tests", test);
                bundle.putString("flags", flagImage);

                Intent intent = new Intent(getApplicationContext(), CountryData.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               myAdapter.getFilter().filter(s);
                myAdapter.notifyDataSetChanged();



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> countryname, countryflag, cases;
    //    ArrayList<String> searchtext;

        MyAdapter(Context c, ArrayList<String> countryname, ArrayList<String> countryflag, ArrayList<String> cases) {
            super(c, R.layout.listview_design, R.id.countryname, countryname);
            this.context = c;
            this.countryname = countryname;
            this.countryflag = countryflag;
            this.cases = cases;
   //         this.searchtext = countryname;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.listview_design, parent, false);
            ImageView imgs = row.findViewById(R.id.flagpic);
            TextView name = row.findViewById(R.id.countryname);
            TextView confirm = row.findViewById(R.id.cases);

            name.setText(countryname.get(position));
            confirm.setText("Total Confirmed cases : " + cases.get(position));
            Glide.with(context).load(countryflag.get(position)).into(imgs);

            return row;
        }

    }
}