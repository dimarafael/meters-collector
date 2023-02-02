package com.dima.meterscollector.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    //serving react application
    public ModelAndView home(){
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }
}
