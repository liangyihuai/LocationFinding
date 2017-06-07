package com.huai.web.service;

import com.huai.algorithm.MetadataOfDataset;
import com.huai.algorithm.ParamsOfAlgorithm;

/**
 * Created by liangyh on 3/14/17.
 */

public interface ExecutionService {
    void startAlgorithm(MetadataOfDataset metadataOfDataset, ParamsOfAlgorithm paramsOfAlgorithm,
    String applicationContextPath);
}
