package com.cqm.labsystem.account.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * Created by qmcheng on 2016/11/30 0030.
 */
@Controller
@RequestMapping("account")
public class AccountController {
    @RequestMapping("signup")
    public String signUp() throws ClassNotFoundException{
        return "signup";
    }
}
