package com.huai.web.service;

import com.huai.web.pojo.Result;

import java.util.List;

/**
 * Created by liangyh on 3/12/17.
 */
public interface ResultService {

    List<Result> getResultSet(int start, int pageSize);
}
