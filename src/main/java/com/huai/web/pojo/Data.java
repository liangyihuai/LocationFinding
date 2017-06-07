package com.huai.web.pojo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by liangyh on 4/22/17.
 */
public class Data {
    private List<RawDataSet> rawDataSet = new LinkedList<>();
    private List<ClusteredData> clusteredDataSet = new LinkedList<>();

    public List<RawDataSet> getRawDataSet() {
        return rawDataSet;
    }

    public void setRawDataSet(List<RawDataSet> rawDataSet) {
        this.rawDataSet = rawDataSet;
    }

    public List<ClusteredData> getClusteredDataSet() {
        return clusteredDataSet;
    }

    public void setClusteredDataSet(List<ClusteredData> clusteredDataSet) {
        this.clusteredDataSet = clusteredDataSet;
    }
}
