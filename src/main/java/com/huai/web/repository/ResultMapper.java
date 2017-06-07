package com.huai.web.repository;

import com.huai.web.pojo.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liangyh on 3/12/17.
 */
public interface ResultMapper {

    List<Result> queryResultSet(@Param("start") int start, @Param("pageSize") int pageSize);
}
