package com.huai.web.service.impl;

import com.huai.algorithm.util.ParamsUtils;
import com.huai.web.service.*;
import org.apache.spark.deploy.SparkSubmit;
import org.springframework.stereotype.Component;

import com.huai.algorithm.MetadataOfDataset;
import com.huai.algorithm.ParamsOfAlgorithm;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liangyh on 3/14/17.
 */
@Component
public class ExecutionServiceImpl implements ExecutionService{

    @Override
    public void startAlgorithm(MetadataOfDataset metadataOfDataset, ParamsOfAlgorithm paramsOfAlgorithm,
                               String applicationContextPath) {
        List<String> algorithmArgs = ParamsUtils.generateParamsArray(metadataOfDataset, paramsOfAlgorithm);

        if(paramsOfAlgorithm.runModel() == 0){
            new Thread(() -> submitSparkJobToLocal(algorithmArgs, applicationContextPath)).start();
        }else if(paramsOfAlgorithm.runModel() == 1){
            new Thread(() -> submitSparkJobToStandalone(algorithmArgs, applicationContextPath)).start();
        }
    }

    private String[] getAllArgs(String[] contextArgs, List<String> algorithmArgs){
        int size = algorithmArgs.size()+contextArgs.length;
        String[] allArgs = new String[size];
        int index = 0;
        for(int i = 0; i < contextArgs.length; i++){
            allArgs[index++] = contextArgs[i];
        }
        Iterator<String> iter = algorithmArgs.iterator();
        while(iter.hasNext()){
            allArgs[index++] = iter.next();
        }
        return allArgs;
    }

    public void submitSparkJobToLocal(List<String> algorithmArgs, String applicationContextPath){
        String[] args = new String[] {
                "--master", "local[*]",
                "--class", "com.huai.algorithm.MainOfSingle",
                getAlgorithmJarArchivePath(applicationContextPath)
        };
        SparkSubmit.main(getAllArgs(args, algorithmArgs));
    }

    public void submitSparkJobToStandalone(List<String> algorithmArgs, String applicationContextPath){
        String[] args = new String[] {
                "--master", "spark://master:7077",
                "--class", "com.huai.algorithm.MainOfSingle",
                getAlgorithmJarArchivePath(applicationContextPath)
        };
        SparkSubmit.main(getAllArgs(args, algorithmArgs));
    }

    /**
     *
     * @param applicationContextPath
     * @return
     */
    private String getAlgorithmJarArchivePath(String applicationContextPath){
        File file = new File(applicationContextPath+"/WEB-INF/lib/");
        if(file.exists()&&file.isDirectory()){
            File [] files = file.listFiles();
            for(File f: files){
                if(f.getName().contains("algorithm")){
                    return file.getPath()+"/"+f.getName();
                }
            }
        }
       return "";
    }

}
