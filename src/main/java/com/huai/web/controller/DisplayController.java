package com.huai.web.controller;

import com.huai.web.pojo.ClusteredData;
import com.huai.web.pojo.Data;
import com.huai.web.pojo.RawDataSet;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import scala.Int;

import java.io.*;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by liangyh on 4/22/17.
 */
@Controller
@RequestMapping(value = "/display")
public class DisplayController {

    @ResponseBody
    @RequestMapping(value = "/data")
    public Data getRawDataSetAndClusteredData(String dataset, String resultset,
                  String longitudeIndex, String latitudeIndex, String separator){

        if(separator.equals("|")){
            separator = "\\|";
        }

        Data result = new Data();

        BufferedReader rawDataReader = null;

        try {
            if(dataset.startsWith("hdfs")){
                result.getRawDataSet().addAll(readRawDataFromHDFS(dataset, longitudeIndex, latitudeIndex, separator));
            }else{
                File datasetFile = new File(dataset);
                if(datasetFile.exists() && datasetFile.isFile()){
                    rawDataReader = new BufferedReader(new InputStreamReader(new FileInputStream(datasetFile)));
                    String line = "";
                    while ((line = rawDataReader.readLine()) != null){
                        RawDataSet rawDataSet = getRawDataSet(line, longitudeIndex, latitudeIndex, separator);
                        if(rawDataSet != null)result.getRawDataSet().add(rawDataSet);
                    }
                }
            }


            if(resultset.startsWith("hdfs")){
                result.getClusteredDataSet().addAll(readClusteredDataFromHDFS(resultset));
            }else{
                File resultsetFile = new File(resultset);
                if(resultsetFile.exists()){
                    if(resultsetFile.isFile()){
                        result.getClusteredDataSet().addAll(readClusteredData(longitudeIndex, latitudeIndex, resultsetFile));
                    }else if(resultsetFile.isDirectory()){
                        File [] files = resultsetFile.listFiles();
                        for(File file: files){
                            if(file.getName().startsWith("."))continue;
                            result.getClusteredDataSet().addAll(readClusteredData(longitudeIndex, latitudeIndex, file));
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(rawDataReader != null){
                    rawDataReader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return result;
    }


    private List<ClusteredData> readClusteredData(String longitudeIndex, String latitudeIndex, File resultsetFile){
        List<ClusteredData> result = new LinkedList<>();
        BufferedReader clusteredDataReader = null;
        try{
            clusteredDataReader = new BufferedReader(new InputStreamReader(new FileInputStream(resultsetFile)));
            String line = "";
            while ((line = clusteredDataReader.readLine()) != null){
                ClusteredData clusteredData = getClusteredData(line);
                if(clusteredData != null) result.add(clusteredData);
            }
        }catch (IOException e){
           e.printStackTrace();
        }finally {
            try{
                if(clusteredDataReader != null){
                    clusteredDataReader.close();
                }
            }catch (IOException e2){
                e2.printStackTrace();
            }
        }
        return result;
    }

    public List<ClusteredData> readClusteredDataFromHDFS(String pathStr){
        List<ClusteredData> result = new LinkedList<>();

        FileSystem fs = null;
        BufferedReader br = null;
        try {
            Configuration conf = new Configuration();

            fs = FileSystem.get(URI.create(pathStr),conf);
            if(fs.isDirectory(new Path(pathStr))){
                FileStatus[] fileStatuses = fs.listStatus(new Path(pathStr));
                for(FileStatus status: fileStatuses){
                    if(status.isFile() && status.getPath().getName().startsWith("part-")){
                        FSDataInputStream inputStream = fs.open(status.getPath());
                        br = new BufferedReader(new InputStreamReader(inputStream));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            ClusteredData clusteredData = getClusteredData(line);
                            if(clusteredData != null) result.add(clusteredData);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(br != null)br.close();
                if(fs != null)fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private ClusteredData getClusteredData(String line){
        String [] items = line.split("\t");
        if(items.length > 1){
            double longitude = Double.valueOf(items[1]);
            double lat = Double.valueOf(items[2]);
            return new ClusteredData(longitude, lat);
        }
        return null;
    }

    private RawDataSet getRawDataSet(String line, String longitudeIndex, String latitudeIndex, String separator){
        String [] items = line.split(separator);
        if(items.length > 1){
            double longitude = Double.valueOf(items[Integer.valueOf(longitudeIndex)]);
            double lat = Double.valueOf(items[Integer.valueOf(latitudeIndex)]);
            return new RawDataSet(longitude, lat);
        }
        return null;
    }

    public List<RawDataSet> readRawDataFromHDFS(String pathStr, String longitudeIndex,
                                                String latitudeIndex, String separator){
        List<RawDataSet> result = new LinkedList<>();

        FileSystem fs = null;
        BufferedReader br = null;
        try {
            Configuration conf = new Configuration();

            fs = FileSystem.get(URI.create(pathStr),conf);
            if(fs.isDirectory(new Path(pathStr))){
                FileStatus[] fileStatuses = fs.listStatus(new Path(pathStr));
                for(FileStatus status: fileStatuses){
                    if(status.isFile()){
                        FSDataInputStream inputStream = fs.open(status.getPath());
                        br = new BufferedReader(new InputStreamReader(inputStream));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            RawDataSet rawDataSet = getRawDataSet(line, longitudeIndex, latitudeIndex, separator);
                            if(rawDataSet != null) result.add(rawDataSet);
                        }
                    }
                }
            }else{
                FSDataInputStream inputStream = fs.open(new Path(pathStr));
                br = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                while ((line = br.readLine()) != null) {
                    RawDataSet rawDataSet = getRawDataSet(line, longitudeIndex, latitudeIndex, separator);
                    if(rawDataSet != null) result.add(rawDataSet);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(br != null)br.close();
                if(fs != null)fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
