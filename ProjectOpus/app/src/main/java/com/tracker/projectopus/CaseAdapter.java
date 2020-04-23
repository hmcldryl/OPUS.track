package com.tracker.projectopus;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.CaseViewHolder> {
    private Context caContext;
    private Cursor caCursor;

    public CaseAdapter (Context context, Cursor cursor) {
        caContext = context;
        caCursor = cursor;
    }

    public static class CaseViewHolder extends RecyclerView.ViewHolder {
        public TextView phDate;
        public TextView phRecovered;
        public TextView phConfirmed;
        public TextView phDeaths;

        public CaseViewHolder(@NonNull View itemView) {
            super(itemView);

            phDate = itemView.findViewById(R.id.timeline_updated_on);
            phRecovered = itemView.findViewById(R.id.timeline_recovered_text);
            phConfirmed = itemView.findViewById(R.id.timeline_confirmed_text);
            phDeaths = itemView.findViewById(R.id.timeline_deaths_text);
        }
    }

    @NonNull
    @Override
    public CaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(caContext);
        View view = layoutInflater.inflate(R.layout.timeline_list, parent, false);
        return new CaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CaseViewHolder holder, int position) {

        if (!caCursor.move(position)) {
            return;
        }
        String date = caCursor.getString(caCursor.getColumnIndex(DatabaseHelper.COL_2));
        int recovered = caCursor.getInt(caCursor.getColumnIndex(DatabaseHelper.COL_3));
        int confirmed = caCursor.getInt(caCursor.getColumnIndex(DatabaseHelper.COL_4));
        int deaths = caCursor.getInt(caCursor.getColumnIndex(DatabaseHelper.COL_5));

        holder.phDate.setText(date);
        holder.phRecovered.setText(recovered);
        holder.phConfirmed.setText(confirmed);
        holder.phDeaths.setText(deaths);
    }

    @Override
    public int getItemCount() {
        return caCursor.getCount();
    }

    public void swapCursor (Cursor cursor) {
        if (caCursor != null) {
            caCursor.close();
        }
        caCursor = cursor;
        if (cursor != null) {
            notifyDataSetChanged();
        }
    }

}
