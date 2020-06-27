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
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
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
import java.util.List;

public class AllCountry extends AppCompatActivity {
    ArrayList<String> countryname, flagpic, cases, todayCases, deaths, todayDeaths, recover, todayRecover, active, critical, populations, tests;
    ListView listView;
    SimpleArcLoader loader;
    EditText search;
    List<ItemsModel> modelList=new ArrayList<>();
    CustomAdapter customAdapter;

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

                        ItemsModel itemsModel = new ItemsModel(object.getString("country"),pic.getString("flag"),object.getString("cases"),object.getString("todayCases"),object.getString("deaths"),object.getString("todayDeaths"),object.getString("recovered"),object.getString("todayRecovered"),object.getString("active"),object.getString("critical"),object.getString("population"),object.getString("tests"));
                        modelList.add(itemsModel);
                        //String cname, String cflag, String tcase, String todayCases, String deaths, String todayDeaths, String recovered, String todayRecover, String active, String critical, String population, String tests
                    }
                    //    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,countryname);
                  //  myAdapter = new MyAdapter(getApplication(), countryname, flagpic, cases);
                  //  listView.setAdapter(myAdapter);

                    customAdapter=new CustomAdapter(modelList,getApplicationContext());
                    listView.setAdapter(customAdapter);
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
                loader.setVisibility(View.INVISIBLE);
            }
        });
        requestQueue.add(jsonArrayRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),countryname.get(position),Toast.LENGTH_LONG).show();

                String country = modelList.get(position).getCname();
                String totalCase = modelList.get(position).getTcase();
                String daillyCase =modelList.get(position).getTodayCases();
                String died = modelList.get(position).getDeaths();
                String daillyDied = modelList.get(position).getTodayDeaths();
                String recov = modelList.get(position).getRecovered();
                String daillyRecov = modelList.get(position).getTodayRecover();
                String activeCase = modelList.get(position).getActive();
                String criticalCase = modelList.get(position).getCritical();
                String poulation = modelList.get(position).getPopulation();
                String test = modelList.get(position).getTests();
                String flagImage = modelList.get(position).getCflag();

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
                customAdapter.getFilter().filter(s);
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    public class CustomAdapter extends BaseAdapter implements Filterable
    {
        List<ItemsModel> itemsModelList;
        List<ItemsModel> itemsModelListFilter;
        Context context;

        public CustomAdapter(List<ItemsModel> itemsModelList, Context context) {
            this.itemsModelList = itemsModelList;
            this.itemsModelListFilter=itemsModelList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return itemsModelListFilter.size();
        }

        @Override
        public Object getItem(int position) {
            return itemsModelListFilter.get(position);
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=getLayoutInflater().inflate(R.layout.listview_design,null);

            ImageView imgs = view.findViewById(R.id.flagpic);
            TextView name = view.findViewById(R.id.countryname);
            TextView confirm = view.findViewById(R.id.cases);

            name.setText(itemsModelListFilter.get(position).getCname());
            confirm.setText("Total Confirmed Cases : "+itemsModelListFilter.get(position).getTcase());
            Glide.with(context).load(itemsModelListFilter.get(position).getCflag()).into(imgs);
            return view;
        }

        @Override
        public Filter getFilter() {
            Filter filter=new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint==null || constraint.length() ==0)
                    {
                        filterResults.count = itemsModelList.size();
                        filterResults.values=itemsModelList;
                    }
                    else
                    {
                        List<ItemsModel> resultModel = new ArrayList<>();
                        String searchStr = constraint.toString().toLowerCase();
                        for (ItemsModel itemsModel : itemsModelList)
                        {
                            if (itemsModel.getCname().toLowerCase().contains(searchStr))
                            {
                                resultModel.add(itemsModel);
                            }
                            filterResults.count=resultModel.size();
                            filterResults.values=resultModel;
                        }
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    itemsModelListFilter=(List<ItemsModel>)results.values;
               //     itemsModelList=(List<ItemsModel>)results.values;
                    modelList=(List<ItemsModel>)results.values;
                    notifyDataSetChanged();

                }
            };
            return filter;
        }

    }

  /*  class MyAdapter extends ArrayAdapter<String> {
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

            View row = layoutInflater.inflate(R.layout.listview_design,parent,false);
            ImageView imgs = row.findViewById(R.id.flagpic);
            TextView name = row.findViewById(R.id.countryname);
            TextView confirm = row.findViewById(R.id.cases);

            name.setText(countryname.get(position));
            confirm.setText("Total Confirmed cases : " + cases.get(position));
            Glide.with(context).load(countryflag.get(position)).into(imgs);

            return row;
        }

    } */
}