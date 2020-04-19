package com.tracker.projectopus;

import java.util.Date;

public class TrackerTimeline {

    private String date;
    private int recovered;
    private int confirmed;
    private int deaths;

    public TrackerTimeline(String date,
                           int recovered,
                           int confirmed,
                           int deaths) {
    }

    public String getDate() {
        return date;
    }

    public int getRecovered() {
        return recovered;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int getDeaths() {
        return deaths;
    }

}