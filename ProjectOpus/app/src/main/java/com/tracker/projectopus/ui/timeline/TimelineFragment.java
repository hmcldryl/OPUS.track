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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.tracker.projectopus.R;
import com.tracker.projectopus.TimelineAdapter;
import com.tracker.projectopus.TrackerTimeline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        phTimeline();

        return root;
    }

    public void phTimeline() {
        Date current = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = simpleDateFormat.format(current);
        String url = "https://covidapi.info/api/v1/country/PHL/timeseries/2020-01-30/" + currentDate;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray phData = response.getJSONArray("result");
                            //getPhTimelineData(data);
                            if (phData.length() > 0) {
                                for (int i = 0; i < phData.length(); i++) {
                                    JSONObject data = phData.getJSONObject(i);

                                    String date = data.getString("date");
                                    int recovered = data.getInt("recovered");
                                    int confirmed = data.getInt("confirmed");
                                    int deaths = data.getInt("deaths");

                                    timelineArrayList.add(new TrackerTimeline(date, recovered, confirmed, deaths));
                                }
                                TimelineAdapter timelineAdapter = new TimelineAdapter(getActivity(), timelineArrayList);
                                recyclerView.setAdapter(timelineAdapter);
                            }
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



    public String parseTime(String time) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        String outputPattern = "EEE, MMMM dd, yyyy | hh:ss a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "(" + str + ")";
    }
}
