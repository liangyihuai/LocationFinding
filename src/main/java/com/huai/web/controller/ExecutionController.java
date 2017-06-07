package com.huai.web.controller;

import com.huai.algorithm.MetadataOfDataset;
import com.huai.algorithm.ParamsOfAlgorithm;
import com.huai.web.service.ExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**

 * Created by liangyh on 3/12/17.
 */
@Controller
@RequestMapping(value = "/exec")
public class ExecutionController {

    @Autowired
    private ExecutionService executionService;

    @ResponseBody
    @RequestMapping(value = "/algorithm")
    public int executeAlgorithm(String eps,
                                String minPoints,
                                String boxLength,
                                String pointNumCluster,
                                String statusDuration,
                                String subtractTime,

                                String dataset_local,
                                String dataset_hdfs,
                                String checkLocalDataSet,
                                String checkHDFSDataSet,

                                String userIdIndex,
                                String timeIndex,
                                String longitudeIndex,
                                String latitudeIndex,
                                String separatorIndex,

                                String local_resultSet,
                                String hdfs_resultSet,
                                String checkLocalResultSet,
                                String checkHDFSResultSet,

                                String runModel,
                                HttpServletRequest request){

        MetadataOfDataset metadataOfDataset = new MetadataOfDataset(Integer.valueOf(userIdIndex),
                Integer.valueOf(timeIndex), Integer.valueOf(longitudeIndex),
                Integer.valueOf(latitudeIndex), separatorIndex);

        ParamsOfAlgorithm paramsOfAlgorithm = new ParamsOfAlgorithm(Double.valueOf(boxLength),
                Double.valueOf(eps), Integer.valueOf(minPoints), Integer.valueOf(statusDuration),
                Integer.valueOf(subtractTime), Integer.valueOf(pointNumCluster),
                dataset_local,local_resultSet, Integer.valueOf(runModel));

        if(checkLocalDataSet.equals("0")){
            if(!dataset_hdfs.startsWith("hdfs:")){
                if(dataset_hdfs.startsWith("/")){
                    dataset_hdfs = "hdfs:/"+dataset_hdfs;
                }else{
                    dataset_hdfs = "hdfs://"+dataset_hdfs;
                }
            }
            paramsOfAlgorithm.inputPath_$eq(dataset_hdfs);
        }

        if(checkLocalResultSet.equals("0")){
            if(!hdfs_resultSet.startsWith("hdfs:")){
                if(hdfs_resultSet.startsWith("/")){
                    hdfs_resultSet = "hdfs:/"+hdfs_resultSet;
                }else{
                    hdfs_resultSet = "hdfs://"+hdfs_resultSet;
                }
            }
            paramsOfAlgorithm.outputPath_$eq(hdfs_resultSet);
        }
        String path = request.getSession().getServletContext().getRealPath("/");
        executionService.startAlgorithm(metadataOfDataset, paramsOfAlgorithm, path);
        return 1;
    }

    @RequestMapping(value = "/emptyLog")
    public void emptyLog(){
        File file = new File("/tmp/graduate/log.log");
        if(!file.exists() || !file.isFile())return ;
        file.delete();

    }

    @ResponseBody
    @RequestMapping(value = "/getLog")
    public String getLog(){

        File file = new File("/tmp/graduate/log.log");
        if(!file.exists() || !file.isFile())return "";

        // 定义结果集
        List<String> lines = new ArrayList<String>();
        //行数统计
        int count = 0;
        int lineNum = 201;

        // 使用随机读取
        RandomAccessFile fileRead = null;
        try {
            //使用读模式
            fileRead = new RandomAccessFile(file, "r");
            //读取文件长度
            long length = fileRead.length();
            //如果是0，代表是空文件，直接返回空结果
            if (length == 0L) return "";

            //初始化游标
            long pos = length - 2;
            while (pos > 0) {
                //开始读取
                fileRead.seek(pos);
                //如果读取到\n代表是读取到一行
                if (fileRead.readByte() == '\n') {
                    //使用readLine获取当前行
                    String line = fileRead.readLine();
                    //保存结果
                    lines.add(line);
                    //行数统计，如果到达了numRead指定的行数，就跳出循环
                    count++;
                    if (count == lineNum) {
                        break;
                    }
                }
                pos--;
            }
            if(pos == 0){
                fileRead.seek(pos);
                lines.add(fileRead.readLine());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileRead != null) {
                try {
                    //关闭资源
                    fileRead.close();
                }
                catch (Exception e) {
                }
            }
        }
        StringBuilder builder = new StringBuilder();
        for(int i = lines.size()-1; i >= 0; i--){
            builder.append(lines.get(i)+"\n");
        }
        return builder.toString();
    }

}
