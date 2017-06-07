package com.huai.web.pojo;

/**
 * Created by liangyh on 3/12/17.
 */
public class Result {
    private String phoneNo;
    private double longitude;
    private double latitude;
    private int batchNo;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
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

    public int getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(int batchNo) {
        this.batchNo = batchNo;
    }

    @Override
    public String toString() {
        return "ResultSet{" +
                "phoneNo='" + phoneNo + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", batchNo=" + batchNo +
                '}';
    }
}
