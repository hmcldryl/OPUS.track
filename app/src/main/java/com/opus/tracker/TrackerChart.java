package com.opus.tracker;

public class TrackerChart {

    private String case_code;
    private String age;
    private String sex;
    private String is_admitted;
    private String date_reported;
    private String date_died;
    private String recovered_on;
    private String location;
    private String latitude;
    private String longitude;

    public TrackerChart(String case_code,
                        String age,
                        String sex,
                        String is_admitted,
                        String date_reported,
                        String date_died,
                        String recovered_on,
                        String location,
                        String phLat,
                        String phLong) {
    }

    public String getCase_code() {
        return case_code;
    }

    public String getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getIs_admitted() {
        return is_admitted;
    }

    public String getDate_reported() {
        return date_reported;
    }

    public String getDate_died() {
        return date_died;
    }

    public String getRecovered_on() {
        return recovered_on;
    }

    public String getLocation() {
        return location;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

}
