package com.tracker.projectopus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tracker.projectopus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TimelineAdapter timelineAdapter;
    private ArrayList<TrackerTimeline> timelineArrayList;
    private RequestQueue requestQueue;
    private TextView text0,text1,text2,text3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text0 = findViewById(R.id.data0);
        text1 = findViewById(R.id.data1);
        text2 = findViewById(R.id.data2);
        text3 = findViewById(R.id.data3);

        recyclerView = findViewById(R.id.timeline);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        timelineArrayList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        parseJSON();
    }

    private void parseJSON() {
        String url = "https://corona-api.com/countries/PH";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("data");

                            JSONObject latest_data = jsonObject.getJSONObject("latest_data");

                            int deaths = latest_data.getInt("deaths");
                            int confirmed = latest_data.getInt("confirmed");
                            int recovered = latest_data.getInt("recovered");
                            int critical = latest_data.getInt("critical");

                            String latest_deaths = ("Deaths: " + deaths);
                            String latest_confirmed = "Confirmed: " + confirmed;
                            String latest_recovered = "Recovered: " + recovered;
                            String latest_critical = "Critical: " + critical;

                            text0.setText(latest_deaths);
                            text1.setText(latest_confirmed);
                            text2.setText(latest_recovered);
                            text3.setText(latest_critical);

                            JSONArray jsonArray = jsonObject.getJSONArray("timeline");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);

                                int timelineDeaths = data.getInt("deaths");
                                int timelineConfirmed = data.getInt("confirmed");
                                int timelineRecovered = data.getInt("recovered");

                                timelineArrayList.add(new TrackerTimeline(timelineDeaths, timelineConfirmed, timelineRecovered));
                            }
                        timelineAdapter = new TimelineAdapter(MainActivity.this, timelineArrayList);
                            recyclerView.setAdapter(timelineAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }
}
