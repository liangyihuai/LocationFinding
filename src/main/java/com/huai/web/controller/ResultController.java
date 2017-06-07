package com.huai.web.controller;

import com.huai.web.pojo.Result;
import com.huai.web.service.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by liangyh on 3/12/17.
 */
@Controller
@RequestMapping(value = "/result")
public class ResultController {

    private final static Logger logger = LoggerFactory.getLogger(ResultController.class);

    @Autowired
    private ResultService resultSetService;

    @ResponseBody
    @RequestMapping(value = "/hello")
    public String page(){
        return "helloworld";
    }

    @ResponseBody
    @RequestMapping(value = "/data")
    public List<Result> getResultSet(){
        return resultSetService.getResultSet(0, 10);
    }
}