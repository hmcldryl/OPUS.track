package com.tracker.projectopus;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {
    private Context timelineContext;
    private ArrayList<TrackerTimeline> timelineArrayList;

    public TimelineAdapter(Context context, ArrayList<TrackerTimeline> arrayList) {
        timelineContext = context;
        timelineArrayList = arrayList;
    }

    @NonNull
    @Override
    public TimelineAdapter.TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(timelineContext).inflate(R.layout.timeline_list, parent, false);
        return new TimelineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineAdapter.TimelineViewHolder holder, int position) {
        TrackerTimeline trackerTimeline = timelineArrayList.get(position);

        int timeline_deaths, timeline_confirmed, timeline_recovered = 0;
        String timeline_date;

        timeline_date = trackerTimeline.getDate();
        timeline_recovered = trackerTimeline.getRecovered();
        timeline_confirmed = trackerTimeline.getConfirmed();
        timeline_deaths = trackerTimeline.getDeaths();

        String date = timeline_date;
        String recovered = Integer.toString(timeline_recovered);
        String confirmed = Integer.toString(timeline_confirmed);
        String deaths = Integer.toString(timeline_deaths);

        holder.timelineDate.setText(date);
        holder.timelineDeaths.setText(confirmed);
        holder.timelineConfirmed.setText(recovered);
        holder.timelineRecovered.setText(deaths);
    }

    @Override
    public int getItemCount() {
        return timelineArrayList.size();
    }

    public class TimelineViewHolder extends RecyclerView.ViewHolder {
        public TextView timelineDate;
        public TextView timelineRecovered;
        public TextView timelineConfirmed;
        public TextView timelineDeaths;

        public TimelineViewHolder(View itemView) {
            super(itemView);
            timelineDate = itemView.findViewById(R.id.timeline_updated_on);
            timelineRecovered = itemView.findViewById(R.id.timeline_recovered_text);
            timelineConfirmed = itemView.findViewById(R.id.timeline_confirmed_text);
            timelineDeaths = itemView.findViewById(R.id.timeline_deaths_text);
        }
    }
}
