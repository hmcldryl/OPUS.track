package com.tracker.projectopus;

import java.util.Date;

public class TrackerChart {

    private String phCase;
    private String phDate;
    private String phAge;
    private String phGender;
    private String phNationality;
    private String phHospital;
    private String phTravelHistory;
    private String phStatus;
    private String phLat;
    private String phLong;
    private String phResident;

    public TrackerChart(String phCase,
                        String phDate,
                        String phAge,
                        String phGender,
                        String phNationality,
                        String phHospital,
                        String phTravelHistory,
                        String status,
                        String phLat,
                        String phLong,
                        String phResident) {
    }

    public String getPhCase() {
        return phCase;
    }

    public String getPhDate() {
        return phDate;
    }

    public String getPhAge() {
        return phAge;
    }

    public String getPhGender() {
        return phGender;
    }

    public String getPhNationality() {
        return phNationality;
    }

    public String getPhHospital() {
        return phHospital;
    }

    public String getPhTravelHistory() {
        return phTravelHistory;
    }

    public String getPhStatus() {
        return phStatus;
    }

    public String getPhLat() {
        return phLat;
    }

    public String getPhLong() {
        return phLong;
    }

    public String getPhResident() {
        return phResident;
    }
}
