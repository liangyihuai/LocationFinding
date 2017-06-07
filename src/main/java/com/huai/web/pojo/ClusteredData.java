package com.huai.web.pojo;

/**
 * Created by liangyh on 4/22/17.
 */
public class ClusteredData {

    private double longitude;
    private double latitude;

    public ClusteredData() {
    }

    public ClusteredData(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
