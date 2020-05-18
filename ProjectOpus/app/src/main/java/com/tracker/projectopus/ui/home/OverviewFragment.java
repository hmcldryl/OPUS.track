package com.tracker.projectopus.ui.home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tracker.projectopus.CaseAdapter;
import com.tracker.projectopus.ChartAdapter;
import com.tracker.projectopus.DatabaseHelper;
import com.tracker.projectopus.R;
import com.tracker.projectopus.TimelineAdapter;
import com.tracker.projectopus.TrackerChart;
import com.tracker.projectopus.TrackerTimeline;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OverviewFragment extends Fragment {

    private RecyclerView recyclerView1, recyclerView2;
    private ArrayList<TrackerTimeline> timelineArrayList;
    private ArrayList<TrackerChart> casesArrayList;
    private RequestQueue requestQueue;
    private TextView text0, text1, text2, text3, textRecovery, textCpm, textRvd, textDeaths,
            phPopulation, todayRecoveredText, todayConfirmedText, todayDeathsText, updatedAt, updatedAtGlobal,
            globalConfirmedtv, globalRecoveredtv, globalDeathstv;
    private OverviewViewModel overviewViewModel;
    private DatabaseHelper phCaseDataDb;
    private CaseAdapter caseAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        overviewViewModel =
                ViewModelProviders.of(this).get(OverviewViewModel.class);
        View root = inflater.inflate(R.layout.fragment_overview, container, false);

        phCaseDataDb = new DatabaseHelper(this.getActivity());
        updatedAt = root.findViewById(R.id.updatedAsOf);
        phPopulation = root.findViewById(R.id.today_population);
        text0 = root.findViewById(R.id.dataDeaths);
        text1 = root.findViewById(R.id.dataConfirmed);
        text2 = root.findViewById(R.id.dataRecovered);
        text3 = root.findViewById(R.id.dataCritical);
        textRecovery = root.findViewById(R.id.calculatedRecoveryRate);
        textCpm = root.findViewById(R.id.calculatedCpmRate);
        textRvd = root.findViewById(R.id.calculatedRvdRate);
        textDeaths = root.findViewById(R.id.calculatedDeathsRate);
        todayRecoveredText = root.findViewById(R.id.today_recovered);
        todayConfirmedText = root.findViewById(R.id.today_confirmed);
        todayDeathsText = root.findViewById(R.id.today_deaths);
        globalRecoveredtv = root.findViewById(R.id.today_recoveredGlobal);
        globalConfirmedtv = root.findViewById(R.id.today_confirmedGlobal);
        globalDeathstv = root.findViewById(R.id.today_deathsGlobal);
        updatedAtGlobal = root.findViewById(R.id.updatedAsOf1);

        requestQueue = Volley.newRequestQueue(this.getActivity());

        getCasesPH();
        getTodayPH_2();
        getTodayPH_1();
        getDataPH();
        getDataGlobal();

        return root;
    }

    public void getTodayPH_1() {
        String url = "https://covidapi.info/api/v1/country/PHL/latest";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Date current = Calendar.getInstance().getTime();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String currentDate = simpleDateFormat.format(current);
                            JSONObject today = response.getJSONObject(currentDate);
                            int todayConfirmed = today.getInt("confirmed");
                            int todayRecovered = today.getInt("recovered");
                            int todayDeaths = today.getInt("deaths");
                            String today_recovered = "Recovered: " + todayRecovered;
                            String today_confirmed = "Confirmed: " + todayConfirmed;
                            String today_deaths = "Deaths: " + todayDeaths;

                            todayRecoveredText.setText(today_recovered);
                            todayConfirmedText.setText(today_confirmed);
                            todayDeathsText.setText(today_deaths);

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

    public void getTodayPH_2() {
        String url = "https://corona-api.com/countries/PH?include=timeline";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("data");

                            String updated_at = jsonObject.getString("updated_at");
                            int phPop = jsonObject.getInt("population");

                            String latestPhPop = "Population: " + Long.parseLong(String.valueOf(phPop));
                            phPopulation.setText(latestPhPop);

                            String parsed = parseDate(updated_at);
                            updatedAt.setText(parsed);
                            updatedAtGlobal.setText(parsed);

                            getLatestData(jsonObject);
                            getCalculatedData(jsonObject);

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

    public void getCasesPH() {
        phCaseDataDb = new DatabaseHelper(this.getActivity());
        Date current = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = simpleDateFormat.format(current);
        String url = "https://covidapi.info/api/v1/country/PHL/timeseries/2020-01-30/" + currentDate;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject phDataCount = response.getJSONObject("count");
                            JSONArray phData = response.getJSONArray("result");
                            if (phData.length() > 0) {
                                for (int i = 0; i < phData.length(); i++) {
                                    JSONObject data = phData.getJSONObject(i);

                                    String date = data.getString("date");
                                    int recovered = data.getInt("recovered");
                                    int confirmed = data.getInt("confirmed");
                                    int deaths = data.getInt("deaths");

                                    phCaseDataDb.insertData(date, recovered, confirmed, deaths);
                                }
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

    public void getDataGlobal() {
        String url = "https://covidapi.info/api/v1/global";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("result");
                            int globalRecovered = jsonObject.getInt("recovered");
                            int globalConfirmed = jsonObject.getInt("confirmed");
                            int globalDeaths = jsonObject.getInt("deaths");

                            String recovered = "Recovered: " + globalRecovered;
                            String confirmed = "Confirmed: " + globalConfirmed;
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

    public void getDataPH() {
        String url = "https://coronavirus-ph-api.herokuapp.com/cases";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            casesArrayList = new ArrayList<>();
                            ChartAdapter chartAdapter = new ChartAdapter(getActivity(), casesArrayList);
                            if (response.length() > 0) {
                                for (int i = 0; i < response.length(); i++) {
                                    String phCaseNo = response.getJSONObject(i).getString("case_no");
                                    String phDate = response.getJSONObject(i).getString("date");
                                    String phAge = response.getJSONObject(i).getString("age");
                                    String phGender = response.getJSONObject(i).getString("gender");
                                    String phNationality = response.getJSONObject(i).getString("nationality");
                                    String phHospital = response.getJSONObject(i).getString("hospital_admitted_to");
                                    String phTravelHistory = response.getJSONObject(i).getString("travel_history");
                                    String phStatus = response.getJSONObject(i).getString("status");
                                    String phLat = response.getJSONObject(i).getString("latitude");
                                    String phLong = response.getJSONObject(i).getString("longitude");
                                    String phResident = response.getJSONObject(i).getString("resident_of");

                                    casesArrayList.add(new TrackerChart(phCaseNo, phDate, phAge, phGender, phNationality, phHospital, phTravelHistory, phStatus, phLat, phLong, phResident));
                                }
                                recyclerView1.setAdapter(chartAdapter);
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

    public void getCalculatedData(JSONObject jsonObject) throws JSONException {
        JSONObject latest_data = jsonObject.getJSONObject("latest_data");
        JSONObject calculated = latest_data.getJSONObject("calculated");

        String recovery_rate = calculated.getString("recovery_rate");
        String cases_per_m_pop = calculated.getString("cases_per_million_population");
        String recovery_death_ratio = calculated.getString("recovered_vs_death_ratio");
        String death_rate = calculated.getString("death_rate");

        String a1 = String.format("%.2f", Float.parseFloat(recovery_rate));
        String a2 = String.format("%.2f", Float.parseFloat(cases_per_m_pop));
        String a4 = String.format("%.2f", Float.parseFloat(death_rate));

        String calculated_recovery_rate = "Recovery %: " + a1 + "%";
        textRecovery.setText(calculated_recovery_rate);

        String calculated_cpm_population = "CpM Pop.: " + a2 + "%";
        textCpm.setText(calculated_cpm_population);

        if (!recovery_death_ratio.equals("null")) {
            String calculated_dr_ratio = "Recovery vs. Death %: " + recovery_death_ratio + "%";
            textRvd.setText(calculated_dr_ratio);
        }

        String calculated_death_rate = "Death %: " + a4 + "%";
        textDeaths.setText(calculated_death_rate);
    }

    public void getLatestData(JSONObject jsonObject) throws JSONException {
        JSONObject latest_data = jsonObject.getJSONObject("latest_data");

        int recovered = latest_data.getInt("recovered");
        int confirmed = latest_data.getInt("confirmed");
        int critical = latest_data.getInt("critical");
        int deaths = latest_data.getInt("deaths");

        String latest_recovered = "Recovered: " + recovered;
        String latest_confirmed = "Confirmed: " + confirmed;
        String latest_critical = "Critical: " + critical;
        String latest_deaths = "Deaths: " + deaths;

        text2.setText(latest_recovered);
        text1.setText(latest_confirmed);
        text3.setText(latest_critical);
        text0.setText(latest_deaths);
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
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        String outputPattern = "EEE, MMM dd HH:ss";
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
        return "(" + str + ")";
    }

    public Cursor getAllItems() {
        SQLiteDatabase db = phCaseDataDb.getWritableDatabase();
        return db.query(DatabaseHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DatabaseHelper.COL_2 + " DESC");
    }
}
