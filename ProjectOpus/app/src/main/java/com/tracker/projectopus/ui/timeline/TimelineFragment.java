package com.tracker.projectopus.ui.timeline;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class TimelineFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<TrackerTimeline> timelineArrayList;
    private RequestQueue requestQueue;
    private TimelineViewModel timelineViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        timelineViewModel =
                ViewModelProviders.of(this).get(TimelineViewModel.class);
        View root = inflater.inflate(R.layout.fragment_timeline, container, false);

        recyclerView = root.findViewById(R.id.timeline);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        timelineArrayList = new ArrayList<>();
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
                            getTimelineData(jsonObject);

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

    public void getTimelineData(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("timeline");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject data = jsonArray.getJSONObject(i);

            int timelineDeaths = data.getInt("deaths");
            int timelineConfirmed = data.getInt("confirmed");
            int timelineRecovered = data.getInt("recovered");

            timelineArrayList.add(new TrackerTimeline(timelineDeaths, timelineConfirmed, timelineRecovered));
        }
        TimelineAdapter timelineAdapter = new TimelineAdapter(getActivity(), timelineArrayList);
        recyclerView.setAdapter(timelineAdapter);
    }
}
