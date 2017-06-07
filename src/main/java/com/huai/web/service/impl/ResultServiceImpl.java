package com.huai.web.service.impl;

import com.huai.web.pojo.Result;
import com.huai.web.repository.ResultMapper;
import com.huai.web.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by liangyh on 3/12/17.
 */
@Component
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultMapper resultMapper;

    @Override
    public List<Result> getResultSet(int start, int pageSize) {

        return resultMapper.queryResultSet(start, pageSize);
    }
}
