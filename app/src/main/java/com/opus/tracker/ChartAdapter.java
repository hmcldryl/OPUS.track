package com.opus.tracker;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.CaseViewHolder> {
    private Context chartContext;
    private ArrayList<TrackerChart> chartArrayList;

    public ChartAdapter(Context context, ArrayList<TrackerChart> arrayList) {
        chartContext = context;
        chartArrayList = arrayList;
    }

    @NonNull
    @Override
    public ChartAdapter.CaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(chartContext).inflate(R.layout.cases_list, parent, false);
        return new CaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChartAdapter.CaseViewHolder holder, int position) {
        TrackerChart caseTracker = chartArrayList.get(position);

        String phCase_code = caseTracker.getCase_code();
        String phAge = caseTracker.getAge();
        String phSex = caseTracker.getSex();
        String phIs_admitted = caseTracker.getIs_admitted();
        String phDate_reported = caseTracker.getDate_reported();
        String phDate_died = caseTracker.getDate_died();
        String phRecovered_on = caseTracker.getRecovered_on();
        String phLocation = caseTracker.getLocation();
        String phLatitude = caseTracker.getLatitude();
        String phLongitude = caseTracker.getLongitude();

        String caseCode = "Case No.: " + phCase_code;
        String age = "Age: " + phAge;
        String sex = "Sex: " + phSex;
        String isAdmitted = "Is admitted: " + phIs_admitted;

        String statusText;
        if (phDate_died == null && phRecovered_on != null) {
            statusText = "ALIVE";
        } else if (phDate_died != null && phRecovered_on == null) {
            statusText = "DECEASED";
        } else {
            statusText = "TBD";
        }

        String dateReported = "Date Reported: " + phDate_reported;
        String dateDied = "Date Died: " + phDate_died;
        String dateRecovered = "Date Recovered: " + phRecovered_on;
        String location = "Location: " + phLocation;
        String location1 = "Lat + Long: " + phLatitude + "+" + phLongitude;

        holder.chartCase_code.setText(caseCode);
        holder.chartAge.setText(age);
        holder.chartSex.setText(sex);
        holder.chartIs_admitted.setText(isAdmitted);
        holder.chartDate_reported.setText(dateReported);
        holder.chartDate_died.setText(dateDied);
        holder.chartRecovered_on.setText(dateRecovered);
        holder.chartLocation.setText(location);
        holder.chartLocation1.setText(location1);
        if (statusText.equals("DECEASED")) {
            holder.chartStatus.setTextColor(Color.RED);
        }
        else if (statusText.equals("ALIVE")) {
            holder.chartStatus.setTextColor(Color.GREEN);
        }
        else {
            holder.chartStatus.setTextColor(Color.WHITE);
        }
        holder.chartStatus.setText(statusText);
    }

    @Override
    public int getItemCount() {
        return chartArrayList.size();
    }

    public class CaseViewHolder extends RecyclerView.ViewHolder {
        public TextView chartCase_code;
        public TextView chartAge;
        public TextView chartSex;
        public TextView chartIs_admitted;
        public TextView chartDate_reported;
        public TextView chartDate_died;
        public TextView chartRecovered_on;
        public TextView chartLocation;
        public TextView chartLocation1;
        public TextView chartStatus;

        public CaseViewHolder(View itemView) {
            super(itemView);
            chartCase_code = itemView.findViewById(R.id.case_code);
            chartAge = itemView.findViewById(R.id.case_age);
            chartSex = itemView.findViewById(R.id.case_sex);
            chartIs_admitted = itemView.findViewById(R.id.case_is_admitted);
            chartDate_reported = itemView.findViewById(R.id.case_reported_on);
            chartDate_died = itemView.findViewById(R.id.case_died_on);
            chartRecovered_on = itemView.findViewById(R.id.case_recovered_on);
            chartLocation = itemView.findViewById(R.id.case_location);
            chartLocation1 = itemView.findViewById(R.id.case_location1);
            chartStatus = itemView.findViewById(R.id.case_status);
        }
    }
}
