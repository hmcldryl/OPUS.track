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

        timeline_deaths = trackerTimeline.getDeaths();
        timeline_confirmed = trackerTimeline.getConfirmed();
        timeline_recovered = trackerTimeline.getRecovered();

            String deaths = Integer.toString(timeline_deaths);
            String confirmed = Integer.toString(timeline_confirmed);
            String recovered = Integer.toString(timeline_recovered);

            holder.timelineDeaths.setText(deaths);
            holder.timelineDeaths.setText(confirmed);
            holder.timelineDeaths.setText(recovered);
    }

    @Override
    public int getItemCount() {
        return timelineArrayList.size();
    }

    public class TimelineViewHolder extends RecyclerView.ViewHolder {
        public TextView timelineDeaths;
        public TextView timelineConfirmed;
        public TextView timelineRecovered;

        public TimelineViewHolder(View itemView) {
            super(itemView);
            timelineDeaths = itemView.findViewById(R.id.timeline_deaths_text);
            timelineConfirmed = itemView.findViewById(R.id.timeline_confirmed_text);
            timelineRecovered = itemView.findViewById(R.id.timeline_recovered_text);
        }
    }
}
