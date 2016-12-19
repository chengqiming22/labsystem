package com.cqm.labsystem.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by qmcheng on 2016/12/19 0019.
 */
@Controller
@RequestMapping("/account")
public class LoginController {
    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(ModelMap map){
        return "index";
    }
}
