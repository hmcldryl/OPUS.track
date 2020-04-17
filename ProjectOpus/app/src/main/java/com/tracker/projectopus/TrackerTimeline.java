package com.tracker.projectopus;

import java.util.Date;

public class TrackerTimeline {

    private Date updated_at;
    private Date date;
    private int deaths;
    private int confirmed;
    private int recovered;
    private int active;
    private int new_confirmed;
    private int new_recovered;
    private int new_deaths;
    private boolean is_in_progress;

    public TrackerTimeline (int deaths,
                            int confirmed,
                            int recovered) {
    }

    public Date getUpdate() {
        return updated_at;
    }

    public Date getDate() {
        return date;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int getRecovered() {
        return recovered;
    }

    public int getActive() {
        return active;
    }

    public int getNew_confirmed() {
        return new_confirmed;
    }

    public int getNew_recovered() {
        return new_recovered;
    }

    public int getNew_deaths() {
        return new_deaths;
    }

    public boolean getIs_in_progress() {
        return is_in_progress;
    }
}
