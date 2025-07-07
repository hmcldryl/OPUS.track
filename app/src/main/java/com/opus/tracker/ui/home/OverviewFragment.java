package com.opus.tracker.ui.home;

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
import com.opus.tracker.ChartAdapter;
import com.opus.tracker.R;
import com.opus.tracker.TrackerChart;
import com.opus.tracker.ui.home.OverviewViewModel;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OverviewFragment extends Fragment {

    private RecyclerView localCaseData;
    private ArrayList<TrackerChart> casesArrayList;
    private RequestQueue requestQueue;
    private TextView phPopulation, todayRecoveredText,
            todayConfirmedText, todayDeathsText, updatedAt, updatedAtGlobal,
            globalConfirmedtv, globalRecoveredtv, globalDeathstv, totalRecoveries, totalDeaths, totalAdmitted,
            fatalityRate, recoveryRate, totalCases;
    private OverviewViewModel overviewViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        overviewViewModel =
                ViewModelProviders.of(this).get(OverviewViewModel.class);
        View root = inflater.inflate(R.layout.fragment_overview, container, false);

        updatedAt = root.findViewById(R.id.updatedAsOf);
        phPopulation = root.findViewById(R.id.today_pop);
        todayRecoveredText = root.findViewById(R.id.today_recovered);
        todayConfirmedText = root.findViewById(R.id.today_cases);
        todayDeathsText = root.findViewById(R.id.today_deaths);
        totalCases = root.findViewById(R.id.phTotalCases);
        totalRecoveries = root.findViewById(R.id.phTotalRecoveries);
        totalAdmitted = root.findViewById(R.id.phTotalAdmitted);
        totalDeaths = root.findViewById(R.id.phTotalDeaths);
        fatalityRate = root.findViewById(R.id.phFatality);
        recoveryRate = root.findViewById(R.id.phTotalRecovery);
        globalRecoveredtv = root.findViewById(R.id.today_recoveredGlobal);
        globalConfirmedtv = root.findViewById(R.id.today_casesGlobal);
        globalDeathstv = root.findViewById(R.id.today_deathsGlobal);
        updatedAtGlobal = root.findViewById(R.id.updatedAsOf1);
        localCaseData = root.findViewById(R.id.casesSummary);

        localCaseData.setHasFixedSize(true);
        localCaseData.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        casesArrayList = new ArrayList<>();

        //Snackbar.make(root, "No action yet.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        //Toast toast=Toast.makeText(this.getActivity(),"Stay at home if your feel unwell.\nIf " +
                //"you have a fever, cough and difficulty breathing, seek medical attention and call in advance.\n" +
                ///"Follow the directions of your local health authority.\n" +
                //"- World Health Organization",Toast.LENGTH_SHORT);
        //toast.show();

        requestQueue = Volley.newRequestQueue(this.getActivity());

        getLocalData();
        getLocalData_more();
        getGlobalData();
        getLocalCaseData();

        return root;
    }

    public void getLocalData() {
        String url = "https://coronavirus-ph-api.herokuapp.com/total";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");

                            int todayCases = data.getInt("cases_today");
                            int todayDeaths = data.getInt("deaths_today");
                            int todayRecoveries = data.getInt("recoveries_today");

                            int admitted = data.getInt("admitted");
                            int cases = data.getInt("cases");
                            int deaths = data.getInt("deaths");
                            int recoveries = data.getInt("recoveries");

                            String recovery_rate = data.getString("recovery_rate");
                            String fatality_rate = data.getString("fatality_rate");
                            //String recoveryR = String.format("%.2f", Float.parseFloat(recovery_rate));
                            //String fatalityR = String.format("%.2f", Float.parseFloat(fatality_rate));

                            String today_cases = "Cases: " + todayCases;
                            String today_deaths = "Deaths: " + todayDeaths;
                            String today_recovered = "Recoveries: " + todayRecoveries;

                            String phAdmitted = "Admitted: " + admitted;
                            String phCases = "Cases: " + cases;
                            String phDeaths = "Deaths: " + deaths;
                            String phRecoveries = "Recoveries: " + recoveries;

                            String rr = "Recovery Rate: " + recovery_rate;
                            String fr = "Fatality Rate: " + fatality_rate;

                            String updated_at = data.getString("last_update");
                            String parsed = parseDate(updated_at);
                            updatedAt.setText(parsed);

                            //String rr = "Recovery Rate: " + recoveryR + "%";
                            //String fr = "Fatality Rate: " + fatalityR + "%";

                            todayConfirmedText.setText(today_cases);
                            todayDeathsText.setText(today_deaths);
                            todayRecoveredText.setText(today_recovered);

                            totalAdmitted.setText(phAdmitted);
                            totalCases.setText(phCases);
                            totalDeaths.setText(phDeaths);
                            totalRecoveries.setText(phRecoveries);

                            recoveryRate.setText(rr);
                            fatalityRate.setText(fr);

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

    public void getLocalData_more() {
        String url = "https://corona-api.com/countries/PH";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");

                            String updated_at = data.getString("updated_at");
                            int phPop = data.getInt("population");
                            String latestPhPop = "Population: " + Long.parseLong(String.valueOf(phPop));

                            String parsed = parseDateGlobal(updated_at);
                            updatedAtGlobal.setText(parsed);
                            phPopulation.setText(latestPhPop);

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

    public void getGlobalData() {
        String url = "https://covidapi.info/api/v1/global";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject result = response.getJSONObject("result");
                            int globalRecovered = result.getInt("recovered");
                            int globalConfirmed = result.getInt("confirmed");
                            int globalDeaths = result.getInt("deaths");

                            String recovered = "Recovered: " + globalRecovered;
                            String confirmed = "Cases: " + globalConfirmed;
                            String deaths = "Deaths: " + globalDeaths;

                            globalRecoveredtv.setText(recovered);
                            globalConfirmedtv.setText(confirmed);
                            globalDeathstv.setText(deaths);

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

    public void getLocalCaseData() {
        String url = "https://coronavirus-ph-api.herokuapp.com/doh-data-drop";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("data");
                            if (data.length() > 0) {
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject n = data.getJSONObject(i);

                                    String phCaseCode = n.getString("case_code");
                                    String phAge = n.getString("age");
                                    String phSex = n.getString("sex");
                                    String phAdmitted = n.getString("is_admitted");
                                    String phDateReported = n.getString("date_reported");
                                    String phDateDied = n.getString("date_died");
                                    String phRecovered = n.getString("recovered_on");
                                    String phLocation = n.getString("location");
                                    String phLat = n.getString("latitude");
                                    String phLong = n.getString("longitude");

                                    casesArrayList.add(new TrackerChart(phCaseCode, phAge, phSex, phAdmitted, phDateReported, phDateDied, phRecovered, phLocation, phLat, phLong));
                                }
                                ChartAdapter chartAdapter = new ChartAdapter(getActivity(), casesArrayList);
                                localCaseData.setAdapter(chartAdapter);
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
        String outputPattern = "EEE, MMMM dd hh:ss a";
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

    public String parseDate(String time) {
        String inputPattern = "yyyy-MM-dd";
        //String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        //String outputPattern = "EEE, MMM dd, HH:ss";
        String outputPattern = "EEE, MMM dd, yyyy";
        //String outputPattern = "EEE, MMMM dd, yyyy hh:mm a";
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
        return "(as of " + str + ")";
    }

    public String parseDateGlobal(String time) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        //String outputPattern = "EEE, MMM dd, HH:ss";
        String outputPattern = "EEE, MMM dd, HH:ss";
        //String outputPattern = "EEE, MMMM dd, yyyy hh:mm a";
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
        return "(as of " + str + ")";
    }
}
