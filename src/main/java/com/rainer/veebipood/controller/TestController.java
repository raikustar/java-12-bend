package com.rainer.veebipood.controller;

import com.rainer.veebipood.entity.Histogram;
import com.rainer.veebipood.entity.LogValues;
import com.rainer.veebipood.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;



@RestController
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping("request/{topNumber}")
    public List<LogValues> getResultByTopNumber(@PathVariable int topNumber) {
        return testService.getDurationParse(topNumber);
    }


    @GetMapping("histogram")
    public List<Histogram> getResultByHistogram() {
        return testService.getMinuteHistogramParse();
    }
}

