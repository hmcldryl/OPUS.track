package com.tracker.projectopus.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tracker.projectopus.R;
import com.tracker.projectopus.TimelineAdapter;
import com.tracker.projectopus.TrackerTimeline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OverviewFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<TrackerTimeline> timelineArrayList;
    private RequestQueue requestQueue;
    private TextView text0, text1, text2, text3;
    private OverviewViewModel overviewViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        overviewViewModel =
                ViewModelProviders.of(this).get(OverviewViewModel.class);
        View root = inflater.inflate(R.layout.fragment_overview, container, false);

        text0 = root.findViewById(R.id.data0);
        text1 = root.findViewById(R.id.data1);
        text2 = root.findViewById(R.id.data2);
        text3 = root.findViewById(R.id.data3);

        requestQueue = Volley.newRequestQueue(getActivity());
        parseJSON();

        return root;
    }
    private void parseJSON() {
        String url = "https://corona-api.com/countries/PH";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("data");
                            getLatestData(jsonObject);

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

    public void getLatestData(JSONObject jsonObject) throws JSONException {
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
    }
}
