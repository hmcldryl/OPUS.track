package com.tracker.projectopus;


import android.content.Context;
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

        String phCase = caseTracker.getPhCase();
        String phDate = caseTracker.getPhDate();
        String phAge = caseTracker.getPhAge();
        String phGender = caseTracker.getPhGender();
        String phNationality = caseTracker.getPhNationality();
        String phHospital = caseTracker.getPhHospital();
        String phTravelHistory = caseTracker.getPhTravelHistory();
        String phStatus = caseTracker.getPhStatus();
        String phLat = caseTracker.getPhLat();
        String phLong = caseTracker.getPhLong();
        String phResident = caseTracker.getPhResident();

        String casePh = "Case No.: " + phCase;
        String datePh = "Date: " + phDate;
        String agePh = "Age: " + phAge;
        String genderPh = "Gender: " + phGender;
        String nationalityPh = "Nationality: " + phNationality;
        String hospitalPh = "Hospital Admitted To: " + phHospital;
        String travelPh = "Has Travel History: " + phTravelHistory;
        String statusPh = "Status: " + phStatus;
        String locPh = "Location: " + phLat + "+" + phLong;
        String residentPh = "Resident of: " + phResident;

        holder.chartCase.setText(casePh);
        holder.chartDate.setText(datePh);
        holder.chartAge.setText(agePh);
        holder.chartGender.setText(genderPh);
        holder.chartNationality.setText(nationalityPh);
        holder.chartHospital.setText(hospitalPh);
        holder.chartTravelHistory.setText(travelPh);
        holder.chartStatus.setText(statusPh);
        holder.chartLoc.setText(locPh);
        holder.chartResident.setText(residentPh);
    }

    @Override
    public int getItemCount() {
        return chartArrayList.size();
    }

    public class CaseViewHolder extends RecyclerView.ViewHolder {
        public TextView chartCase;
        public TextView chartDate;
        public TextView chartAge;
        public TextView chartGender;
        public TextView chartNationality;
        public TextView chartHospital;
        public TextView chartTravelHistory;
        public TextView chartStatus;
        public TextView chartLoc;
        public TextView chartResident;

        public CaseViewHolder(View itemView) {
            super(itemView);
            chartCase = itemView.findViewById(R.id.case_no);
            chartDate = itemView.findViewById(R.id.case_date);
            chartAge = itemView.findViewById(R.id.case_age);
            chartGender = itemView.findViewById(R.id.case_gender);
            chartNationality = itemView.findViewById(R.id.case_nationality);
            chartHospital = itemView.findViewById(R.id.case_hospital);
            chartTravelHistory = itemView.findViewById(R.id.case_travel);
            chartStatus = itemView.findViewById(R.id.case_status);
            chartLoc = itemView.findViewById(R.id.case_location);
            chartResident = itemView.findViewById(R.id.case_resident);

        }
    }
}
